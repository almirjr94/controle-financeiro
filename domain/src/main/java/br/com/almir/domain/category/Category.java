package br.com.almir.domain.category;


import br.com.almir.domain.AggregateRoot;
import br.com.almir.domain.validation.ValidationHandler;
import java.time.Instant;

public class Category extends AggregateRoot<CategoryID> {

  private String name;

  private Instant createdAt;
  private Instant updatedAt;

  private Category(final CategoryID id,
      final String name,
      final Instant createdAt,
      final Instant updatedAt
  ) {
    super(id);
    this.name = name;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static Category newCategory(final String name) {
    final var now = Instant.now();
    return new Category(null, name, now, now);
  }

  public Category with(final CategoryID categoryID) {
    if (this.getId() == null) {
      super.id = categoryID;
    }

    return this;
  }

  public Category update(
      final String name
  ) {
    this.name = name;
    this.updatedAt = Instant.now();
    return this;
  }

  @Override
  public void validate(ValidationHandler handler) {
    new CategoryValidator(this, handler).validate();
  }

  public String getName() {
    return name;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }


}
