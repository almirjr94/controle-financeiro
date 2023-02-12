package br.com.almir.application.financialrelease.update;

import br.com.almir.domain.exceptions.NotFoundException;
import br.com.almir.domain.financialrelease.FinancialRelease;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.financialrelease.FinancialReleaseID;
import java.util.Objects;

public class DefaultUpdateFinancialReleaseUseCase extends UpdateFinancialReleaseUseCase {

  private final FinancialReleaseGateway financialReleaseGateway;

  public DefaultUpdateFinancialReleaseUseCase(FinancialReleaseGateway financialReleaseGateway) {
    this.financialReleaseGateway = Objects.requireNonNull(financialReleaseGateway);
  }

  @Override
  public UpdateFinancialReleaseOutput execute(UpdateFinancialReleaseCommand in) {
    final var id = FinancialReleaseID.from(in.id().getValue());

    FinancialRelease financialRelease = this.financialReleaseGateway.findById(id)
        .orElseThrow(() -> NotFoundException.with(FinancialRelease.class, id));

    financialRelease.update(in.money(), in.description(), in.releasedAt());

    financialReleaseGateway.update(financialRelease);

    return UpdateFinancialReleaseOutput.from(financialRelease.getId().getValue());
  }
}
