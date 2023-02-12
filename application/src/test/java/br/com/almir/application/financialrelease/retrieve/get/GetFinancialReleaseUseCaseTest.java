package br.com.almir.application.financialrelease.retrieve.get;

import static org.mockito.ArgumentMatchers.eq;

import br.com.almir.application.UseCaseTest;
import br.com.almir.domain.exceptions.NotFoundException;
import br.com.almir.domain.financialrelease.FinancialRelease;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.financialrelease.FinancialReleaseID;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import javax.money.Monetary;
import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class GetFinancialReleaseUseCaseTest extends UseCaseTest {

  @InjectMocks
  private DefaultGetFinancialReleaseUseCase useCase;

  @Mock
  private FinancialReleaseGateway financialReleaseGateway;

  @Override
  protected List<Object> getMocks() {
    return List.of(financialReleaseGateway);
  }

  @Test
  void givenAValidId_whenCallsGetFinancialRelease_shouldReturnFinancialRelease() {
    final var expectedMoney = FastMoney.of(BigDecimal.valueOf(2), Monetary.getCurrency("BRL"));
    final var expectedSubcategoryID = SubcategoryID.from(3L);
    final var expectedDescription = "La la la la";
    final var expectedReleasedAt = LocalDate.of(2023, Month.JANUARY, 3);
    final var expectedId = FinancialReleaseID.from(2L);

    final var financialRelease = FinancialRelease.newFinancialRelease(expectedMoney,
        expectedSubcategoryID, expectedDescription, expectedReleasedAt).with(expectedId);

    Mockito.when(financialReleaseGateway.findById(eq(expectedId)))
        .thenReturn(Optional.of(financialRelease));

    FinancialReleaseOutput releaseOutput = useCase.execute(expectedId.getValue());

    Assertions.assertEquals(expectedMoney, releaseOutput.money());
    Assertions.assertEquals(expectedSubcategoryID, releaseOutput.subcategoryID());
    Assertions.assertEquals(expectedDescription, releaseOutput.describe());
    Assertions.assertEquals(expectedReleasedAt, releaseOutput.releasedAt());
    Assertions.assertEquals(expectedId, releaseOutput.financialReleaseID());
  }

  @Test
  void givenAInvalidId_whenCallsGetFinancialRelease_shouldReturnNotFound() {
    final var expectedId = FinancialReleaseID.from(2L);
    final var expectedErrorMessage = "FinancialRelease with ID 2 was not found";

    Mockito.when(financialReleaseGateway.findById(eq(expectedId)))
        .thenReturn(Optional.empty());

    final var actualException = Assertions.assertThrows(
        NotFoundException.class,
        () -> useCase.execute(expectedId.getValue())
    );

    Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
  }

  @Test
  void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
    final var expectedId = FinancialReleaseID.from(2L);
    final var expectedErrorMessage = "Gateway error";

    Mockito.when(financialReleaseGateway.findById(eq(expectedId)))
        .thenThrow(new IllegalStateException(expectedErrorMessage));

    final var actualException = Assertions.assertThrows(
        IllegalStateException.class,
        () -> useCase.execute(expectedId.getValue())
    );

    Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
  }
}