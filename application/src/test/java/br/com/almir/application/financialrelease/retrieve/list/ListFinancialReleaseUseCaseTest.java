package br.com.almir.application.financialrelease.retrieve.list;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import br.com.almir.application.UseCaseTest;
import br.com.almir.domain.financialrelease.FinancialRelease;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.financialrelease.FinancialReleaseID;
import br.com.almir.domain.pagination.Pagination;
import br.com.almir.domain.pagination.SearchQuery;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import javax.money.Monetary;
import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class ListFinancialReleaseUseCaseTest extends UseCaseTest {

  @InjectMocks
  private DefaultListFinancialReleaseUseCase useCase;

  @Mock
  private FinancialReleaseGateway financialReleaseGateway;

  @Override
  protected List<Object> getMocks() {
    return List.of(financialReleaseGateway);
  }

  @Test
  void givenAValidQuery_whenCallsListFinancialRelease_thenShouldReturnFinancialReleases() {
    final var money = FastMoney.of(BigDecimal.valueOf(2), Monetary.getCurrency("BRL"));
    final var moneyTwo = FastMoney.of(BigDecimal.valueOf(3), Monetary.getCurrency("BRL"));

    final var subcategoryID = SubcategoryID.from(3L);
    final var description = "La la la la";
    final var releasedAt = LocalDate.of(2023, Month.JANUARY, 3);
    final var id = FinancialReleaseID.from(2L);
    final var idTwo = FinancialReleaseID.from(3L);

    final var financialRelease = FinancialRelease.newFinancialRelease(money,
        subcategoryID, description, releasedAt).with(id);
    final var financialReleaseTwo = FinancialRelease.newFinancialRelease(moneyTwo,
        subcategoryID, description, releasedAt).with(idTwo);

    final var financialReleases = List.of(financialReleaseTwo, financialRelease);

    final var expectedPage = 0;
    final var expectedPerPage = 10;
    final var expectedTerms = "";
    final var expectedSort = "createdAt";
    final var expectedDirection = "asc";

    final var query =
        new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort,
            expectedDirection);

    final var expectedPagination =
        new Pagination<>(expectedPage, expectedPerPage, financialReleases.size(),
            financialReleases);

    final var expectedItemsCount = 2;
    final var expectedResult = expectedPagination.map(FinancialReleaseListOutput::from);

    Mockito.when(financialReleaseGateway.findAll(eq(query))).thenReturn(expectedPagination);
    final var actualResult = useCase.execute(query);

    Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
    Assertions.assertEquals(expectedResult, actualResult);
    Assertions.assertEquals(expectedPage, actualResult.currentPage());
    Assertions.assertEquals(expectedPerPage, actualResult.perPage());
    Assertions.assertEquals(financialReleases.size(), actualResult.total());
  }

  @Test
  void givenAValidQuery_whenHasNoResults_thenShouldReturnEmptyFinancialReleases() {
    final var financialReleases = List.<FinancialRelease>of();

    final var expectedPage = 0;
    final var expectedPerPage = 10;
    final var expectedTerms = "";
    final var expectedSort = "createdAt";
    final var expectedDirection = "asc";

    final var query =
        new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort,
            expectedDirection);

    final var expectedPagination =
        new Pagination<>(expectedPage, expectedPerPage, financialReleases.size(),
            financialReleases);

    final var expectedItemsCount = 0;
    final var expectedResult = expectedPagination.map(FinancialReleaseListOutput::from);

    when(financialReleaseGateway.findAll(eq(query)))
        .thenReturn(expectedPagination);

    final var actualResult = useCase.execute(query);

    Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
    Assertions.assertEquals(expectedResult, actualResult);
    Assertions.assertEquals(expectedPage, actualResult.currentPage());
    Assertions.assertEquals(expectedPerPage, actualResult.perPage());
    Assertions.assertEquals(financialReleases.size(), actualResult.total());
  }

  @Test
  void givenAValidQuery_whenGatewayThrowsException_shouldReturnException() {
    final var expectedPage = 0;
    final var expectedPerPage = 10;
    final var expectedTerms = "";
    final var expectedSort = "createdAt";
    final var expectedDirection = "asc";
    final var expectedErrorMessage = "Gateway error";

    final var query =
        new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort,
            expectedDirection);

    when(financialReleaseGateway.findAll(eq(query)))
        .thenThrow(new IllegalStateException(expectedErrorMessage));

    final var actualException =
        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(query));

    Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
  }


}