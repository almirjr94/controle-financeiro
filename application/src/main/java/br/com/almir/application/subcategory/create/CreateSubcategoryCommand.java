package br.com.almir.application.subcategory.create;

import br.com.almir.domain.category.CategoryID;

public record CreateSubcategoryCommand(
    String name,
    CategoryID categoryID
) {

  public static CreateSubcategoryCommand with(
      final String name,
      final CategoryID categoryID
  ) {
    return new CreateSubcategoryCommand(name, categoryID);
  }
}
