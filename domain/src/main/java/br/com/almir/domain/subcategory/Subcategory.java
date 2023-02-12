package br.com.almir.domain.subcategory;

import br.com.almir.domain.AggregateRoot;
import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.financialrelease.FinancialReleaseID;
import br.com.almir.domain.validation.ValidationHandler;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Subcategory extends AggregateRoot<SubcategoryID> {

  private String name;
  private CategoryID categoryID;
  private List<FinancialReleaseID> financialReleases;
  private Instant createdAt;
  private Instant updatedAt;

  private Subcategory(final SubcategoryID subcategoryId,
      final String name,
      final CategoryID categoryID,
      final List<FinancialReleaseID> financialReleases,
      final Instant createdAt,
      final Instant updatedAt) {

    super(subcategoryId);
    this.name = name;
    this.categoryID = Objects.requireNonNull(categoryID, "'categoryID' should not be null");
    this.financialReleases = financialReleases;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static Subcategory newSubcatergory(
      final String name,
      final CategoryID category
  ) {
    var now = Instant.now();
    return new Subcategory(null, name, category, new ArrayList<>(), now, now);
  }

  public Subcategory with(final SubcategoryID subcategoryID) {
    if (this.getId() == null) {
      super.id = subcategoryID;
    }
    return this;
  }

  public Subcategory update(
      final String name
  ) {
    this.name = name;
    this.updatedAt = Instant.now();
    return this;
  }

  public Subcategory addFinancialRelease(final FinancialReleaseID financialReleaseID) {
    if (financialReleaseID == null) {
      return this;
    }
    this.financialReleases.add(financialReleaseID);
    this.updatedAt = Instant.now();
    return this;
  }

  public Subcategory addFinancialRelease(final List<FinancialReleaseID> financialReleases) {
    if (financialReleases == null || financialReleases.isEmpty()) {
      return this;
    }
    this.financialReleases.addAll(financialReleases);
    this.updatedAt = Instant.now();
    return this;
  }

  @Override
  public void validate(ValidationHandler handler) {
    new SubCategoryValidator(this, handler).validate();
  }

  public List<FinancialReleaseID> getFinancialReleases() {
    return Collections.unmodifiableList(financialReleases);
  }

  public String getName() {
    return name;
  }

  public CategoryID getCategoryID() {
    return categoryID;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }


}
