package br.com.almir.domain.category;


import br.com.almir.domain.pagination.Pagination;
import br.com.almir.domain.pagination.SearchQuery;
import java.util.List;
import java.util.Optional;

public interface CategoryGateway {

  Category create(Category category);

  void deleteById(CategoryID id);

  Optional<Category> findById(CategoryID id);

  Category update(Category category);

  Pagination<Category> findAll(SearchQuery<CategoryFilter> query);

  List<CategoryID> existsByIds(Iterable<CategoryID> ids);
}
