package br.com.almir.domain.financialrelease;


import br.com.almir.domain.validation.Error;
import br.com.almir.domain.validation.ValidationHandler;
import br.com.almir.domain.validation.Validator;

public class FinancialReleaseValidator extends Validator {

  private final FinancialRelease financialRelease;

  public FinancialReleaseValidator(final FinancialRelease financialRelease,
      final ValidationHandler aHandler) {
    super(aHandler);
    this.financialRelease = financialRelease;
  }

  @Override
  public void validate() {
    checkMoneyConstraints();
    checkFinancialReleaseIDContraints();
    checkReleasedAtConstraints();

  }

  private void checkMoneyConstraints() {
    final var money = this.financialRelease.getMoney();

    if (money == null) {
      this.validationHandler().append(new Error("'money' should not be null"));
      return;
    }

    if (money.isZero()) {
      this.validationHandler().append(new Error("'money' should be greater or less than zero"));
    }
  }

  private void checkFinancialReleaseIDContraints() {
    final var id = this.financialRelease.getSubcategoryID();

    if (id == null) {
      this.validationHandler().append(new Error("'subcategoryID' should not be null"));
    }
  }

  private void checkReleasedAtConstraints() {
    final var releasedAt = this.financialRelease.getReleasedAt();

    if (releasedAt == null) {
      this.validationHandler().append(new Error("releasedAt' should not be null"));
    }
  }
}