package br.com.almir.domain.financialrelease;


import br.com.almir.domain.pagination.Pagination;
import br.com.almir.domain.pagination.SearchQuery;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.util.List;
import java.util.Optional;

public interface FinancialReleaseGateway {

  FinancialRelease create(FinancialRelease subcategory);

  void deleteById(FinancialReleaseID id);

  Optional<FinancialRelease> findById(FinancialReleaseID id);

  FinancialRelease update(FinancialRelease financialRelease);

  Pagination<FinancialRelease> findAll(SearchQuery<FinancialReleaseFilter> query);

  List<FinancialReleaseID> findBySubCategoryIds(Iterable<SubcategoryID> subcategoryIDs);
}
