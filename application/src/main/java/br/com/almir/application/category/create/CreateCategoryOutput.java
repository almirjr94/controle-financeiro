package br.com.almir.application.category.create;


import br.com.almir.domain.category.Category;

public record CreateCategoryOutput(
    Long id
) {

  public static CreateCategoryOutput from(final Long id) {
    return new CreateCategoryOutput(id);
  }

  public static CreateCategoryOutput from(final Category category) {
    return new CreateCategoryOutput(category.getId().getValue());
  }
}
