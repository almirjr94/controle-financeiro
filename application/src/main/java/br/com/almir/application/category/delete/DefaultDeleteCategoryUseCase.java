package br.com.almir.application.category.delete;

import br.com.almir.domain.category.CategoryGateway;
import br.com.almir.domain.category.CategoryID;
import java.util.Objects;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {

  private final CategoryGateway categoryGateway;

  public DefaultDeleteCategoryUseCase(final CategoryGateway categoryGateway) {
    this.categoryGateway = Objects.requireNonNull(categoryGateway);
  }

  @Override
  public void execute(final Long in) {
    this.categoryGateway.deleteById(CategoryID.from(in));
  }
}
