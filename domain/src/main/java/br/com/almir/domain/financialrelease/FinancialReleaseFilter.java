package br.com.almir.domain.financialrelease;

import br.com.almir.domain.subcategory.SubcategoryID;
import java.time.LocalDate;

public class FinancialReleaseFilter {

  private LocalDate releasedStart;
  private LocalDate releasedEnd;
  private SubcategoryID subcategoryID;


  private FinancialReleaseFilter(LocalDate releasedStart, LocalDate releasedEnd,
      SubcategoryID subcategoryID) {
    this.releasedStart = releasedStart;
    this.releasedEnd = releasedEnd;
    this.subcategoryID = subcategoryID;
  }

  public FinancialReleaseFilter with(
      LocalDate releasedStart,
      LocalDate releasedEnd,
      SubcategoryID subcategoryID) {
    return new FinancialReleaseFilter(releasedStart, releasedEnd, subcategoryID);
  }

  public FinancialReleaseFilter with(LocalDate releasedStart, LocalDate releasedEnd) {
    return with(releasedStart, releasedEnd, null);
  }

  public LocalDate getReleasedStart() {
    return releasedStart;
  }

  public LocalDate getReleasedEnd() {
    return releasedEnd;
  }

  public SubcategoryID getSubcategoryID() {
    return subcategoryID;
  }
}
