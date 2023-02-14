package br.com.almir.application.subcategory.create;

import br.com.almir.domain.category.Category;
import br.com.almir.domain.category.CategoryGateway;
import br.com.almir.domain.exceptions.DomainException;
import br.com.almir.domain.exceptions.NotFoundException;
import br.com.almir.domain.subcategory.Subcategory;
import br.com.almir.domain.subcategory.SubcategoryGateway;
import br.com.almir.domain.validation.Error;
import br.com.almir.domain.validation.handler.ThrowsValidationHandler;
import java.util.Objects;

public class DefaultCreateSubcategoryUseCase extends CreateSubcategoryUseCase {

  private final SubcategoryGateway subcategoryGateway;
  private final CategoryGateway categoryGateway;

  public DefaultCreateSubcategoryUseCase(SubcategoryGateway subcategoryGateway,
      CategoryGateway categoryGateway) {
    this.subcategoryGateway = Objects.requireNonNull(subcategoryGateway);
    this.categoryGateway = Objects.requireNonNull(categoryGateway);
  }

  @Override
  public CreateSubcategoryOutput execute(CreateSubcategoryCommand in) {

    Category category = this.categoryGateway.findById(in.categoryID())
        .orElseThrow(() -> NotFoundException.with(Category.class, in.categoryID()));

    final Subcategory newSubcategory = Subcategory.newSubcatergory(in.name(), category.getId());
    newSubcategory.validate(new ThrowsValidationHandler());

    final var subcategory = subcategoryGateway.create(newSubcategory);

    if (subcategory.getId() == null) {
      throw DomainException.with(new Error("Subcategory Gateway returned an 'id' null"));
    }

    return CreateSubcategoryOutput.from(subcategory);
  }
}
