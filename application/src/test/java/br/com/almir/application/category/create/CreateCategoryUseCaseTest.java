package br.com.almir.application.category.create;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import br.com.almir.application.UseCaseTest;
import br.com.almir.domain.category.Category;
import br.com.almir.domain.category.CategoryGateway;
import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.exceptions.DomainException;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class CreateCategoryUseCaseTest extends UseCaseTest {

  @InjectMocks
  private DefaultCreateCategoryUseCase useCase;

  @Mock
  private CategoryGateway categoryGateway;

  @Override
  protected List<Object> getMocks() {
    return List.of(categoryGateway);
  }

  @Test
  void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() {
    final var expectedName = "Test";
    final var expectedID = CategoryID.from(1L);

    final var aCommand = CreateCategoryCommand.with(expectedName);

    when(categoryGateway.create(any()))
        .thenReturn(Category.newCategory(expectedName).with(expectedID));

    final var actualOutput = useCase.execute(aCommand);

    Assertions.assertNotNull(actualOutput);
    Assertions.assertEquals(expectedID.getValue(), actualOutput.id());

    Mockito.verify(categoryGateway, times(1))
        .create(argThat(category -> Objects.equals(expectedName, category.getName())
            && Objects.nonNull(category.getCreatedAt())
            && Objects.nonNull(category.getUpdatedAt())
        ));
  }

  @Test
  void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnDomainException() {
    final String expectedName = null;

    final var expectedErrorMessage = "'name' should not be null";
    final var expectedErrorCount = 1;

    final var command =
        CreateCategoryCommand.with(expectedName);

    DomainException actualException = Assertions.assertThrows(DomainException.class,
        () -> useCase.execute(command));

    Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

    Mockito.verify(categoryGateway, times(0)).create(any());
  }

  @Test
  void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
    final String expectedName = "teste";

    final var expectedErrorMessage = "Gateway error";

    final var command =
        CreateCategoryCommand.with(expectedName);

    when(categoryGateway.create(any()))
        .thenThrow(new IllegalStateException(expectedErrorMessage));

    IllegalStateException actualException = Assertions.assertThrows(IllegalStateException.class,
        () -> useCase.execute(command));

    Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

    Mockito.verify(categoryGateway, times(1))
        .create(argThat(category -> Objects.equals(expectedName, category.getName())
            && Objects.nonNull(category.getCreatedAt())
            && Objects.nonNull(category.getUpdatedAt())
        ));
  }

  @Test
  void givenAValidCommand_whenCallsCreateCategoryNotReturnId_shouldReturnAnError() {
    final var expectedName = "Test";
    final var expectedErrorMessage = "Category Gateway returned an 'id' null";
    final var expectedErrorNum = 1;

    final var aCommand = CreateCategoryCommand.with(expectedName);

    when(categoryGateway.create(any()))
        .thenReturn(Category.newCategory(expectedName));

    DomainException domainException = Assertions.assertThrows(DomainException.class,
        () -> useCase.execute(aCommand));

    Assertions.assertEquals(expectedErrorMessage, domainException.getErrors().get(0).message());
    Assertions.assertEquals(expectedErrorNum, domainException.getErrors().size());

    Mockito.verify(categoryGateway, times(1))
        .create(argThat(aCategory -> Objects.equals(expectedName, aCategory.getName())
            && Objects.nonNull(aCategory.getCreatedAt())
            && Objects.nonNull(aCategory.getUpdatedAt())
        ));
  }
}