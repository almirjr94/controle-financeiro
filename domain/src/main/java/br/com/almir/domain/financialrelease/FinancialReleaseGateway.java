package br.com.almir.domain.financialrelease;


import br.com.almir.domain.pagination.Pagination;
import br.com.almir.domain.pagination.SearchQuery;
import br.com.almir.domain.subcategory.Subcategory;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.util.List;
import java.util.Optional;

public interface FinancialReleaseGateway {

  FinancialRelease create(Subcategory aCategory);

  void deleteById(FinancialReleaseID anId);

  Optional<FinancialRelease> findById(FinancialReleaseID anId);

  Subcategory update(FinancialRelease aCategory);

  Pagination<FinancialRelease> findAll(SearchQuery aQuery);

  List<FinancialReleaseID> existsByIds(Iterable<FinancialReleaseID> ids);
}
