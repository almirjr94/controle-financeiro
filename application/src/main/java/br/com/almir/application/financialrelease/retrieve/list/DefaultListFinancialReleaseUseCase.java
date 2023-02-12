package br.com.almir.application.financialrelease.retrieve.list;

import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.pagination.Pagination;
import br.com.almir.domain.pagination.SearchQuery;
import java.util.Objects;

public class DefaultListFinancialReleaseUseCase extends ListFinancialReleaseUseCase {

  private final FinancialReleaseGateway financialReleaseGateway;

  public DefaultListFinancialReleaseUseCase(FinancialReleaseGateway financialReleaseGateway) {
    this.financialReleaseGateway = Objects.requireNonNull(financialReleaseGateway);
  }

  @Override
  public Pagination<FinancialReleaseListOutput> execute(SearchQuery query) {
    return financialReleaseGateway.findAll(query)
        .map(FinancialReleaseListOutput::from);
  }
}
