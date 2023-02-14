package br.com.almir.infrastructure.balance.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BalanceByCategoryResponse(
    BalanceCatergoryResponse category,
    double profit,
    double waste,
    double balance
) {

  public record BalanceCatergoryResponse(
      @JsonProperty("category_id") Long categoryId,
      String name
  ) {

  }
}
