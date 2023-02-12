package br.com.almir.application.category.delete;

import br.com.almir.domain.category.CategoryGateway;
import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.exceptions.DomainException;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.financialrelease.FinancialReleaseID;
import br.com.almir.domain.subcategory.SubcategoryGateway;
import br.com.almir.domain.subcategory.SubcategoryID;
import br.com.almir.domain.validation.Error;
import java.util.List;
import java.util.Objects;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {

  private final CategoryGateway categoryGateway;
  private final SubcategoryGateway subcategoryGateway;
  private final FinancialReleaseGateway financialReleaseGateway;

  public DefaultDeleteCategoryUseCase(final CategoryGateway categoryGateway,
      SubcategoryGateway subcategoryGateway, FinancialReleaseGateway financialReleaseGateway) {
    this.categoryGateway = Objects.requireNonNull(categoryGateway);
    this.subcategoryGateway = Objects.requireNonNull(subcategoryGateway);
    this.financialReleaseGateway = Objects.requireNonNull(financialReleaseGateway);
  }

  @Override
  public void execute(final Long in) {
    List<SubcategoryID> categoryIds = subcategoryGateway.findByCategoryId(CategoryID.from(in));
    List<FinancialReleaseID> financialReleaseIDs = financialReleaseGateway.findBySubCategoryIds(
        categoryIds);

    if (financialReleaseIDs != null && !financialReleaseIDs.isEmpty()) {
      throw DomainException.with(
          new Error(String.format("CategoryID %s there are financial release", in)));
    }

    this.categoryGateway.deleteById(CategoryID.from(in));
  }
}
