package br.com.almir.infrastructure.subcategory.persistence;

import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.subcategory.Subcategory;
import br.com.almir.domain.subcategory.SubcategoryID;
import br.com.almir.infrastructure.category.persistence.CategoryJpaEntity;
import java.time.Instant;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "Subcategory")
@Table(name = "subcategory", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"category_id", "name"})})
public class SubcategoryJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;


  @Column(name = "name", nullable = false)
  private String name;

  @OneToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "category_id", nullable = false)
  private CategoryJpaEntity category;

  @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
  private Instant updatedAt;

  public SubcategoryJpaEntity() {
  }

  public SubcategoryJpaEntity(Long id, String name, CategoryJpaEntity category, Instant createdAt,
      Instant updatedAt) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static SubcategoryJpaEntity from(final Subcategory subcategory) {
    return new SubcategoryJpaEntity(
        subcategory.getId().getValue(),
        subcategory.getName(),
        CategoryJpaEntity.from(subcategory.getCategoryID()),
        subcategory.getCreatedAt(),
        subcategory.getUpdatedAt()
    );
  }

  public Subcategory toAggregate() {
    return Subcategory.with(
        SubcategoryID.from(getId()),
        getName(),
        CategoryID.from(getCategory().getId()),
        getCreatedAt(),
        getUpdatedAt()
    );
  }

  public static SubcategoryJpaEntity from(final SubcategoryID subcategoryID) {
    return new SubcategoryJpaEntity(subcategoryID.getValue(), null, null, null, null);
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

  public CategoryJpaEntity getCategory() {
    return category;
  }

  public void setCategory(CategoryJpaEntity category) {
    this.category = category;
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
