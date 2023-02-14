package br.com.almir.infrastructure.subcategory.persistence;

import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.pagination.Pagination;
import br.com.almir.domain.pagination.SearchQuery;
import br.com.almir.domain.subcategory.Subcategory;
import br.com.almir.domain.subcategory.SubcategoryFilter;
import br.com.almir.domain.subcategory.SubcategoryGateway;
import br.com.almir.domain.subcategory.SubcategoryID;
import br.com.almir.infrastructure.category.persistence.CategoryJpaEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SubcategoryMySQLGateway implements SubcategoryGateway {

  private final SubcategoryRepository repository;

  public SubcategoryMySQLGateway(SubcategoryRepository repository) {
    this.repository = repository;
  }

  @Override
  public Subcategory create(Subcategory subcategory) {
    return save(subcategory);
  }

  private Subcategory save(Subcategory subcategory) {
    return repository.save(SubcategoryJpaEntity.from(subcategory)).toAggregate();
  }

  @Override
  public void deleteById(SubcategoryID id) {
    final Long idValue = id.getValue();
    if (this.repository.existsById(idValue)) {
      this.repository.deleteById(idValue);
    }
  }

  @Override
  public Optional<Subcategory> findById(SubcategoryID id) {
    return this.repository.findById(id.getValue())
        .map(SubcategoryJpaEntity::toAggregate);
  }

  @Override
  public Subcategory update(Subcategory subcategory) {
    return save(subcategory);
  }

  @Override
  public Pagination<Subcategory> findAll(SearchQuery<SubcategoryFilter> query) {

    final var page = PageRequest.of(
        query.page(),
        query.perPage(),
        Sort.by(Direction.fromString(query.direction()), query.sort())
    );

    Page<SubcategoryJpaEntity> pageResult = repository.findAll(
        Specification.where(specificaton(query.filter())), page);

    return new Pagination<>(
        pageResult.getNumber(),
        pageResult.getSize(),
        pageResult.getTotalElements(),
        pageResult.map(SubcategoryJpaEntity::toAggregate).toList());
  }

  @Override
  public List<SubcategoryID> findByCategoryId(CategoryID categoryId) {
    return repository.findByCategoryId(categoryId.getValue())
        .stream()
        .map(SubcategoryJpaEntity::getId)
        .map(SubcategoryID::from)
        .toList();
  }

  private Specification<SubcategoryJpaEntity> specificaton(final SubcategoryFilter str) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (str.name() != null && !str.name().isBlank()) {
        predicates.add(cb.like(cb.lower(root.get("name")), "%" + str.name().toLowerCase() + "%"));
      }

      if (str.name() != null && !str.name().isBlank()) {
        predicates.add(cb.equal(root.get("id"), str.id().getValue().toString()));
      }

      return cb.or(predicates.toArray(new Predicate[0]));
    };
  }
}
