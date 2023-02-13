package br.com.almir.application.balance.categoria;

import br.com.almir.domain.category.CategoryID;
import java.time.LocalDate;

public record BalanceByCategoryCommand(
    LocalDate initDate,
    LocalDate endDate,
    CategoryID categoryID
) {

  public static BalanceByCategoryCommand with(
      final LocalDate initDate,
      final LocalDate endDate,
      final CategoryID categoryID
  ) {
    return new BalanceByCategoryCommand(initDate, endDate, categoryID);
  }

}
