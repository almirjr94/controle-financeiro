package br.com.almir.application.subcategory.retrieve.list;


import br.com.almir.application.UseCase;
import br.com.almir.domain.pagination.Pagination;
import br.com.almir.domain.pagination.SearchQuery;
import br.com.almir.domain.subcategory.SubcategoryFilter;

public abstract class ListSubcategoriesUseCase
    extends UseCase<SearchQuery<SubcategoryFilter>, Pagination<SubcategoryListOutput>> {

}
