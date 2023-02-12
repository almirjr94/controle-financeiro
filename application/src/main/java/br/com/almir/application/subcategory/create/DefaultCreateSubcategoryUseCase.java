package br.com.almir.application.subcategory.create;

import br.com.almir.application.category.retrieve.get.GetCategoryByIdUseCase;
import br.com.almir.domain.exceptions.DomainException;
import br.com.almir.domain.subcategory.Subcategory;
import br.com.almir.domain.subcategory.SubcategoryGateway;
import br.com.almir.domain.validation.Error;
import br.com.almir.domain.validation.handler.ThrowsValidationHandler;
import java.util.Objects;

public class DefaultCreateSubcategoryUseCase extends CreateSubcategoryUseCase {

  private final SubcategoryGateway subcategoryGateway;
  private final GetCategoryByIdUseCase getCategoryByIdUseCase;

  public DefaultCreateSubcategoryUseCase(SubcategoryGateway categoryGateway,
      GetCategoryByIdUseCase getCategoryByIdUseCase) {
    this.subcategoryGateway = Objects.requireNonNull(categoryGateway);
    this.getCategoryByIdUseCase = Objects.requireNonNull(getCategoryByIdUseCase);
  }

  @Override
  public CreateSubcategoryOutput execute(CreateSubcategoryCommand in) {

    getCategoryByIdUseCase.execute(in.categoryID().getValue());

    final Subcategory newSubcategory = Subcategory.newSubcatergory(in.name(), in.categoryID());
    newSubcategory.validate(new ThrowsValidationHandler());

    final var subcategory = subcategoryGateway.create(newSubcategory);

    if (subcategory.getId() == null) {
      throw DomainException.with(new Error("Subcategory Gateway returned an 'id' null"));
    }

    return CreateSubcategoryOutput.from(subcategory);
  }
}
