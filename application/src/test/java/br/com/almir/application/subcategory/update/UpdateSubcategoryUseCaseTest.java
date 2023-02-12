package br.com.almir.application.subcategory.update;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import br.com.almir.application.UseCaseTest;
import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.exceptions.DomainException;
import br.com.almir.domain.subcategory.Subcategory;
import br.com.almir.domain.subcategory.SubcategoryGateway;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class UpdateSubcategoryUseCaseTest extends UseCaseTest {

  @InjectMocks
  private DefaultUpdateSubcategoryUseCase useCase;

  @Mock
  private SubcategoryGateway subcategoryGateway;

  @Override
  protected List<Object> getMocks() {
    return null;
  }

  @Test
  void givenAValidCommand_whenCallsUpdateSubcategory_shouldReturnSubcategoryId() {

    final var subCatergory = Subcategory.newSubcatergory("Test", CategoryID.from(2L))
        .with(SubcategoryID.from(2L));

    final var expectedName = "Teste";
    final var expectedId = subCatergory.getId();

    Mockito.when(subcategoryGateway.findById(expectedId)).thenReturn(Optional.of(subCatergory));
    Mockito.when(subcategoryGateway.update(any()))
        .thenReturn(Subcategory.newSubcatergory(expectedName, subCatergory.getCategoryID())
            .with(expectedId));

    final var command = UpdateSubcategoryCommand.with(expectedId.getValue(), expectedName);

    final var actualOutput = useCase.execute(command);

    Assertions.assertNotNull(actualOutput);
    Assertions.assertNotNull(actualOutput.id());

    Mockito.verify(subcategoryGateway, times(1)).findById(eq(expectedId));

    Mockito.verify(subcategoryGateway, times(1)).update(argThat(
        updatedSubcategory ->
            Objects.equals(expectedName, updatedSubcategory.getName())
                && Objects.equals(subCatergory.getCategoryID(), updatedSubcategory.getCategoryID())
                && Objects.equals(expectedId, updatedSubcategory.getId())
    ));
  }


  @Test
  void givenAInvalidName_whenCallsUpdateSubcategory_thenShouldReturnDomainException() {
    final var category =
        Subcategory.newSubcatergory("Tes", CategoryID.from(1L)).with(SubcategoryID.from(2L));

    final String expectedName = null;
    final var expectedId = category.getId();

    final var expectedErrorMessage = "'name' should not be null";
    final var expectedErrorCount = 1;

    final var aCommand = UpdateSubcategoryCommand.with(
        expectedId.getValue(),
        expectedName
    );

    when(subcategoryGateway.findById(eq(expectedId)))
        .thenReturn(Optional.of(category));

    DomainException domainException = Assertions.assertThrows(DomainException.class,
        () -> useCase.execute(aCommand));

    Assertions.assertEquals(expectedErrorCount, domainException.getErrors().size());
    Assertions.assertEquals(expectedErrorMessage, domainException.getErrors().get(0).message());

    Mockito.verify(subcategoryGateway, times(0)).update(any());
  }

}