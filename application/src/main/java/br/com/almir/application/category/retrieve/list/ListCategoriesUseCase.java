package br.com.almir.application.category.retrieve.list;


import br.com.almir.application.UseCase;
import br.com.almir.domain.category.CategoryFilter;
import br.com.almir.domain.pagination.Pagination;
import br.com.almir.domain.pagination.SearchQuery;

public abstract class ListCategoriesUseCase
    extends UseCase<SearchQuery<CategoryFilter>, Pagination<CategoryListOutput>> {

}
