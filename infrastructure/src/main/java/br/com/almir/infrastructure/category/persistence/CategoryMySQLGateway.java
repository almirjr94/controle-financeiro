package br.com.almir.infrastructure.category.persistence;

import br.com.almir.domain.category.Category;
import br.com.almir.domain.category.CategoryFilter;
import br.com.almir.domain.category.CategoryGateway;
import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.pagination.Pagination;
import br.com.almir.domain.pagination.SearchQuery;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CategoryMySQLGateway implements CategoryGateway {

  private final CategoryRepository repository;

  public CategoryMySQLGateway(CategoryRepository categoryRepository) {
    this.repository = categoryRepository;
  }

  @Override
  public Category create(Category category) {
    return save(category);
  }

  @Override
  public void deleteById(CategoryID id) {
    final Long idValue = id.getValue();
    if (this.repository.existsById(idValue)) {
      this.repository.deleteById(idValue);
    }
  }

  @Override
  public Optional<Category> findById(CategoryID id) {
    return this.repository.findById(id.getValue())
        .map(CategoryJpaEntity::toAggregate);
  }

  @Override
  public Category update(Category category) {
    return save(category);
  }

  @Override
  public Pagination<Category> findAll(SearchQuery<CategoryFilter> query) {
    final var page = PageRequest.of(
        query.page(),
        query.perPage(),
        Sort.by(Direction.fromString(query.direction()), query.sort())
    );

    Page<CategoryJpaEntity> pageResult = repository.findAll(
        Specification.where(specificaton(query.filter())), page);

    return new Pagination<>(
        pageResult.getNumber(),
        pageResult.getSize(),
        pageResult.getTotalElements(),
        pageResult.map(CategoryJpaEntity::toAggregate).toList());
  }

  @Override
  public boolean existByName(String name) {
    return repository.existsByName(name);
  }

  private Category save(Category category) {
    CategoryJpaEntity save = repository.save(CategoryJpaEntity.from(category));
    return save.toAggregate();
  }

  private Specification<CategoryJpaEntity> specificaton(final CategoryFilter str) {
    if (str.name() != null && !str.name().isBlank()) {
      return (root, query, cb) -> cb.like(cb.lower(root.get("name")),
          "%" + str.name().toLowerCase() + "%");
    }
    return null;
  }
}
