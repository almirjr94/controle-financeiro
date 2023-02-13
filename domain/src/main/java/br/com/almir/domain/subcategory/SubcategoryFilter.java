package br.com.almir.domain.subcategory;

public record SubcategoryFilter(
    SubcategoryID id,
    String name
) {

  public static SubcategoryFilter with(SubcategoryID id, String name) {
    return new SubcategoryFilter(id, name);
  }

}
