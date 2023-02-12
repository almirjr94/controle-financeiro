package br.com.almir.application.subcategory.update;

public record UpdateSubcategoryCommand(
    Long id,
    String name
) {

  public static UpdateSubcategoryCommand with(
      final Long id,
      final String name

  ) {
    return new UpdateSubcategoryCommand(id, name);
  }
}
