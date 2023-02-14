package br.com.almir.infrastructure.balance.presenters;

import br.com.almir.application.balance.all.BalanceOutput;
import br.com.almir.application.balance.categoria.BalanceByCategoryOutput;
import br.com.almir.infrastructure.balance.model.BalanceByCategoryResponse;
import br.com.almir.infrastructure.balance.model.BalanceByCategoryResponse.BalanceCatergoryResponse;
import br.com.almir.infrastructure.balance.model.BalanceResponse;

public interface BalancePresenter {

  static BalanceResponse present(final BalanceOutput output) {
    return new BalanceResponse(output.profit(), output.waste(), output.balance());
  }

  static BalanceByCategoryResponse present(final BalanceByCategoryOutput output) {
    BalanceCatergoryResponse catergory =
        new BalanceCatergoryResponse(output.category().id().getValue(), output.category().name());

    return new BalanceByCategoryResponse(catergory, output.profit(), output.waste(),
        output.balance());
  }

}
