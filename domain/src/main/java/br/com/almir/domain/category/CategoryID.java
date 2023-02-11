package br.com.almir.domain.category;


import br.com.almir.domain.Identifier;
import java.util.Objects;

public class CategoryID extends Identifier {

  private final Long value;

  private CategoryID(final Long value) {
    Objects.requireNonNull(value);
    if (Long.signum(value) != 1) {
      throw new IllegalArgumentException("Category 'id' should be positive");
    }
    this.value = value;
  }

  public static CategoryID from(final Long anId) {
    return new CategoryID(anId);
  }


  @Override
  public Long getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final CategoryID that = (CategoryID) o;
    return getValue().equals(that.getValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getValue());
  }
}
