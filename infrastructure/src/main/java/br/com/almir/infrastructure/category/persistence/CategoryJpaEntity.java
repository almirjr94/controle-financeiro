package br.com.almir.infrastructure.category.persistence;

import br.com.almir.domain.category.Category;
import br.com.almir.domain.category.CategoryID;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Category")
@Table(name = "category")
public class CategoryJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
  private Instant updatedAt;

  public CategoryJpaEntity() {
  }

  public CategoryJpaEntity(Long id, String name, Instant createdAt, Instant updatedAt) {
    this.id = id;
    this.name = name;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static CategoryJpaEntity from(final Category category) {
    return new CategoryJpaEntity(
        category.getId() != null ? category.getId().getValue() : null,
        category.getName(),
        category.getCreatedAt(),
        category.getUpdatedAt()
    );
  }

  public static CategoryJpaEntity from(final CategoryID category) {
    return new CategoryJpaEntity(
        category.getValue(), null, null, null
    );
  }

  public Category toAggregate() {
    return Category.with(
        CategoryID.from(getId()),
        getName(),
        getCreatedAt(),
        getUpdatedAt()
    );
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }
}
