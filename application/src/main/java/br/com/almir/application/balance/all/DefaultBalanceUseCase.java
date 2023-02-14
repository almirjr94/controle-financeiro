package br.com.almir.application.balance.all;

import br.com.almir.application.balance.BalanceHelper;
import br.com.almir.application.balance.BalanceResult;
import br.com.almir.domain.financialrelease.FinancialRelease;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import java.util.List;
import java.util.Objects;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.FastMoney;

public class DefaultBalanceUseCase extends BalanceUseCase {

  public static final String THERE_IS_NO_BALANCE = "Category there is no balance";
  public static final FastMoney ZERO = FastMoney.of(0, "BRL");


  private final FinancialReleaseGateway financialReleaseGateway;

  public DefaultBalanceUseCase(
      FinancialReleaseGateway financialReleaseGateway
  ) {

    this.financialReleaseGateway = Objects.requireNonNull(financialReleaseGateway);
  }

  @Override
  public BalanceOutput execute(BalanceCommand command) {

    List<MonetaryAmount> monetaryAmounts = financialReleaseGateway
        .findAll(command.initDate(), command.endDate())
        .stream()
        .map(FinancialRelease::getMoney).toList();

    BalanceResult balanceResult = BalanceHelper.total(monetaryAmounts);

    return BalanceOutput
        .with(balanceResult.profit(), balanceResult.waste(), balanceResult.balance());
  }

}
