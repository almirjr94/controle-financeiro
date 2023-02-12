package br.com.almir.application.category.delete;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

import br.com.almir.application.UseCaseTest;
import br.com.almir.domain.category.Category;
import br.com.almir.domain.category.CategoryGateway;
import br.com.almir.domain.category.CategoryID;
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
    final var aCategory = Category.newCategory("Teste").with(CategoryID.from(2L));
    final var expectedId = aCategory.getId();

    doThrow(new IllegalStateException("Gateway error"))
        .when(categoryGateway).deleteById(eq(expectedId));

    Assertions.assertThrows(IllegalStateException.class,
        () -> useCase.execute(expectedId.getValue()));

    Mockito.verify(categoryGateway, times(1)).deleteById(eq(expectedId));
  }

}