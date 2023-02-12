package br.com.almir.application.subcategory.create;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import br.com.almir.application.UseCaseTest;
import br.com.almir.application.category.create.CreateCategoryCommand;
import br.com.almir.application.category.retrieve.get.GetCategoryByIdUseCase;
import br.com.almir.domain.category.Category;
import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.exceptions.DomainException;
import br.com.almir.domain.exceptions.NotFoundException;
import br.com.almir.domain.subcategory.Subcategory;
import br.com.almir.domain.subcategory.SubcategoryGateway;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class CreateSubcategoryUseCaseTest extends UseCaseTest {

  @InjectMocks
  private DefaultCreateSubcategoryUseCase useCase;

  @Mock
  private SubcategoryGateway subcategoryGateway;

  @Mock
  private GetCategoryByIdUseCase getCategoryByIdUseCase;

  @Override
  protected List<Object> getMocks() {
    return null;
  }

  @Test
  void givenAValidCommand_whenCallsCreateSubcategory_shouldReturnSubcategoryId() {
    final var expectedName = "Test";
    final var expectedCategoryID = CategoryID.from(1L);
    final var expectedID = SubcategoryID.from(3L);

    CreateSubcategoryCommand command = CreateSubcategoryCommand.with(expectedName,
        expectedCategoryID);

    when(subcategoryGateway.create(any()))
        .thenReturn(Subcategory.newSubcatergory(expectedName, expectedCategoryID).with(expectedID));

    CreateSubcategoryOutput actualOutput = useCase.execute(command);

    Assertions.assertNotNull(actualOutput);
    Assertions.assertEquals(expectedID.getValue(), actualOutput.id());

    Mockito.verify(subcategoryGateway, times(1))
        .create(argThat(subcatergory -> Objects.equals(expectedName, subcatergory.getName())
            && Objects.nonNull(subcatergory.getCreatedAt())
            && Objects.nonNull(subcatergory.getUpdatedAt())
            && Objects.equals(expectedCategoryID, subcatergory.getCategoryID())
        ));
  }

  @Test
  void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnDomainException() {
    final String expectedName = null;
    final var expectedCategoryID = CategoryID.from(1L);

    final var expectedErrorMessage = "'name' should not be null";
    final var expectedErrorCount = 1;

    final var command =
        CreateSubcategoryCommand.with(expectedName,expectedCategoryID);

    DomainException actualException = Assertions.assertThrows(DomainException.class,
        () -> useCase.execute(command));

    Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

    Mockito.verify(subcategoryGateway, times(0)).create(any());
  }

  @Test
  void givenAInvalidCategoryID_whenCallsCreateCategory_thenShouldReturnDomainException() {
    final String expectedName = "Teste";
    final var categoryID = CategoryID.from(1L);

    final var expectedErrorMessage = "Category with ID 1 was not found";

    Mockito.when(getCategoryByIdUseCase.execute(categoryID.getValue())).
        thenThrow(NotFoundException.with(Category.class, categoryID));

    final var command =
        CreateSubcategoryCommand.with(expectedName,categoryID);

    NotFoundException actualException = Assertions.assertThrows(NotFoundException.class,
        () -> useCase.execute(command));

    Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

    Mockito.verify(subcategoryGateway, times(0)).create(any());
  }
}