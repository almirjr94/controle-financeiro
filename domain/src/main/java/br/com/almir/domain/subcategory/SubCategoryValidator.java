package br.com.almir.domain.subcategory;


import br.com.almir.domain.validation.Error;
import br.com.almir.domain.validation.ValidationHandler;
import br.com.almir.domain.validation.Validator;

public class SubCategoryValidator extends Validator {

  private final Subcategory subcategory;

  public SubCategoryValidator(final Subcategory subcategory, final ValidationHandler handler) {
    super(handler);
    this.subcategory = subcategory;
  }

  @Override
  public void validate() {
    checkNameConstraints();
  }

  private void checkNameConstraints() {
    final var name = this.subcategory.getName();
    if (name == null) {
      this.validationHandler().append(new Error("'name' should not be null"));
      return;
    }

    if (name.isBlank()) {
      this.validationHandler().append(new Error("'name' should not be empty"));
    }
  }
}
