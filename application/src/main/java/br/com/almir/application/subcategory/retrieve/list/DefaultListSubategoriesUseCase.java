package br.com.almir.application.subcategory.retrieve.list;


import br.com.almir.domain.pagination.Pagination;
import br.com.almir.domain.pagination.SearchQuery;
import br.com.almir.domain.subcategory.SubcategoryGateway;
import java.util.Objects;

public class DefaultListSubategoriesUseCase extends ListSubcategoriesUseCase {

  private final SubcategoryGateway subcategoryGateway;

  public DefaultListSubategoriesUseCase(final SubcategoryGateway subcategoryGateway) {
    this.subcategoryGateway = Objects.requireNonNull(subcategoryGateway);
  }

  @Override
  public Pagination<SubcategoryListOutput> execute(final SearchQuery in) {
    return this.subcategoryGateway.findAll(in)
        .map(SubcategoryListOutput::from);
  }
}
