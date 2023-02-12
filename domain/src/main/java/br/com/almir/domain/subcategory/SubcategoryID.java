package br.com.almir.domain.subcategory;


import br.com.almir.domain.Identifier;
import java.util.Objects;

public class SubcategoryID extends Identifier {

  private final Long value;

  private SubcategoryID(final Long value) {
    Objects.requireNonNull(value);
    if (Long.signum(value) != 1) {
      throw new IllegalArgumentException("SubCategory 'id' should be positive");
    }
    this.value = value;
  }

  public static SubcategoryID from(final Long id) {
    return new SubcategoryID(id);
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
    final SubcategoryID that = (SubcategoryID) o;
    return getValue().equals(that.getValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getValue());
  }
}
