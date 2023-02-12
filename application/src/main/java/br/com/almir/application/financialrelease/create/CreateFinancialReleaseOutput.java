package br.com.almir.application.financialrelease.create;

import br.com.almir.domain.financialrelease.FinancialRelease;

public record CreateFinancialReleaseOutput(
    Long id
) {

  public static CreateFinancialReleaseOutput from(final Long id) {
    return new CreateFinancialReleaseOutput(id);
  }

  public static CreateFinancialReleaseOutput from(final FinancialRelease financialRelease) {
    return new CreateFinancialReleaseOutput(financialRelease.getId().getValue());
  }

}
