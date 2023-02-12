package br.com.almir.application.financialrelease.update;

import br.com.almir.domain.financialrelease.FinancialReleaseID;
import java.time.LocalDate;
import javax.money.MonetaryAmount;

public record UpdateFinancialReleaseCommand(
    FinancialReleaseID id,
    MonetaryAmount money,
    String description,
    LocalDate releasedAt
) {

  public static UpdateFinancialReleaseCommand from(
      final FinancialReleaseID id,
      final MonetaryAmount money,
      final String description,
      final LocalDate releasedAt
  ) {
    return new UpdateFinancialReleaseCommand(id, money, description, releasedAt);
  }

}
