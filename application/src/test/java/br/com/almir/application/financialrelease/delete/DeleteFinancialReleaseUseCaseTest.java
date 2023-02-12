package br.com.almir.application.financialrelease.delete;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

import br.com.almir.application.UseCaseTest;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.financialrelease.FinancialReleaseID;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class DeleteFinancialReleaseUseCaseTest extends UseCaseTest {


  @InjectMocks
  private DefaultDeleteFinancialReleaseUseCase useCase;

  @Mock
  private FinancialReleaseGateway financialReleaseGateway;

  @Override
  protected List<Object> getMocks() {
    return List.of(financialReleaseGateway);
  }

  @Test
  void givenAValidId_whenCallsFinancialRelease_shouldBeOK() {
    final var expectedId = FinancialReleaseID.from(2L);

    doNothing()
        .when(financialReleaseGateway).deleteById(eq(expectedId));

    Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

    Mockito.verify(financialReleaseGateway, times(1)).deleteById(eq(expectedId));
  }

  @Test
  void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
    final var expectedId = FinancialReleaseID.from(2L);

    doThrow(new IllegalStateException("Gateway error"))
        .when(financialReleaseGateway).deleteById(eq(expectedId));

    Assertions.assertThrows(IllegalStateException.class,
        () -> useCase.execute(expectedId.getValue()));

    Mockito.verify(financialReleaseGateway, times(1)).deleteById(eq(expectedId));
  }

}