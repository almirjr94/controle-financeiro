package br.com.almir.application.balance.categoria;

import br.com.almir.application.balance.BalanceHelper;
import br.com.almir.application.balance.BalanceResult;
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

public class DefaultBalanceByCategorieUseCase extends BalanceByCategorieUseCase {

  public static final String THERE_IS_NO_BALANCE = "Category there is no balance";

  private final CategoryGateway categoryGateway;
  private final SubcategoryGateway subcategoryGateway;
  private final FinancialReleaseGateway financialReleaseGateway;

  public DefaultBalanceByCategorieUseCase(
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

    List<MonetaryAmount> monetaryAmounts = financialReleaseGateway
        .findBySubCategoryIds(subcategoryIDS, command.initDate(), command.endDate())
        .stream()
        .map(FinancialRelease::getMoney).toList();

    BalanceResult balanceResult = BalanceHelper.total(monetaryAmounts);

    BalanceCategoryDescriptionOutput categoryDescription =
        BalanceCategoryDescriptionOutput.with(category.getId(), category.getName());

    return BalanceByCategoryOutput
        .with(categoryDescription, balanceResult.profit(), balanceResult.waste(),
            balanceResult.balance());
  }

}
