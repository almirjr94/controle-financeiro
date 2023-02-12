package br.com.almir.application.subcategory.delete;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

import br.com.almir.application.UseCaseTest;
import br.com.almir.domain.subcategory.SubcategoryGateway;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class DeleteSubcategoryUseCaseTest extends UseCaseTest {

  @InjectMocks
  private DefaultDeleteSubcategoryUseCase useCase;

  @Mock
  private SubcategoryGateway subCategoryGateway;

  @Override
  protected List<Object> getMocks() {
    return List.of(subCategoryGateway);
  }



  @Test
  void givenAValidId_whenCallsDeleteCategory_shouldBeOK() {
    final var expectedId = SubcategoryID.from(2l);

    doNothing()
        .when(subCategoryGateway).deleteById(eq(expectedId));

    Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

    Mockito.verify(subCategoryGateway, times(1)).deleteById(eq(expectedId));
  }

  @Test
  void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
    final var expectedId = SubcategoryID.from(2l);

    doThrow(new IllegalStateException("Gateway error"))
        .when(subCategoryGateway).deleteById(eq(expectedId));

    Assertions.assertThrows(IllegalStateException.class,
        () -> useCase.execute(expectedId.getValue()));

    Mockito.verify(subCategoryGateway, times(1)).deleteById(eq(expectedId));
  }
}