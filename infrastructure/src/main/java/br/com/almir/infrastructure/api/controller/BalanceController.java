package br.com.almir.infrastructure.api.controller;

import br.com.almir.application.balance.all.BalanceCommand;
import br.com.almir.application.balance.all.BalanceUseCase;
import br.com.almir.application.balance.categoria.BalanceByCategorieUseCase;
import br.com.almir.application.balance.categoria.BalanceByCategoryCommand;
import br.com.almir.domain.category.CategoryID;
import br.com.almir.infrastructure.api.BalanceAPI;
import br.com.almir.infrastructure.balance.presenters.BalancePresenter;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController implements BalanceAPI {

  private final BalanceUseCase balanceUseCase;
  private final BalanceByCategorieUseCase balanceByCategorieUseCase;


  public BalanceController(BalanceUseCase balanceUseCase,
      BalanceByCategorieUseCase balanceByCategorieUseCase) {
    this.balanceUseCase = balanceUseCase;
    this.balanceByCategorieUseCase = balanceByCategorieUseCase;
  }

  @Override
  public ResponseEntity<?> getByDateBetween(LocalDate initDate, LocalDate endDate,
      Long categoryId) {
    if (categoryId == null) {
      BalanceCommand command = BalanceCommand.with(initDate, endDate);
      return ResponseEntity.ok(BalancePresenter.present(balanceUseCase.execute(command)));
    }

    BalanceByCategoryCommand command = BalanceByCategoryCommand.with(initDate, endDate,
        CategoryID.from(categoryId));

    return ResponseEntity.ok(BalancePresenter.present(balanceByCategorieUseCase.execute(command)));
  }
}
