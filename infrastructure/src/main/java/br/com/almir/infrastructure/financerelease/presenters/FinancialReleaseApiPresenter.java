package br.com.almir.infrastructure.financerelease.presenters;

import br.com.almir.application.financialrelease.retrieve.get.FinancialReleaseOutput;
import br.com.almir.application.financialrelease.retrieve.list.FinancialReleaseListOutput;
import br.com.almir.infrastructure.financerelease.model.FinancialReleaseListResponse;
import br.com.almir.infrastructure.financerelease.model.FinancialReleaseResponse;

public interface FinancialReleaseApiPresenter {

  static FinancialReleaseResponse present(final FinancialReleaseOutput output) {
    return new FinancialReleaseResponse(output.financialReleaseID().getValue(),
        output.money().getNumber().doubleValueExact(),
        output.subcategoryID().getValue(),
        output.describe(),
        output.releasedAt()
    );
  }

  static FinancialReleaseListResponse present(final FinancialReleaseListOutput output) {
    return new FinancialReleaseListResponse(output.financialReleaseID().getValue(),
        output.money().getNumber().doubleValueExact(),
        output.subcategoryID().getValue(),
        output.describe(),
        output.releasedAt());
  }


}
