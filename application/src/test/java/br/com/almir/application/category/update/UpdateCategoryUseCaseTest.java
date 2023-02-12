package br.com.almir.application.category.update;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import br.com.almir.application.UseCaseTest;
import br.com.almir.domain.category.Category;
import br.com.almir.domain.category.CategoryGateway;
import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.exceptions.DomainException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class UpdateCategoryUseCaseTest extends UseCaseTest {


  @InjectMocks
  private DefaultUpdateCategoryUseCase useCase;

  @Mock
  private CategoryGateway categoryGateway;

  @Override
  protected List<Object> getMocks() {
    return List.of(categoryGateway);
  }

  @Test
  void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() {
    final var category =
        Category.newCategory("Tes").with(CategoryID.from(2L));

    final var expectedName = "Teste";
    final var expectedId = category.getId();

    final var command = UpdateCategoryCommand.with(
        expectedId.getValue(),
        expectedName
    );

    when(categoryGateway.findById(eq(expectedId)))
        .thenReturn(Optional.of(category));

    when(categoryGateway.update(any()))
        .thenReturn(Category.newCategory("Teste").with(CategoryID.from(2L)));

    final var actualOutput = useCase.execute(command);

    Assertions.assertNotNull(actualOutput);
    Assertions.assertNotNull(actualOutput.id());

    Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));

    Mockito.verify(categoryGateway, times(1)).update(argThat(
        updatedCategory ->
            Objects.equals(expectedName, updatedCategory.getName())
                && Objects.equals(expectedId, updatedCategory.getId())
    ));
  }

  @Test
  void givenAInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() {
    final var category =
        Category.newCategory("Tes").with(CategoryID.from(2L));

    final String expectedName = null;
    final var expectedId = category.getId();

    final var expectedErrorMessage = "'name' should not be null";
    final var expectedErrorCount = 1;

    final var aCommand = UpdateCategoryCommand.with(
        expectedId.getValue(),
        expectedName
    );

    when(categoryGateway.findById(eq(expectedId)))
        .thenReturn(Optional.of(category));

    DomainException domainException = Assertions.assertThrows(DomainException.class,
        () -> useCase.execute(aCommand));

    Assertions.assertEquals(expectedErrorCount, domainException.getErrors().size());
    Assertions.assertEquals(expectedErrorMessage, domainException.getErrors().get(0).message());

    Mockito.verify(categoryGateway, times(0)).update(any());
  }
}