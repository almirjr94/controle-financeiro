package br.com.almir.application.subcategory.update;


import br.com.almir.domain.subcategory.Subcategory;

public record UpdateSubcategoryOutput(
    Long id
) {

  public static UpdateSubcategoryOutput from(final Long anid) {
    return new UpdateSubcategoryOutput(anid);
  }

  public static UpdateSubcategoryOutput from(final Subcategory subcategory) {
    return new UpdateSubcategoryOutput(subcategory.getId().getValue());
  }
}
