package br.com.almir.application.subcategory.delete;

import br.com.almir.domain.exceptions.DomainException;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.financialrelease.FinancialReleaseID;
import br.com.almir.domain.subcategory.SubcategoryGateway;
import br.com.almir.domain.subcategory.SubcategoryID;
import br.com.almir.domain.validation.Error;
import java.util.List;
import java.util.Objects;

public class DefaultDeleteSubcategoryUseCase extends DeleteSubcategoryUseCase {

  private final SubcategoryGateway subcategoryGateway;
  private final FinancialReleaseGateway financialReleaseGateway;

  public DefaultDeleteSubcategoryUseCase(final SubcategoryGateway subcategoryGateway,
      FinancialReleaseGateway financialReleaseGateway) {
    this.subcategoryGateway = Objects.requireNonNull(subcategoryGateway);
    this.financialReleaseGateway = Objects.requireNonNull(financialReleaseGateway);
  }

  @Override
  public void execute(final Long in) {

    List<FinancialReleaseID> financialReleaseIDs =
        financialReleaseGateway.findIdsBySubCategoryIds(List.of(SubcategoryID.from(in)));

    if (financialReleaseIDs != null && !financialReleaseIDs.isEmpty()) {
      throw DomainException.with(
          new Error(String.format("CategoryID %s there are financial release", in)));
    }

    this.subcategoryGateway.deleteById(SubcategoryID.from(in));
  }
}
