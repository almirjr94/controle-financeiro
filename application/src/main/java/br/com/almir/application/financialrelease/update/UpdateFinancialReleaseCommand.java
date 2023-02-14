package br.com.almir.application.financialrelease.update;

import br.com.almir.domain.financialrelease.FinancialReleaseID;
import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateFinancialReleaseCommand(
    FinancialReleaseID id,
    BigDecimal money,
    String description,
    LocalDate releasedAt
) {

  public static UpdateFinancialReleaseCommand from(
      final FinancialReleaseID id,
      final BigDecimal money,
      final String description,
      final LocalDate releasedAt
  ) {
    return new UpdateFinancialReleaseCommand(id, money, description, releasedAt);
  }

}
