package br.com.almir.domain.financialrelease;

import br.com.almir.domain.subcategory.SubcategoryID;
import java.time.LocalDate;

public record FinancialReleaseFilter(
    LocalDate releasedStart,
    LocalDate releasedEnd,
    SubcategoryID subcategoryID
) {


  public static FinancialReleaseFilter with(
      LocalDate releasedStart,
      LocalDate releasedEnd,
      SubcategoryID subcategoryID) {
    return new FinancialReleaseFilter(releasedStart, releasedEnd, subcategoryID);
  }

  public static FinancialReleaseFilter with(LocalDate releasedStart, LocalDate releasedEnd) {
    return with(releasedStart, releasedEnd, null);
  }
}
