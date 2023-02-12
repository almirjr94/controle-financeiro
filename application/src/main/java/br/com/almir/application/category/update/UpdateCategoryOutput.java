package br.com.almir.application.category.update;


import br.com.almir.domain.category.Category;

public record UpdateCategoryOutput(
    Long id
) {

  public static UpdateCategoryOutput from(final Long anId) {
    return new UpdateCategoryOutput(anId);
  }

  public static UpdateCategoryOutput from(final Category aCategory) {
    return new UpdateCategoryOutput(aCategory.getId().getValue());
  }
}
