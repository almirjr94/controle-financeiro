package br.com.almir.application.category.update;


import br.com.almir.domain.category.Category;

public record UpdateCategoryOutput(
    Long id
) {

  public static UpdateCategoryOutput from(final Long id) {
    return new UpdateCategoryOutput(id);
  }

  public static UpdateCategoryOutput from(final Category category) {
    return new UpdateCategoryOutput(category.getId().getValue());
  }
}
