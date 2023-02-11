package br.com.almir.domain.subcategory;


import br.com.almir.domain.pagination.Pagination;
import br.com.almir.domain.pagination.SearchQuery;
import java.util.List;
import java.util.Optional;

public interface SubcategoryGateway {

  Subcategory create(Subcategory aCategory);

  void deleteById(SubcategoryID anId);

  Optional<Subcategory> findById(SubcategoryID anId);

  Subcategory update(Subcategory aCategory);

  Pagination<Subcategory> findAll(SearchQuery aQuery);

  List<SubcategoryID> existsByIds(Iterable<SubcategoryID> ids);
}
