package br.com.almir.domain.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.almir.domain.exceptions.DomainException;
import br.com.almir.domain.validation.handler.ThrowsValidationHandler;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class CategoryTest {

  @Test
  void givenAValidParams_whenCallNewCategory_thenInstantiateACategory() {
    final var expectedId = 1L;
    final var expectednName = "Test Category";

    final var category = Category.newCategory(expectedId, expectednName);

    assertNotNull(category);
    assertEquals(CategoryID.from(expectedId), category.getId());
    assertEquals(expectednName, category.getName());
    assertNotNull(category.getCreatedAt());
    assertNotNull(category.getUpdatedAt());

  }

  @Test
  void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
    final String expectedName = null;
    final var expectedErrorCount = 1;
    final var expectedErrorMessage = "'name' should not be null";

    final var actualCategory =
        Category.newCategory(1L, expectedName);

    final var actualException =
        assertThrows(DomainException.class,
            () -> actualCategory.validate(new ThrowsValidationHandler()));

    assertEquals(expectedErrorCount, actualException.getErrors().size());
    assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
  }

  @Test
  void givenANegativeId_whenCallNewCategory_thenShouldReceiveError() {
    final var expectedId = -1L;
    final var expectedName = "Loja";
    final var expectedErrorMessage = "Category 'id' should be positive";

    final var actualException =
        assertThrows(IllegalArgumentException.class,
            () -> Category.newCategory(expectedId, expectedName));

    assertEquals(expectedErrorMessage, actualException.getMessage());
  }

  @Test
  void givenAZeroId_whenCallNewCategory_thenShouldReceiveError() {
    final var expectedId = 0L;
    final var expectedName = "Loja";
    final var expectedErrorMessage = "Category 'id' should be positive";

    final var actualException =
        assertThrows(IllegalArgumentException.class,
            () -> Category.newCategory(expectedId, expectedName));

    assertEquals(expectedErrorMessage, actualException.getMessage());
  }

  @Test
  void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
    final var expectedId = 1L;
    final var expectedName = "  ";
    final var expectedErrorCount = 1;
    final var expectedErrorMessage = "'name' should not be empty";

    final var actualCategory =
        Category.newCategory(expectedId, expectedName);

    final var actualException =
        assertThrows(DomainException.class,
            () -> actualCategory.validate(new ThrowsValidationHandler()));

    assertEquals(expectedErrorCount, actualException.getErrors().size());
    assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
  }

  @Test
  void givenAnValidCategory_whenCallUpdate_thenReturnCategoryUpdated() throws InterruptedException {
    final var expectedName = "Teste2";

    final var actualCategory =
        Category.newCategory(2L, "Teste");

    final var actualCreatedAt = actualCategory.getCreatedAt();
    final var actualUpdatedAt = actualCategory.getUpdatedAt();

    TimeUnit.MILLISECONDS.sleep(1);
    actualCategory.update(expectedName);

    assertEquals(expectedName, actualCategory.getName());
    assertEquals(actualCreatedAt, actualCategory.getCreatedAt());
    assertTrue(actualUpdatedAt.isBefore(actualCategory.getUpdatedAt()));
  }

  @Test
  void testez(){
    final var actualCategory =
        Category.newCategory(2L, "Testee");
    final var actualCategory1 =
        Category.newCategory(2L, "Teste");

    Set<Category> actualCategory11 = new HashSet<>();
    actualCategory11.add(actualCategory1);
    actualCategory11.add(actualCategory);
  }
}
