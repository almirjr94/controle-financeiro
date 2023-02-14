package br.com.almir.application.category.update;


import br.com.almir.domain.category.Category;
import br.com.almir.domain.category.CategoryGateway;
import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.exceptions.DomainException;
import br.com.almir.domain.exceptions.NotFoundException;
import br.com.almir.domain.validation.Error;
import br.com.almir.domain.validation.handler.ThrowsValidationHandler;
import java.util.Objects;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

  private final CategoryGateway categoryGateway;

  public DefaultUpdateCategoryUseCase(final CategoryGateway categoryGateway) {
    this.categoryGateway = Objects.requireNonNull(categoryGateway);
  }

  @Override
  public UpdateCategoryOutput execute(final UpdateCategoryCommand in) {
    final var id = CategoryID.from(in.id());
    final var name = in.name();

    final var category = this.categoryGateway.findById(id)
        .orElseThrow(() -> NotFoundException.with(Category.class, id));

    if (categoryGateway.existByName(in.name())) {
      throw DomainException.with(
          new Error(String.format("Category with name %s already exists", in.name())));
    }

    category
        .update(name)
        .validate(new ThrowsValidationHandler());

    this.categoryGateway.update(category);
    return UpdateCategoryOutput.from(category);
  }


}
