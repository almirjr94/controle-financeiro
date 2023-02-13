package br.com.almir.application.balance;

public record BalanceResult(
    double profit,
    double waste,
    double balance
) {

  public static BalanceResult with(
      final double profit,
      final double waste,
      final double balance) {
    return new BalanceResult(profit, waste, balance);
  }
}
