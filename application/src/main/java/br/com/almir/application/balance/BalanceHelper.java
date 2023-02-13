package br.com.almir.application.balance;

import java.util.List;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.FastMoney;

public class BalanceHelper {

  private BalanceHelper() {
  }

  private static final FastMoney ZERO = FastMoney.of(0, "BRL");

  private static double getProfit(List<MonetaryAmount> financialReleases) {
    return financialReleases.stream()
        .filter(MonetaryAmount::isPositive)
        .reduce(MonetaryAmount::add).orElse(ZERO)
        .getNumber().doubleValueExact();
  }

  private static double getWaste(List<MonetaryAmount> financialReleases) {
    return financialReleases.stream()
        .filter(MonetaryAmount::isNegative)
        .reduce(MonetaryAmount::add).orElse(ZERO)
        .getNumber().doubleValueExact();
  }

  private static double getBalance(List<MonetaryAmount> financialReleases) {
    return financialReleases.stream()
        .reduce(MonetaryAmount::add)
        .orElse(ZERO).getNumber().doubleValueExact();
  }

  public static BalanceResult total(List<MonetaryAmount> financialReleases) {
    return BalanceResult.with(
        getProfit(financialReleases),
        getWaste(financialReleases),
        getBalance(financialReleases));
  }
}
