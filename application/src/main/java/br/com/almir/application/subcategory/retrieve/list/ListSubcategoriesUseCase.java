package br.com.almir.application.subcategory.retrieve.list;


import br.com.almir.application.UseCase;
import br.com.almir.domain.pagination.Pagination;
import br.com.almir.domain.pagination.SearchQuery;

public abstract class ListSubcategoriesUseCase
    extends UseCase<SearchQuery, Pagination<SubcategoryListOutput>> {

}
