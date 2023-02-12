package br.com.almir.application.financialrelease.delete;

import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.financialrelease.FinancialReleaseID;
import java.util.Objects;

public class DefaultDeleteFinancialReleaseUseCase extends DeleteFinancialReleaseUseCase {

  private final FinancialReleaseGateway financialReleaseGateway;

  public DefaultDeleteFinancialReleaseUseCase(FinancialReleaseGateway financialReleaseGateway) {
    this.financialReleaseGateway = Objects.requireNonNull(financialReleaseGateway);
  }

  @Override
  public void execute(Long in) {
    financialReleaseGateway.deleteById(FinancialReleaseID.from(in));
  }
}
