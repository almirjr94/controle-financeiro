package br.com.almir.application.balance.categoria;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import br.com.almir.application.UseCaseTest;
import br.com.almir.domain.category.Category;
import br.com.almir.domain.category.CategoryGateway;
import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.exceptions.DomainException;
import br.com.almir.domain.exceptions.NotFoundException;
import br.com.almir.domain.financialrelease.FinancialRelease;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.subcategory.Subcategory;
import br.com.almir.domain.subcategory.SubcategoryGateway;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class GetBalanceByCategorieUseCaseTest extends UseCaseTest {

  @InjectMocks
  private DefaultGetBalanceByCategorieUseCase useCase;

  @Mock
  private CategoryGateway categoryGateway;
  @Mock
  private SubcategoryGateway subcategoryGateway;
  @Mock
  private FinancialReleaseGateway financialReleaseGateway;

  @Override
  protected List<Object> getMocks() {
    return List.of(categoryGateway, subcategoryGateway, financialReleaseGateway);
  }

  @Test
  void givenAValidCommand_whenCallGetBalanceByCategorie_shouldReturnOK() {
    final var initDate = LocalDate.of(2023, Month.JANUARY, 20);
    final var endDate = LocalDate.of(2023, Month.JANUARY, 24);
    final var categoryId = CategoryID.from(2L);

    final var moneyTen = FastMoney.of(10, "BRL");
    final var moneyFive = FastMoney.of(5, "BRL");
    final var moneyLessFive = FastMoney.of(-5.50, "BRL");

    final var expecterBalance = 9.50D;
    final var expectedWaste = -5.50D;
    final var expectedProfit = 15D;

    final var expectedCategory = Category.newCategory("Teste").with(CategoryID.from(6L));
    final var subcatergory = Subcategory.newSubcatergory("Teste teste", expectedCategory.getId())
        .with(SubcategoryID.from(65L));

    final var financialReleaseOne = FinancialRelease
        .newFinancialRelease(moneyTen, subcatergory.getId(), null, initDate.plusDays(1));
    final var financialReleaseTwo = FinancialRelease
        .newFinancialRelease(moneyFive, subcatergory.getId(), null, initDate.plusDays(1));

    final var financialReleaseThree = FinancialRelease
        .newFinancialRelease(moneyLessFive, subcatergory.getId(), null, initDate.plusDays(1));

    List<FinancialRelease> financialReleases = List.of(financialReleaseOne, financialReleaseTwo,
        financialReleaseThree);

    Mockito.when(categoryGateway.findById(categoryId)).thenReturn(Optional.of(expectedCategory));
    Mockito.when(subcategoryGateway.findByCategoryId(categoryId))
        .thenReturn(List.of(subcatergory.getId()));

    Mockito.when(financialReleaseGateway
            .findBySubCategoryIds(List.of(subcatergory.getId()), initDate, endDate))
        .thenReturn(financialReleases);

    final BalanceByCategoryOutput actualOutput = useCase.execute(
        BalanceByCategoryCommand.with(initDate, endDate, categoryId));

    Assertions.assertEquals(expectedProfit, actualOutput.profit());
    Assertions.assertEquals(expectedWaste, actualOutput.waste());
    Assertions.assertEquals(expecterBalance, actualOutput.balance());
    Assertions.assertEquals(expectedCategory.getName(), actualOutput.category().name());
    Assertions.assertEquals(expectedCategory.getId(), actualOutput.category().id());
  }

  @Test
  void givenAnInvalidCategory_whenCallGetBalanceByCategorie_shouldReturnError() {
    final var initDate = LocalDate.of(2023, Month.JANUARY, 20);
    final var endDate = LocalDate.of(2023, Month.JANUARY, 24);
    final var categoryId = CategoryID.from(2L);

    final var expectedErrorMessage = "Category with ID 2 was not found";

    Mockito.when(categoryGateway.findById(categoryId)).thenReturn(Optional.empty());

    final NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class,
        () -> useCase.execute(
            BalanceByCategoryCommand.with(initDate, endDate, categoryId)));

    Assertions.assertEquals(expectedErrorMessage, notFoundException.getMessage());

    Mockito.verify(subcategoryGateway, times(0)).findByCategoryId(any());

    Mockito.verify(financialReleaseGateway, times(0)).findBySubCategoryIds(any(), any(), any());
  }

  @Test
  void givenAnCategoryWithoutSubcategories_whenCallGetBalanceByCategorie_shouldReturnError() {
    final var initDate = LocalDate.of(2023, Month.JANUARY, 20);
    final var endDate = LocalDate.of(2023, Month.JANUARY, 24);
    final var categoryId = CategoryID.from(2L);

    final var expectedCategory = Category.newCategory("Teste").with(CategoryID.from(6L));

    final var expectedErrorMessage = "Category there is no balance";
    final var expectedNumErrors = 1;

    Mockito.when(categoryGateway.findById(categoryId)).thenReturn(Optional.of(expectedCategory));
    Mockito.when(subcategoryGateway.findByCategoryId(categoryId))
        .thenReturn(Collections.emptyList());

    final DomainException notFoundException = Assertions.assertThrows(DomainException.class,
        () -> useCase.execute(
            BalanceByCategoryCommand.with(initDate, endDate, categoryId)));

    Assertions.assertEquals(expectedErrorMessage, notFoundException.getErrors().get(0).message());
    Assertions.assertEquals(expectedNumErrors, notFoundException.getErrors().size());

    Mockito.verify(categoryGateway, times(1)).findById(categoryId);
    Mockito.verify(financialReleaseGateway, times(0)).findBySubCategoryIds(any(), any(), any());
  }
}