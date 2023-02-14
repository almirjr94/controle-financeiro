package br.com.almir.infrastructure.financerelease.persistence;

import br.com.almir.domain.financialrelease.FinancialRelease;
import br.com.almir.domain.financialrelease.FinancialReleaseFilter;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.financialrelease.FinancialReleaseID;
import br.com.almir.domain.pagination.Pagination;
import br.com.almir.domain.pagination.SearchQuery;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class FinancialMySqlGateway implements FinancialReleaseGateway {

  private final FinancialReleaseRepository repository;

  public FinancialMySqlGateway(FinancialReleaseRepository repository) {
    this.repository = Objects.requireNonNull(repository);
  }

  @Override
  public FinancialRelease create(FinancialRelease financialRelease) {
    return save(financialRelease);
  }

  @Override
  public void deleteById(FinancialReleaseID id) {
    final Long idValue = id.getValue();
    if (this.repository.existsById(idValue)) {
      this.repository.deleteById(idValue);
    }
  }

  @Override
  public Optional<FinancialRelease> findById(FinancialReleaseID id) {
    return this.repository.findById(id.getValue())
        .map(FinancialReleaseJpaEntity::toAggregate);
  }

  @Override
  public FinancialRelease update(FinancialRelease financialRelease) {
    return save(financialRelease);
  }

  @Override
  public Pagination<FinancialRelease> findAll(SearchQuery<FinancialReleaseFilter> query) {

    final var page = PageRequest.of(
        query.page(),
        query.perPage(),
        Sort.by(Direction.fromString(query.direction()), query.sort())
    );

    Page<FinancialReleaseJpaEntity> pageResult = repository.findAll(
        Specification.where(specificaton(query.filter())), page);

    return new Pagination<>(
        pageResult.getNumber(),
        pageResult.getSize(),
        pageResult.getTotalElements(),
        pageResult.map(FinancialReleaseJpaEntity::toAggregate).toList());
  }

  @Override
  public List<FinancialRelease> findAll(LocalDate start, LocalDate end) {
    return repository.findByReleasedAtBetween(start, end)
        .stream()
        .map(FinancialReleaseJpaEntity::toAggregate).toList();
  }

  @Override
  public List<FinancialReleaseID> findIdsBySubCategoryIds(Iterable<SubcategoryID> subcategoryIDs) {
    List<Long> ids = StreamSupport.stream(subcategoryIDs.spliterator(), false)
        .map(SubcategoryID::getValue)
        .toList();

    return repository.findBySubcategoryIdIn(ids)
        .stream()
        .map(it -> FinancialReleaseID.from(it.getId()))
        .toList();
  }

  @Override
  public List<FinancialRelease> findBySubCategoryIds(Iterable<SubcategoryID> subcategoryIDs,
      LocalDate start, LocalDate end) {
    List<Long> ids = StreamSupport.stream(subcategoryIDs.spliterator(), false)
        .map(SubcategoryID::getValue)
        .toList();

    return repository.findByReleasedAtBetweenAndSubcategoryIdIn(start, end, ids)
        .stream()
        .map(FinancialReleaseJpaEntity::toAggregate)
        .toList();
  }

  private FinancialRelease save(FinancialRelease financialRelease) {
    return repository.save(FinancialReleaseJpaEntity.from(financialRelease)).toAggregate();
  }

  private Specification<FinancialReleaseJpaEntity> specificaton(
      final FinancialReleaseFilter filter) {

    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filter.releasedStart() != null && filter.releasedEnd() != null) {
        predicates.add(cb.between(root.get("releasedAt"), filter.releasedStart(),
            filter.releasedEnd()));
      }

      if (filter.subcategoryID() != null) {
        predicates.add(
            cb.equal(root.get("subcategory"), filter.subcategoryID().getValue()));
      }

      if (predicates.isEmpty()) {
        return null;
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
