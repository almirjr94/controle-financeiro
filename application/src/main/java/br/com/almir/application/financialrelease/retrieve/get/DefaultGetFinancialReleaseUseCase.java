package br.com.almir.application.financialrelease.retrieve.get;

import br.com.almir.domain.exceptions.NotFoundException;
import br.com.almir.domain.financialrelease.FinancialRelease;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.financialrelease.FinancialReleaseID;
import java.util.Objects;

public class DefaultGetFinancialReleaseUseCase extends GetFinancialReleaseUseCase {

  private final FinancialReleaseGateway financialReleaseGateway;

  public DefaultGetFinancialReleaseUseCase(FinancialReleaseGateway financialReleaseGateway) {
    this.financialReleaseGateway = Objects.requireNonNull(financialReleaseGateway);
  }

  @Override
  public FinancialReleaseOutput execute(Long in) {
    final var financialReleaseID = FinancialReleaseID.from(in);

    return financialReleaseGateway.findById(financialReleaseID)
        .map(FinancialReleaseOutput::from)
        .orElseThrow(() -> NotFoundException.with(FinancialRelease.class, financialReleaseID));
  }
}
