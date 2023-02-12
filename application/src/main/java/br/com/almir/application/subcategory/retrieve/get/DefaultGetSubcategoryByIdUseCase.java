package br.com.almir.application.subcategory.retrieve.get;


import br.com.almir.domain.exceptions.NotFoundException;
import br.com.almir.domain.subcategory.Subcategory;
import br.com.almir.domain.subcategory.SubcategoryGateway;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.util.Objects;

public class DefaultGetSubcategoryByIdUseCase extends GetSubcategoryByIdUseCase {

  private final SubcategoryGateway subcategoryGateway;

  public DefaultGetSubcategoryByIdUseCase(final SubcategoryGateway subcategoryGateway) {
    this.subcategoryGateway = Objects.requireNonNull(subcategoryGateway);
  }

  @Override
  public SubcategoryOutput execute(final Long in) {
    final var subcategoryID = SubcategoryID.from(in);

    return this.subcategoryGateway.findById(subcategoryID)
        .map(SubcategoryOutput::from)
        .orElseThrow(() -> NotFoundException.with(Subcategory.class, subcategoryID));
  }
}
