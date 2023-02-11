package br.com.almir.domain.financialrelease;


import br.com.almir.domain.Identifier;
import java.util.Objects;

public class FinancialReleaseID extends Identifier {

  private final Long value;

  private FinancialReleaseID(final Long value) {
    Objects.requireNonNull(value);
    if (Long.signum(value) != 1) {
      throw new IllegalArgumentException("FinancialRelease 'id' should be positive");
    }
    this.value = value;
  }

  public static FinancialReleaseID from(final Long anId) {
    return new FinancialReleaseID(anId);
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
    final FinancialReleaseID that = (FinancialReleaseID) o;
    return getValue().equals(that.getValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getValue());
  }
}
