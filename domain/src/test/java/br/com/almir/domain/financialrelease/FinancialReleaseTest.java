package br.com.almir.domain.financialrelease;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.almir.domain.exceptions.NotificationException;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.TimeUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.Test;

class FinancialReleaseTest {

  @Test
  void givenValidParams_WhenCallNewFinancialRelease_shouldInstantiateAFinancialRelease() {
    final var expectedId = FinancialReleaseID.from(2L);
    final var expectedMoney = FastMoney.of(-1.0, Monetary.getCurrency("BRL"));
    final var expectedSubCatergoryID = SubcategoryID.from(3L);
    final var expectedReleasedAt = LocalDate.of(2022, Month.JANUARY, 1);
    final var expectedDescription = "Bla bla bla bla";

    FinancialRelease actualFinancialRelease = FinancialRelease.newFinancialRelease(expectedMoney,
        expectedSubCatergoryID, expectedDescription, expectedReleasedAt).with(expectedId);

    assertNotNull(actualFinancialRelease);
    assertEquals(expectedId, actualFinancialRelease.getId());
    assertEquals(expectedMoney, actualFinancialRelease.getMoney());
    assertEquals(expectedSubCatergoryID, actualFinancialRelease.getSubcategoryID());
    assertEquals(expectedReleasedAt, actualFinancialRelease.getReleasedAt());
    assertEquals(expectedDescription, actualFinancialRelease.getDescription());


    assertNotNull(actualFinancialRelease.getCreatedAt());
    assertNotNull(actualFinancialRelease.getUpdatedAt());

  }

  @Test
  void givenInvalidZeroMoney_WhenCallNewFinancialRelease_shouldReceiveAnError() {
    final var expectedMoney = FastMoney.of(0, Monetary.getCurrency("BRL"));
    final var expectedSubCatergoryID = SubcategoryID.from(3L);
    final var expectedReleasedAt = LocalDate.of(2022, Month.JANUARY, 1);
    final var expectedDescription = "Bla bla bla bla";

    final var expectedErrorCount = 1;
    final var expectedErrorMessage = "'money' should be greater or less than zero";

    final var actualException = assertThrows(NotificationException.class, () ->
        FinancialRelease.newFinancialRelease(expectedMoney, expectedSubCatergoryID,
            expectedDescription, expectedReleasedAt));

    assertEquals(expectedErrorCount, actualException.getErrors().size());
    assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
  }

  @Test
  void givenInvalidNullMoney_WhenCallNewFinancialRelease_shouldReceiveAnError() {
    final var expectedId = FinancialReleaseID.from(1L);
    final MonetaryAmount expectedMoney = null;
    final var expectedSubCatergoryID = SubcategoryID.from(3L);
    final var expectedReleasedAt = LocalDate.of(2022, Month.JANUARY, 1);
    final var expectedDescription = "Bla bla bla bla";

    final var expectedErrorCount = 1;
    final var expectedErrorMessage = "'money' should not be null";

    final var actualException = assertThrows(NotificationException.class, () ->
        FinancialRelease.newFinancialRelease(expectedMoney, expectedSubCatergoryID,
            expectedDescription, expectedReleasedAt));

    assertEquals(expectedErrorCount, actualException.getErrors().size());
    assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
  }

  @Test
  void givenInvalidNullSubCatergoryID_WhenCallNewFinancialRelease_shouldReceiveAnError() {
    final var expectedMoney = FastMoney.of(2, Monetary.getCurrency("BRL"));
    final SubcategoryID expectedSubCatergoryID = null;
    final var expectedReleasedAt = LocalDate.of(2022, Month.JANUARY, 1);
    final var expectedDescription = "Bla bla bla bla";

    final var expectedErrorMessage = "'subcategoryID' should not be null";
    final var expectedErrorCount = 1;

    final var actualException = assertThrows(NotificationException.class, () ->
        FinancialRelease.newFinancialRelease(expectedMoney, expectedSubCatergoryID,
            expectedDescription, expectedReleasedAt));

    assertEquals(expectedErrorCount, actualException.getErrors().size());
    assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
  }

  @Test
  void givenAnActiveFinancialRelease_whenCallUpdate_shouldReceiveOK()
      throws InterruptedException {
    final var financialReleaseID = FinancialReleaseID.from(2L);
    final var money = FastMoney.of(-1.0, Monetary.getCurrency("BRL"));
    final var description = "Bla bla bla bla";
    final var subCatergoryID = SubcategoryID.from(3L);
    final var releasedAt = LocalDate.of(2022, Month.JANUARY, 20);

    final var expectedMoney = FastMoney.of(-1.0, Monetary.getCurrency("BRL"));
    final var expectedReleasedAt = LocalDate.of(2022, Month.JANUARY, 1);
    final var expectedDescription = "Lá lá lá";


    FinancialRelease actualFinancialRelease = FinancialRelease.newFinancialRelease(money,
        subCatergoryID, description, releasedAt);

    final var actualCreatedAt = actualFinancialRelease.getCreatedAt();
    final var actualUpdatedAt = actualFinancialRelease.getUpdatedAt();

    TimeUnit.MILLISECONDS.sleep(1L);
    actualFinancialRelease.update(expectedMoney, expectedDescription, expectedReleasedAt);

    assertEquals(expectedMoney, actualFinancialRelease.getMoney());
    assertEquals(expectedReleasedAt, actualFinancialRelease.getReleasedAt());
    assertEquals(expectedDescription, actualFinancialRelease.getDescription());

    assertEquals(actualCreatedAt, actualFinancialRelease.getCreatedAt());
    assertTrue(actualUpdatedAt.isBefore(actualFinancialRelease.getUpdatedAt()));
  }

}