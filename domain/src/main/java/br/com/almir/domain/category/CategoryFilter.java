package br.com.almir.domain.category;

public record CategoryFilter(
    String name
) {

  private static CategoryFilter with(String name) {
    return new CategoryFilter(name);
  }

}
