package br.com.almir.domain.subcategory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.exceptions.DomainException;
import br.com.almir.domain.validation.handler.ThrowsValidationHandler;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class SubcategoryTest {

  @Test
  void givenAValidParams_whenCallNewSubcategory_thenInstantiateASubcategory() {
    final var expectedId = SubcategoryID.from(1L);
    final var expectednName = "Test Category";
    final var expectedCategoryId = CategoryID.from(2L);

    final var subcategory = Subcategory.newSubcatergory(expectednName,
        expectedCategoryId).with(expectedId);

    assertNotNull(subcategory);
    assertEquals(expectedId, subcategory.getId());
    assertEquals(expectednName, subcategory.getName());
    assertEquals(expectedCategoryId, subcategory.getCategoryID());
    assertNotNull(subcategory.getCreatedAt());
    assertNotNull(subcategory.getUpdatedAt());
  }

  @Test
  void givenAnInvalidNullName_whenCallNewSubcategoryAndValidate_thenShouldReceiveError() {
    final var expectedId = SubcategoryID.from(1L);
    final String expectednName = null;
    final var expectedCategoryId = CategoryID.from(2L);

    final var expectedErrorCount = 1;
    final var expectedErrorMessage = "'name' should not be null";

    final var actualSubcategory = Subcategory.newSubcatergory(expectednName,
        expectedCategoryId);

    final var actualException =
        assertThrows(DomainException.class,
            () -> actualSubcategory.validate(new ThrowsValidationHandler()));

    assertEquals(expectedErrorCount, actualException.getErrors().size());
    assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
  }

  @Test
  void givenAnInvalidEmptyName_whenCallNewSubcategoryAndValidate_thenShouldReceiveError() {
    final var expectedId = SubcategoryID.from(1L);
    final String expectednName = "   ";
    final var expectedCategoryId = CategoryID.from(2L);

    final var expectedErrorCount = 1;
    final var expectedErrorMessage = "'name' should not be empty";

    final var actualSubcategory = Subcategory.newSubcatergory(expectednName,
        expectedCategoryId).with(expectedId);

    final var actualException =
        assertThrows(DomainException.class,
            () -> actualSubcategory.validate(new ThrowsValidationHandler()));

    assertEquals(expectedErrorCount, actualException.getErrors().size());
    assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
  }

  @Test
  void givenAValidSubcategory_whenCallUpdate_shouldReturnSubcategoryUpdated()
      throws InterruptedException {
    final var id = SubcategoryID.from(1L);
    final var name = "Test";
    final var categoryId = CategoryID.from(2L);

    final var expectedName = "Text Novo";

    final var actualSubcategory = Subcategory.newSubcatergory(name, categoryId).with(id);

    final var actualCreatedAt = actualSubcategory.getCreatedAt();
    final var actualUpdatedAt = actualSubcategory.getUpdatedAt();

    TimeUnit.MILLISECONDS.sleep(1);
    actualSubcategory.update(expectedName);

    assertEquals(expectedName, actualSubcategory.getName());
    assertEquals(actualCreatedAt, actualSubcategory.getCreatedAt());
    assertTrue(actualUpdatedAt.isBefore(actualSubcategory.getUpdatedAt()));
  }
}