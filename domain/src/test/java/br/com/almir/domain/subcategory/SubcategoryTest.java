package br.com.almir.domain.subcategory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.exceptions.DomainException;
import br.com.almir.domain.financialrelease.FinancialReleaseID;
import br.com.almir.domain.validation.handler.ThrowsValidationHandler;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SubcategoryTest {

  @Test
  void givenAValidParams_whenCallNewSubcategory_thenInstantiateASubcategory() {
    final var expectedId = SubcategoryID.from(1L);
    final var expectednName = "Test Category";
    final var expectedCategoryId = CategoryID.from(2L);

    final var subcategory = Subcategory.newSubcatergory(expectedId, expectednName,
        expectedCategoryId);

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

    final var actualSubcategory = Subcategory.newSubcatergory(expectedId, expectednName,
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

    final var actualSubcategory = Subcategory.newSubcatergory(expectedId, expectednName,
        expectedCategoryId);

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

    final var actualSubcategory = Subcategory.newSubcatergory(id, name, categoryId);

    final var actualCreatedAt = actualSubcategory.getCreatedAt();
    final var actualUpdatedAt = actualSubcategory.getUpdatedAt();

    TimeUnit.MILLISECONDS.sleep(1);
    actualSubcategory.update(expectedName);

    assertEquals(expectedName, actualSubcategory.getName());
    assertEquals(actualCreatedAt, actualSubcategory.getCreatedAt());
    assertTrue(actualUpdatedAt.isBefore(actualSubcategory.getUpdatedAt()));
  }

  @Test
  void givenAValidEmptyFinancialRelease_whenCallAddFinancialRelease_shouldReceiveOK()
      throws InterruptedException {
    final var expectedId = SubcategoryID.from(1L);
    final String expectedName = "Test";
    final var expectedCategoryId = CategoryID.from(2L);

    final var expectedFinancialReleases = List.of(FinancialReleaseID.from(1L),
        FinancialReleaseID.from(2L));

    final var actualSubcategory = Subcategory.newSubcatergory(expectedId, expectedName,
        expectedCategoryId);

    final var actualCreatedAt = actualSubcategory.getCreatedAt();
    final var actualUpdatedAt = actualSubcategory.getUpdatedAt();

    TimeUnit.MILLISECONDS.sleep(1);
    actualSubcategory.addFinancialRelease(FinancialReleaseID.from(1L));
    actualSubcategory.addFinancialRelease(FinancialReleaseID.from(2L));

    Assertions.assertEquals(expectedFinancialReleases, actualSubcategory.getFinancialReleases());
    assertEquals(expectedName, actualSubcategory.getName());
    assertEquals(actualCreatedAt, actualSubcategory.getCreatedAt());
    assertTrue(actualUpdatedAt.isBefore(actualSubcategory.getUpdatedAt()));
  }

  @Test
  void givenAInvalidNullFinancialRelease_whenCallAddFinancialRelease_shouldReceiveOK()
      throws InterruptedException {
    final var expectedId = SubcategoryID.from(1L);
    final String expectedName = "Test";
    final var expectedCategoryId = CategoryID.from(2L);
    final FinancialReleaseID expectedFinancialReleases = null;

    final var actualSubcategory = Subcategory.newSubcatergory(expectedId, expectedName,
        expectedCategoryId);

    final var actualCreatedAt = actualSubcategory.getCreatedAt();
    final var actualUpdatedAt = actualSubcategory.getUpdatedAt();

    TimeUnit.MILLISECONDS.sleep(1);
    actualSubcategory.addFinancialRelease(expectedFinancialReleases);

    Assertions.assertTrue(actualSubcategory.getFinancialReleases().isEmpty());
    assertEquals(expectedName, actualSubcategory.getName());
    assertEquals(actualCreatedAt, actualSubcategory.getCreatedAt());
    assertEquals(actualUpdatedAt, actualSubcategory.getUpdatedAt());
  }

  @Test
  void givenAValidEmptyListOfFinancialRelease_whenCallAddFinancialRelease_shouldReceiveOK()
      throws InterruptedException {
    final var expectedId = SubcategoryID.from(1L);
    final String expectedName = "Test";
    final var expectedCategoryId = CategoryID.from(2L);

    final var expectedFinancialReleases = List.of(FinancialReleaseID.from(1L),
        FinancialReleaseID.from(2L));

    final var actualSubcategory = Subcategory.newSubcatergory(expectedId, expectedName,
        expectedCategoryId);

    final var actualCreatedAt = actualSubcategory.getCreatedAt();
    final var actualUpdatedAt = actualSubcategory.getUpdatedAt();

    TimeUnit.MILLISECONDS.sleep(1);
    actualSubcategory.addFinancialRelease(expectedFinancialReleases);

    Assertions.assertEquals(expectedFinancialReleases, actualSubcategory.getFinancialReleases());
    assertEquals(expectedName, actualSubcategory.getName());
    assertEquals(actualCreatedAt, actualSubcategory.getCreatedAt());
    assertTrue(actualUpdatedAt.isBefore(actualSubcategory.getUpdatedAt()));
  }

  @Test
  void givenAInvalidNullLifOfFinancialRelease_whenCallAddFinancialRelease_shouldReceiveOK()
      throws InterruptedException {
    final var expectedId = SubcategoryID.from(1L);
    final String expectedName = "Test";
    final var expectedCategoryId = CategoryID.from(2L);
    final List<FinancialReleaseID> expectedFinancialReleases = null;

    final var actualSubcategory = Subcategory.newSubcatergory(expectedId, expectedName,
        expectedCategoryId);

    final var actualCreatedAt = actualSubcategory.getCreatedAt();
    final var actualUpdatedAt = actualSubcategory.getUpdatedAt();

    TimeUnit.MILLISECONDS.sleep(1);
    actualSubcategory.addFinancialRelease(expectedFinancialReleases);

    Assertions.assertTrue(actualSubcategory.getFinancialReleases().isEmpty());
    assertEquals(expectedName, actualSubcategory.getName());
    assertEquals(actualCreatedAt, actualSubcategory.getCreatedAt());
    assertEquals(actualUpdatedAt, actualSubcategory.getUpdatedAt());
  }
}