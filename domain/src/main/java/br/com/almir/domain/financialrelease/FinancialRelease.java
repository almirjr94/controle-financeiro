package br.com.almir.domain.financialrelease;

import br.com.almir.domain.AggregateRoot;
import br.com.almir.domain.exceptions.NotificationException;
import br.com.almir.domain.subcategory.SubcategoryID;
import br.com.almir.domain.validation.ValidationHandler;
import br.com.almir.domain.validation.handler.Notification;
import java.time.Instant;
import java.time.LocalDate;
import javax.money.MonetaryAmount;

public class FinancialRelease extends AggregateRoot<FinancialReleaseID> {

  private MonetaryAmount money;
  private SubcategoryID subcategoryID;
  private String description;
  private LocalDate releasedAt;

  private Instant createdAt;
  private Instant updatedAt;

  private FinancialRelease(
      final FinancialReleaseID financialReleaseID,
      final MonetaryAmount money,
      final SubcategoryID subcategoryID,
      final String describe,
      final LocalDate releasedAt,
      final Instant createdAt,
      final Instant updatedAt) {

    super(financialReleaseID);
    this.money = money;
    this.subcategoryID = subcategoryID;
    this.description = describe;
    this.releasedAt = releasedAt;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    selfValidate();
  }

  public static FinancialRelease newFinancialRelease(
      final MonetaryAmount money,
      final SubcategoryID subcategoryID,
      final String describe,
      final LocalDate releasedAt
  ) {
    final var now = Instant.now();
    final var release = releasedAt == null ? LocalDate.from(now) : releasedAt;

    return new FinancialRelease(null,
        money, subcategoryID, describe, release, now, now);
  }

  public FinancialRelease with(final FinancialReleaseID financialReleaseID) {
    if (this.getId() == null) {
      super.id = financialReleaseID;
    }
    return this;
  }


  public FinancialRelease update(
      final MonetaryAmount money,
      final String description,
      final LocalDate releasedAt) {

    this.money = money;
    this.description = description;
    this.releasedAt = releasedAt;
    this.updatedAt = Instant.now();

    selfValidate();
    return this;
  }

  private void selfValidate() {
    final var notification = Notification.create();
    validate(notification);

    if (notification.hasError()) {
      throw new NotificationException("Failed to create a Aggregate FinancialRelease",
          notification);
    }
  }

  public MonetaryAmount getMoney() {
    return money;
  }

  public SubcategoryID getSubcategoryID() {
    return subcategoryID;
  }

  public String getDescription() {
    return description;
  }

  public LocalDate getReleasedAt() {
    return releasedAt;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public void validate(ValidationHandler handler) {
    new FinancialReleaseValidator(this, handler).validate();
  }
}
