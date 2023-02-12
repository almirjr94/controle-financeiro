package br.com.almir.application.financialrelease.retrieve.list;

import br.com.almir.domain.financialrelease.FinancialRelease;
import br.com.almir.domain.financialrelease.FinancialReleaseID;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.time.Instant;
import java.time.LocalDate;
import javax.money.MonetaryAmount;

public record FinancialReleaseListOutput(
    FinancialReleaseID financialReleaseID,
    MonetaryAmount money,
    SubcategoryID subcategoryID,
    String describe,
    LocalDate releasedAt,
    Instant createdAt,
    Instant updatedAt
) {

  public static FinancialReleaseListOutput from(FinancialRelease financialRelease) {
    return new FinancialReleaseListOutput(
        financialRelease.getId(),
        financialRelease.getMoney(),
        financialRelease.getSubcategoryID(),
        financialRelease.getDescription(),
        financialRelease.getReleasedAt(),
        financialRelease.getCreatedAt(),
        financialRelease.getUpdatedAt()
    );
  }
}
