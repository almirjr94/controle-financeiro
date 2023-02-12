package br.com.almir.domain.subcategory;

public class SubcategoryFilter {
  private SubcategoryID id;
  private String name;

  private SubcategoryFilter(SubcategoryID id, String name) {
    this.id = id;
    this.name = name;
  }

  public static SubcategoryFilter with(SubcategoryID id, String name){
    return new SubcategoryFilter(id, name);
  }

  public SubcategoryID getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
