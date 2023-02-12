package br.com.almir.application.subcategory.create;


import br.com.almir.domain.subcategory.Subcategory;

public record CreateSubcategoryOutput(
    Long id
) {

  public static CreateSubcategoryOutput from(final Long id) {
    return new CreateSubcategoryOutput(id);
  }

  public static CreateSubcategoryOutput from(final Subcategory category) {
    return new CreateSubcategoryOutput(category.getId().getValue());
  }
}
