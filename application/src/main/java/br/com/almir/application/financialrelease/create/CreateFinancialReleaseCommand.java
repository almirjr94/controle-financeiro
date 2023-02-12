package br.com.almir.application.financialrelease.create;

import br.com.almir.domain.subcategory.SubcategoryID;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateFinancialReleaseCommand(
    BigDecimal money,
    SubcategoryID subcategoryID,
    String description,
    LocalDate releasedAt
) {

  public static CreateFinancialReleaseCommand with(
      BigDecimal money,
      SubcategoryID subcategoryID,
      String description,
      LocalDate releasedAt
  ) {
    return new CreateFinancialReleaseCommand(money, subcategoryID, description, releasedAt);
  }

}
