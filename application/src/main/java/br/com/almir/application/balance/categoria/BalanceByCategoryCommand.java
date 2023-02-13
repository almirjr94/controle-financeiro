package br.com.almir.application.balance.categoria;

import br.com.almir.domain.category.CategoryID;
import java.time.LocalDate;

public record BalanceByCategoryCommand(
    LocalDate initDate,
    LocalDate endDate,
    CategoryID categoryID
) {

}
