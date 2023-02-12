package br.com.almir.application.category.retrieve.list;


import br.com.almir.domain.category.CategoryGateway;
import br.com.almir.domain.pagination.Pagination;
import br.com.almir.domain.pagination.SearchQuery;
import java.util.Objects;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultListCategoriesUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(final SearchQuery in) {
        return this.categoryGateway.findAll(in)
                .map(CategoryListOutput::from);
    }
}
