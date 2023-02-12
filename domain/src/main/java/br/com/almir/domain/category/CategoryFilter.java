package br.com.almir.domain.category;

public class CategoryFilter {

  private String name;

  private CategoryFilter(String name) {
    this.name = name;
  }

  private static CategoryFilter with(String name) {
    return new CategoryFilter(name);
  }

  public String getName() {
    return name;
  }
}
