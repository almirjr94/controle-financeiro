package br.com.almir.application.financialrelease.create;

import br.com.almir.domain.exceptions.DomainException;
import br.com.almir.domain.financialrelease.FinancialRelease;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.validation.Error;
import br.com.almir.domain.validation.handler.Notification;
import java.util.Objects;
import org.javamoney.moneta.FastMoney;

public class DefaultCreateFinancialReleaseUseCase extends CreateFinancialReleaseUseCase {

  private final FinancialReleaseGateway financialReleaseGateway;

  public DefaultCreateFinancialReleaseUseCase(FinancialReleaseGateway financialReleaseGateway) {
    this.financialReleaseGateway = Objects.requireNonNull(financialReleaseGateway);
  }

  @Override
  public CreateFinancialReleaseOutput execute(CreateFinancialReleaseCommand in) {
    final var brMoney = FastMoney.of(in.money(), "BRL");

    final FinancialRelease newFinancialRelease =
        FinancialRelease.newFinancialRelease(brMoney, in.subcategoryID(), in.description(),
            in.releasedAt());

    Notification notification = Notification.create();
    newFinancialRelease.validate(notification);

    if (notification.hasError()) {
      throw DomainException.with(notification.getErrors());
    }

    FinancialRelease financialRelease = financialReleaseGateway.create(newFinancialRelease);

    if (financialRelease.getId() == null) {
      throw DomainException.with(new Error("FinancialRelease Gateway returned an 'id' null"));
    }

    return CreateFinancialReleaseOutput.from(financialRelease);
  }
}
