package br.com.almir.application.balance.categoria;

import br.com.almir.domain.category.CategoryID;

public record BalanceCategoryDescriptionOutput(
    CategoryID id,
    String name
) {

  public static BalanceCategoryDescriptionOutput with(CategoryID id, String name) {
    return new BalanceCategoryDescriptionOutput(id, name);
  }
}
