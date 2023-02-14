package br.com.almir.application.subcategory.update;


import br.com.almir.domain.exceptions.DomainException;
import br.com.almir.domain.exceptions.NotFoundException;
import br.com.almir.domain.subcategory.Subcategory;
import br.com.almir.domain.subcategory.SubcategoryGateway;
import br.com.almir.domain.subcategory.SubcategoryID;
import br.com.almir.domain.validation.Error;
import br.com.almir.domain.validation.handler.ThrowsValidationHandler;
import java.util.Objects;

public class DefaultUpdateSubcategoryUseCase extends UpdateSubcategoryUseCase {

  private final SubcategoryGateway subcategoryGateway;

  public DefaultUpdateSubcategoryUseCase(final SubcategoryGateway subcategoryGateway) {
    this.subcategoryGateway = Objects.requireNonNull(subcategoryGateway);
  }

  @Override
  public UpdateSubcategoryOutput execute(final UpdateSubcategoryCommand in) {
    final var id = SubcategoryID.from(in.id());
    final var name = in.name();

    final var subcategory = this.subcategoryGateway.findById(id)
        .orElseThrow(() -> NotFoundException.with(Subcategory.class, id));

    if (subcategoryGateway.existsByCategoryIdAndName(subcategory.getCategoryID(), in.name())) {
      throw DomainException.with(
          new Error(String.format("Subcategory with name %s already exists", in.name())));
    }

    subcategory
        .update(name)
        .validate(new ThrowsValidationHandler());

    this.subcategoryGateway.update(subcategory);
    return UpdateSubcategoryOutput.from(subcategory);
  }


}
