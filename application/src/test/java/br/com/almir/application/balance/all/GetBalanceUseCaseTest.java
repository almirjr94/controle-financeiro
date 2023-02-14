package br.com.almir.application.balance.all;

import br.com.almir.application.UseCaseTest;
import br.com.almir.domain.financialrelease.FinancialRelease;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class GetBalanceUseCaseTest extends UseCaseTest {

  @InjectMocks
  private DefaultBalanceUseCase useCase;


  @Mock
  private FinancialReleaseGateway financialReleaseGateway;

  @Override
  protected List<Object> getMocks() {
    return List.of(financialReleaseGateway);
  }


  @Test
  void givenAValidCommand_whenCallGetBalance_shouldReturnOK() {
    final var initDate = LocalDate.of(2023, Month.JANUARY, 20);
    final var endDate = LocalDate.of(2023, Month.JANUARY, 24);

    final var moneyTen = FastMoney.of(10, "BRL");
    final var moneyFive = FastMoney.of(5, "BRL");
    final var moneyLessFive = FastMoney.of(-5.50, "BRL");

    final var subcatergory = SubcategoryID.from(65L);

    final var expecterBalance = 9.50D;
    final var expectedWaste = -5.50D;
    final var expectedProfit = 15D;

    final var financialReleaseOne = FinancialRelease
        .newFinancialRelease(moneyTen, subcatergory, null, initDate.plusDays(1));
    final var financialReleaseTwo = FinancialRelease
        .newFinancialRelease(moneyFive, subcatergory, null, initDate.plusDays(1));

    final var financialReleaseThree = FinancialRelease
        .newFinancialRelease(moneyLessFive, subcatergory, "teste", initDate.plusDays(1));

    List<FinancialRelease> financialReleases = List.of(financialReleaseOne, financialReleaseTwo,
        financialReleaseThree);

    Mockito.when(financialReleaseGateway.findAll(initDate, endDate)).thenReturn(financialReleases);

    BalanceOutput actualOutput = useCase.execute(BalanceCommand.with(initDate, endDate));

    Assertions.assertEquals(expectedProfit, actualOutput.profit());
    Assertions.assertEquals(expectedWaste, actualOutput.waste());
    Assertions.assertEquals(expecterBalance, actualOutput.balance());
  }

}