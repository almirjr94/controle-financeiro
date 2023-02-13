package br.com.almir.domain.financialrelease;


import br.com.almir.domain.pagination.Pagination;
import br.com.almir.domain.pagination.SearchQuery;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FinancialReleaseGateway {

  FinancialRelease create(FinancialRelease subcategory);

  void deleteById(FinancialReleaseID id);

  Optional<FinancialRelease> findById(FinancialReleaseID id);

  FinancialRelease update(FinancialRelease financialRelease);

  Pagination<FinancialRelease> findAll(SearchQuery<FinancialReleaseFilter> query);

  List<FinancialRelease> findAll(LocalDate start, LocalDate end);

  List<FinancialReleaseID> findIdsBySubCategoryIds(Iterable<SubcategoryID> subcategoryIDs);

  List<FinancialRelease> findBySubCategoryIds(Iterable<SubcategoryID> subcategoryIDs,
      LocalDate start, LocalDate end);
}
