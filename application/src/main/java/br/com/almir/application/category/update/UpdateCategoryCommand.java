package br.com.almir.application.category.update;

public record UpdateCategoryCommand(
    Long id,
    String name
) {

  public static UpdateCategoryCommand with(
      final Long id,
      final String name

  ) {
    return new UpdateCategoryCommand(id, name);
  }
}
