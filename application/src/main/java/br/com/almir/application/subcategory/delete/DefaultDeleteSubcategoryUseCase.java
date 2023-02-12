package br.com.almir.application.subcategory.delete;

import br.com.almir.domain.subcategory.SubcategoryGateway;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.util.Objects;

public class DefaultDeleteSubcategoryUseCase extends DeleteSubcategoryUseCase {

  private final SubcategoryGateway subcategoryGateway;

  public DefaultDeleteSubcategoryUseCase(final SubcategoryGateway subcategoryGateway) {
    this.subcategoryGateway = Objects.requireNonNull(subcategoryGateway);
  }

  @Override
  public void execute(final Long in) {
    this.subcategoryGateway.deleteById(SubcategoryID.from(in));
  }
}
