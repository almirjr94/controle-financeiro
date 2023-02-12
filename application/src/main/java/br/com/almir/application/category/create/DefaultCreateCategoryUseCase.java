package br.com.almir.application.category.create;

import br.com.almir.domain.category.Category;
import br.com.almir.domain.category.CategoryGateway;
import br.com.almir.domain.exceptions.DomainException;
import br.com.almir.domain.validation.Error;
import br.com.almir.domain.validation.handler.ThrowsValidationHandler;
import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

  private final CategoryGateway categoryGateway;

  public DefaultCreateCategoryUseCase(CategoryGateway categoryGateway) {
    this.categoryGateway = Objects.requireNonNull(categoryGateway);
  }

  @Override
  public CreateCategoryOutput execute(CreateCategoryCommand in) {
    final Category newCategory = Category.newCategory(in.name());
    newCategory.validate(new ThrowsValidationHandler());
    final var category = categoryGateway.create(newCategory);
    if (category.getId() == null) {
      throw DomainException.with(new Error("Category Gateway returned an 'id' null"));
    }
    return CreateCategoryOutput.from(category);
  }
}
