package br.com.almir.application.balance.all;

public record BalanceOutput(
    double profit,
    double waste,
    double balance
) {

  public static BalanceOutput with(
      final double profit,
      final double waste,
      final double balance) {
    return new BalanceOutput(profit, waste, balance);
  }

}
