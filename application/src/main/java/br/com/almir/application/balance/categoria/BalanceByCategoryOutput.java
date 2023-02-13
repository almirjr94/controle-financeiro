package br.com.almir.application.balance.categoria;

public record BalanceByCategoryOutput(
    BalanceCategoryDescriptionOutput category,
    double profit,
    double waste,
    double balance
) {

  public static BalanceByCategoryOutput with(BalanceCategoryDescriptionOutput category,
      double profit,
      double waste,
      double balance) {
    return new BalanceByCategoryOutput(category, profit, waste, balance);
  }

}
