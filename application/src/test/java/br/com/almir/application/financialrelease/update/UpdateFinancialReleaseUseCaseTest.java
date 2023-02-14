package br.com.almir.application.financialrelease.update;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

import br.com.almir.application.UseCaseTest;
import br.com.almir.domain.exceptions.DomainException;
import br.com.almir.domain.financialrelease.FinancialRelease;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.financialrelease.FinancialReleaseID;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.money.Monetary;
import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class UpdateFinancialReleaseUseCaseTest extends UseCaseTest {

  @InjectMocks
  private DefaultUpdateFinancialReleaseUseCase useCase;

  @Mock
  private FinancialReleaseGateway financialReleaseGateway;

  @Override
  protected List<Object> getMocks() {
    return List.of(financialReleaseGateway);
  }

  @Test
  void givenAValidCommand_whenCallsUpdateFinancialRelease_shouldReturnFinancialReleaseId() {
    final var money = FastMoney.of(BigDecimal.valueOf(2), Monetary.getCurrency("BRL"));
    final var subcategoryID = SubcategoryID.from(3L);
    final var description = "La la la la";
    final var releasedAt = LocalDate.of(2023, Month.JANUARY, 3);
    final var id = FinancialReleaseID.from(2L);

    final var financialRelease = FinancialRelease.newFinancialRelease(money,
        subcategoryID, description, releasedAt).with(id);

    final var expectedMoney = BigDecimal.valueOf(4);
    final var expectedDescription = "La la la test";
    final var expectedReleased = LocalDate.of(2023, Month.JANUARY, 3);

    UpdateFinancialReleaseCommand command = UpdateFinancialReleaseCommand.from(
        id,
        expectedMoney,
        expectedDescription,
        expectedReleased
    );

    Mockito.when(financialReleaseGateway.findById(id)).thenReturn(Optional.of(financialRelease));
    Mockito.when(financialReleaseGateway.update(any())).thenReturn(
        FinancialRelease.newFinancialRelease(FastMoney.of(expectedMoney, "BRL"), subcategoryID,
            expectedDescription,
            expectedReleased));

    UpdateFinancialReleaseOutput actualOutput = useCase.execute(command);

    Assertions.assertNotNull(actualOutput);
    Assertions.assertNotNull(actualOutput.id());

    Mockito.verify(financialReleaseGateway, times(1)).findById(eq(id));

    Mockito.verify(financialReleaseGateway, times(1)).update(argThat(
        updatedFinancialRelease ->
            Objects.equals(id, updatedFinancialRelease.getId())
                && Objects.equals(expectedMoney, updatedFinancialRelease.getMoney())
                && Objects.equals(expectedReleased, updatedFinancialRelease.getReleasedAt())
                && Objects.equals(expectedDescription, updatedFinancialRelease.getDescription())
    ));
  }

  @Test
  void givenAInvalidName_whenCallsUpdateFinancialRelease_thenShouldReturnDomainException() {
    final var money = FastMoney.of(BigDecimal.valueOf(2), Monetary.getCurrency("BRL"));
    final SubcategoryID subcategoryID = SubcategoryID.from(3L);
    final var description = "La la la la";
    final var releasedAt = LocalDate.of(2023, Month.JANUARY, 3);
    final var id = FinancialReleaseID.from(2L);

    final var financialRelease = FinancialRelease.newFinancialRelease(money,
        subcategoryID, description, releasedAt).with(id);

    final var expectedMoney = BigDecimal.valueOf(0);
    final var expectedDescription = "La la la test";
    final var expectedReleased = LocalDate.of(2023, Month.JANUARY, 3);

    Mockito.when(financialReleaseGateway.findById(id)).thenReturn(Optional.of(financialRelease));

    UpdateFinancialReleaseCommand command = UpdateFinancialReleaseCommand.from(
        id,
        expectedMoney,
        expectedDescription,
        expectedReleased
    );

    final var expectedErrorMessage = "'money' should be greater or less than zero";
    final var expectedErrorCount = 1;

    DomainException domainException = Assertions.assertThrows(DomainException.class,
        () -> useCase.execute(command));

    Assertions.assertEquals(expectedErrorCount, domainException.getErrors().size());
    Assertions.assertEquals(expectedErrorMessage, domainException.getErrors().get(0).message());

    Mockito.verify(financialReleaseGateway, times(0)).update(any());
  }

}