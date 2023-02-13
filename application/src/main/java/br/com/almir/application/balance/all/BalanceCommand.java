package br.com.almir.application.balance.all;

import java.time.LocalDate;

public record BalanceCommand(
    LocalDate initDate,
    LocalDate endDate
) {

  public static BalanceCommand with(
      final LocalDate initDate,
      final LocalDate endDate
  ) {
    return new BalanceCommand(initDate, endDate);
  }

}
