package br.com.almir.application.financialrelease.create;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import br.com.almir.application.UseCaseTest;
import br.com.almir.domain.exceptions.DomainException;
import br.com.almir.domain.financialrelease.FinancialRelease;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.financialrelease.FinancialReleaseID;
import br.com.almir.domain.subcategory.SubcategoryGateway;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import javax.money.Monetary;
import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class CreateFinancialReleaseUseCaseTest extends UseCaseTest {

  @InjectMocks
  private DefaultCreateFinancialReleaseUseCase useCase;

  @Mock
  private FinancialReleaseGateway financialReleaseGateway;
  @Mock
  private SubcategoryGateway subcategoryGateway;

  @Override
  protected List<Object> getMocks() {
    return List.of(financialReleaseGateway, subcategoryGateway);
  }

  @Test
  void givenAValidCommand_whenCallsCreateFinancialRelease_shouldReturnCreateFinancialReleaseId() {
    final var expectedMoney = FastMoney.of(BigDecimal.valueOf(2), Monetary.getCurrency("BRL"));
    final var expectedSubcategoryID = SubcategoryID.from(3L);
    final var expectedDescription = "La la la la";
    final var expectedReleasedAt = LocalDate.of(2023, Month.JANUARY, 3);

    final var expectedIsActive = true;
    final var expectedID = FinancialReleaseID.from(1L);

    final var aCommand = CreateFinancialReleaseCommand.with(BigDecimal.valueOf(2),
        expectedSubcategoryID, expectedDescription, expectedReleasedAt);

    when(financialReleaseGateway.create(any()))
        .thenReturn(FinancialRelease.newFinancialRelease(expectedMoney, expectedSubcategoryID,
            expectedDescription, expectedReleasedAt).with(expectedID));

    final var actualOutput = useCase.execute(aCommand);

    Assertions.assertNotNull(actualOutput);
    Assertions.assertEquals(expectedID.getValue(), actualOutput.id());

    Mockito.verify(financialReleaseGateway, times(1))
        .create(
            argThat(financialRelease -> Objects.equals(expectedMoney, financialRelease.getMoney())
                && Objects.nonNull(financialRelease.getCreatedAt())
                && Objects.nonNull(financialRelease.getUpdatedAt())
            ));
  }

  @Test
  void givenAInvalidSubCategory_whenCallsCreateFinancialRelease_thenShouldReturnDomainException() {
    final SubcategoryID expectedSubcategoryID = null;
    final var expectedDescription = "La la la la";
    final var expectedReleasedAt = LocalDate.of(2023, Month.JANUARY, 3);

    final var expectedErrorMessage = "'subcategoryID' should not be null";
    final var expectedErrorCount = 1;

    final var aCommand = CreateFinancialReleaseCommand.with(BigDecimal.valueOf(-2),
        expectedSubcategoryID, expectedDescription, expectedReleasedAt);

    DomainException domainException = Assertions.assertThrows(DomainException.class,
        () -> useCase.execute(aCommand));

    Assertions.assertEquals(expectedErrorCount, domainException.getErrors().size());
    Assertions.assertEquals(expectedErrorMessage, domainException.getErrors().get(0).message());

    Mockito.verify(financialReleaseGateway, times(0)).create(any());
  }
}