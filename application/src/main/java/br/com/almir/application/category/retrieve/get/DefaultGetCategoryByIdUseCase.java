package br.com.almir.application.category.retrieve.get;


import br.com.almir.domain.category.Category;
import br.com.almir.domain.category.CategoryGateway;
import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.exceptions.NotFoundException;
import java.util.Objects;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

  private final CategoryGateway categoryGateway;

  public DefaultGetCategoryByIdUseCase(final CategoryGateway categoryGateway) {
    this.categoryGateway = Objects.requireNonNull(categoryGateway);
  }

  @Override
  public CategoryOutput execute(final Long in) {
    final var categoryID = CategoryID.from(in);

    return this.categoryGateway.findById(categoryID)
        .map(CategoryOutput::from)
        .orElseThrow(() -> NotFoundException.with(Category.class, categoryID));
  }
}
