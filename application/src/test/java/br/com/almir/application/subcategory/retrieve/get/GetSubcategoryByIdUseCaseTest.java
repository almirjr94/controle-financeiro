package br.com.almir.application.subcategory.retrieve.get;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import br.com.almir.application.UseCaseTest;
import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.exceptions.NotFoundException;
import br.com.almir.domain.subcategory.Subcategory;
import br.com.almir.domain.subcategory.SubcategoryGateway;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class GetSubcategoryByIdUseCaseTest extends UseCaseTest {

  @InjectMocks
  private DefaultGetSubcategoryByIdUseCase useCase;

  @Mock
  private SubcategoryGateway subcategoryGateway;

  @Override
  protected List<Object> getMocks() {
    return List.of(subcategoryGateway);
  }

  @Test
  void givenAValidId_whenCallsGetSubCategory_shouldReturnCategory() {
    final var expectedName = "Teste";
    final var expectedCategoryID = CategoryID.from(4L);

    final var category = Subcategory.newSubcatergory(expectedName, expectedCategoryID)
        .with(SubcategoryID.from(2L));
    final var expectedId = category.getId();

    when(subcategoryGateway.findById(eq(expectedId)))
        .thenReturn(Optional.of(category));

    final var actualCategory = useCase.execute(expectedId.getValue());

    Assertions.assertEquals(expectedId, actualCategory.id());
    Assertions.assertEquals(expectedName, actualCategory.name());
    Assertions.assertEquals(expectedCategoryID, actualCategory.categoryID());
    Assertions.assertEquals(category.getCreatedAt(), actualCategory.createdAt());
    Assertions.assertEquals(category.getUpdatedAt(), actualCategory.updatedAt());
  }

  @Test
  void givenAInvalidId_whenCallsGetSubcategory_shouldReturnNotFound() {
    final var expectedErrorMessage = "Subcategory with ID 123 was not found";
    final var expectedId = SubcategoryID.from(123L);

    when(subcategoryGateway.findById(eq(expectedId)))
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
    final var expectedId = SubcategoryID.from(123L);

    when(subcategoryGateway.findById(eq(expectedId)))
        .thenThrow(new IllegalStateException(expectedErrorMessage));

    final var actualException = Assertions.assertThrows(
        IllegalStateException.class,
        () -> useCase.execute(expectedId.getValue())
    );

    Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
  }
}