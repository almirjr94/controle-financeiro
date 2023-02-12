package br.com.almir.domain.subcategory;


import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.pagination.Pagination;
import br.com.almir.domain.pagination.SearchQuery;
import java.util.List;
import java.util.Optional;

public interface SubcategoryGateway {

  Subcategory create(Subcategory subcategory);

  void deleteById(SubcategoryID id);

  Optional<Subcategory> findById(SubcategoryID id);

  Subcategory update(Subcategory subcategory);

  Pagination<Subcategory> findAll(SearchQuery<SubcategoryFilter> query);

  List<SubcategoryID> findByCategoryId(CategoryID categoryId);
}
