package br.com.almir.application.financialrelease.retrieve.list;

import br.com.almir.application.UseCase;
import br.com.almir.domain.pagination.Pagination;
import br.com.almir.domain.pagination.SearchQuery;

public abstract class ListFinancialReleaseUseCase extends
    UseCase<SearchQuery, Pagination<FinancialReleaseListOutput>> {

}
