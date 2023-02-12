package br.com.almir.application.category.delete;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

import br.com.almir.application.UseCaseTest;
import br.com.almir.domain.category.Category;
import br.com.almir.domain.category.CategoryGateway;
import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.exceptions.DomainException;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.financialrelease.FinancialReleaseID;
import br.com.almir.domain.subcategory.SubcategoryGateway;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class DeleteCategoryUseCaseTest extends UseCaseTest {

  @InjectMocks
  private DefaultDeleteCategoryUseCase useCase;

  @Mock
  private CategoryGateway categoryGateway;
  @Mock
  private SubcategoryGateway subcategoryGateway;
  @Mock
  private FinancialReleaseGateway financialReleaseGateway;

  @Override
  protected List<Object> getMocks() {
    return List.of(categoryGateway);
  }

  @Test
  void givenAValidId_whenCallsDeleteCategory_shouldBeOK() {
    final var category = Category.newCategory("Teste").with(CategoryID.from(2L));
    final var expectedId = category.getId();

    doNothing()
        .when(categoryGateway).deleteById(eq(expectedId));

    Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

    Mockito.verify(categoryGateway, times(1)).deleteById(eq(expectedId));
  }

  @Test
  void givenAInvalidId_whenCallsDeleteCategory_shouldBeOK() {
    final var expectedId = CategoryID.from(123L);

    doNothing()
        .when(categoryGateway).deleteById(eq(expectedId));

    Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

    Mockito.verify(categoryGateway, times(1)).deleteById(eq(expectedId));
  }

  @Test
  void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
    final var category = Category.newCategory("Teste").with(CategoryID.from(2L));
    final var expectedId = category.getId();

    doThrow(new IllegalStateException("Gateway error"))
        .when(categoryGateway).deleteById(eq(expectedId));

    Assertions.assertThrows(IllegalStateException.class,
        () -> useCase.execute(expectedId.getValue()));

    Mockito.verify(categoryGateway, times(1)).deleteById(eq(expectedId));
  }

  @Test
  void givenAValidIdWithFinancialReleases_whenCallsDeleteCategory_shouldReturnException() {
    final var expectedId = CategoryID.from(2L);
    final var subcategoryID = SubcategoryID.from(3L);

    final var expectedMessageError = String.format("CategoryID %s there are financial release",
        expectedId.getValue());

    Mockito.when(subcategoryGateway.findByCategoryId(expectedId)).thenReturn(List.of(subcategoryID));
    Mockito.when(financialReleaseGateway.findBySubCategoryIds(List.of(subcategoryID)))
        .thenReturn(List.of(FinancialReleaseID.from(1L)));


    DomainException domainException = Assertions.assertThrows(DomainException.class,
        () -> useCase.execute(expectedId.getValue()));

    Assertions.assertEquals(expectedMessageError, domainException.getMessage());

    Mockito.verify(categoryGateway, times(0)).deleteById(eq(expectedId));
  }

}