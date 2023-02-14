package br.com.almir.infrastructure.financerelease.persistence;

import br.com.almir.domain.financialrelease.FinancialRelease;
import br.com.almir.domain.financialrelease.FinancialReleaseID;
import br.com.almir.domain.subcategory.SubcategoryID;
import br.com.almir.infrastructure.subcategory.persistence.SubcategoryJpaEntity;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "financialRelease")
@Table(name = "financial_release")
public class FinancialReleaseJpaEntity {

  @Id
  @GeneratedValue
  private Long id;

  @OneToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "subcategory_id", nullable = false)
  private SubcategoryJpaEntity subcategory;

  @Column(name = "money", nullable = false)
  private BigDecimal money;

  @Column(name = "released_at", nullable = false)
  private LocalDate releasedAt;

  @Column(name = "description")
  private String description;

  @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
  private Instant updatedAt;

  public FinancialReleaseJpaEntity() {
  }

  public FinancialReleaseJpaEntity(Long id, SubcategoryJpaEntity subcategory, BigDecimal money,
      String description, LocalDate releasedAt,Instant createdAt, Instant updatedAt) {
    this.id = id;
    this.subcategory = subcategory;
    this.money = money;
    this.description = description;
    this.releasedAt = releasedAt;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static FinancialReleaseJpaEntity from(final FinancialRelease financialRelease) {
    return new FinancialReleaseJpaEntity(
        financialRelease.getId() != null ? financialRelease.getId().getValue() : null,
        SubcategoryJpaEntity.from(financialRelease.getSubcategoryID()),
        BigDecimal.valueOf(financialRelease.getMoney().getNumber().doubleValueExact()),
        financialRelease.getDescription(),
        financialRelease.getReleasedAt(),
        financialRelease.getCreatedAt(),
        financialRelease.getUpdatedAt()
    );
  }

  public FinancialRelease toAggregate() {
    return FinancialRelease.with(
        FinancialReleaseID.from(getId()),
        getMoney(),
        SubcategoryID.from(getSubcategory().getId()),
        getDescription(),
        getReleasedAt(),
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

  public SubcategoryJpaEntity getSubcategory() {
    return subcategory;
  }

  public void setSubcategory(
      SubcategoryJpaEntity subcategory) {
    this.subcategory = subcategory;
  }

  public BigDecimal getMoney() {
    return money;
  }

  public void setMoney(BigDecimal money) {
    this.money = money;
  }

  public LocalDate getReleasedAt() {
    return releasedAt;
  }

  public void setReleasedAt(LocalDate releasedAt) {
    this.releasedAt = releasedAt;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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
