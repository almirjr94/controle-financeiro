package br.com.almir.application.category.retrieve.get;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import br.com.almir.application.UseCaseTest;
import br.com.almir.domain.category.Category;
import br.com.almir.domain.category.CategoryGateway;
import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.exceptions.NotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class GetCategoryByNameUseCaseTest extends UseCaseTest {

  @InjectMocks
  private DefaultGetCategoryByIdUseCase useCase;

  @Mock
  private CategoryGateway categoryGateway;

  @Override
  protected List<Object> getMocks() {
    return List.of(categoryGateway);
  }

  @Test
  void givenAValidId_whenCallsGetCategory_shouldReturnCategory() {
    final var expectedName = "Teste";

    final var category = Category.newCategory(expectedName).with(CategoryID.from(2L));
    final var expectedId = category.getId();

    when(categoryGateway.findById(eq(expectedId)))
        .thenReturn(Optional.of(category));

    final var actualCategory = useCase.execute(expectedId.getValue());

    Assertions.assertEquals(expectedId, actualCategory.id());
    Assertions.assertEquals(expectedName, actualCategory.name());
    Assertions.assertEquals(category.getCreatedAt(), actualCategory.createdAt());
    Assertions.assertEquals(category.getUpdatedAt(), actualCategory.updatedAt());
  }

  @Test
  void givenAInvalidId_whenCallsGetCategory_shouldReturnNotFound() {
    final var expectedErrorMessage = "Category with ID 123 was not found";
    final var expectedId = CategoryID.from(123L);

    when(categoryGateway.findById(eq(expectedId)))
        .thenReturn(Optional.empty());

    final var actualException = Assertions.assertThrows(
        NotFoundException.class,
        () -> useCase.execute(expectedId.getValue())
    );

    Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
  }

  @Test
  void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
    final var expectedErrorMessage = "Gateway error";
    final var expectedId = CategoryID.from(123L);

    when(categoryGateway.findById(eq(expectedId)))
        .thenThrow(new IllegalStateException(expectedErrorMessage));

    final var actualException = Assertions.assertThrows(
        IllegalStateException.class,
        () -> useCase.execute(expectedId.getValue())
    );

    Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
  }
}