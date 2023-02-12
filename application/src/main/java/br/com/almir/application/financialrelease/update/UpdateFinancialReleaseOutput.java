package br.com.almir.application.financialrelease.update;

import br.com.almir.domain.financialrelease.FinancialRelease;

public record UpdateFinancialReleaseOutput(
    Long id
) {

  public static UpdateFinancialReleaseOutput from(final Long anId) {
    return new UpdateFinancialReleaseOutput(anId);
  }

  public static UpdateFinancialReleaseOutput from(final FinancialRelease release) {
    return new UpdateFinancialReleaseOutput(release.getId().getValue());
  }

}
