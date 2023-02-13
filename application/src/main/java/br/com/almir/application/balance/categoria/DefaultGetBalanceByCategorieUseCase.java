package br.com.almir.application.balance.categoria;

import br.com.almir.domain.category.Category;
import br.com.almir.domain.category.CategoryGateway;
import br.com.almir.domain.exceptions.DomainException;
import br.com.almir.domain.exceptions.NotFoundException;
import br.com.almir.domain.financialrelease.FinancialRelease;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.subcategory.SubcategoryGateway;
import br.com.almir.domain.subcategory.SubcategoryID;
import br.com.almir.domain.validation.Error;
import java.util.List;
import java.util.Objects;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.FastMoney;

public class DefaultGetBalanceByCategorieUseCase extends GetBalanceByCategorieUseCase {

  public static final String THERE_IS_NO_BALANCE = "Category there is no balance";
  public static final FastMoney ZERO = FastMoney.of(0, "BRL");

  private final CategoryGateway categoryGateway;
  private final SubcategoryGateway subcategoryGateway;
  private final FinancialReleaseGateway financialReleaseGateway;

  public DefaultGetBalanceByCategorieUseCase(
      CategoryGateway categoryGateway,
      SubcategoryGateway subcategoryGateway,
      FinancialReleaseGateway financialReleaseGateway
  ) {
    this.categoryGateway = Objects.requireNonNull(categoryGateway);
    this.subcategoryGateway = Objects.requireNonNull(subcategoryGateway);
    this.financialReleaseGateway = Objects.requireNonNull(financialReleaseGateway);
  }

  @Override
  public BalanceByCategoryOutput execute(BalanceByCategoryCommand command) {

    Category category = categoryGateway.findById(command.categoryID())
        .orElseThrow(() -> NotFoundException.with(Category.class, command.categoryID()));

    List<SubcategoryID> subcategoryIDS = subcategoryGateway.findByCategoryId(command.categoryID());

    if (subcategoryIDS == null || subcategoryIDS.isEmpty()) {
      throw DomainException.with(new Error(THERE_IS_NO_BALANCE));
    }

    List<MonetaryAmount> allFinancialReleases = financialReleaseGateway
        .findBySubCategoryIds(subcategoryIDS).stream()
        .map(FinancialRelease::getMoney)
        .toList();

    final var balance = getBalance(allFinancialReleases);
    final var profit = getProfit(allFinancialReleases);
    final var waste = getWaste(allFinancialReleases);

    BalanceCategoryDescriptionOutput categoryDescription =
        BalanceCategoryDescriptionOutput.with(category.getId(), category.getName());

    return BalanceByCategoryOutput.with(categoryDescription, profit, waste, balance);
  }

  private double getProfit(List<MonetaryAmount> financialReleases) {
    return financialReleases.stream()
        .filter(MonetaryAmount::isPositive)
        .reduce(MonetaryAmount::add).orElse(ZERO)
        .getNumber().doubleValueExact();
  }

  private double getWaste(List<MonetaryAmount> financialReleases) {
    return financialReleases.stream()
        .filter(MonetaryAmount::isNegative)
        .reduce(MonetaryAmount::add).orElse(ZERO)
        .getNumber().doubleValueExact();
  }

  private double getBalance(List<MonetaryAmount> financialReleases) {
    return financialReleases.stream()
        .reduce(MonetaryAmount::add)
        .orElse(ZERO).getNumber().doubleValueExact();
  }
}
