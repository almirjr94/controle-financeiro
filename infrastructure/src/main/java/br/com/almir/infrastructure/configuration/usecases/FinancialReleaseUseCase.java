package br.com.almir.infrastructure.configuration.usecases;

import br.com.almir.application.financialrelease.create.CreateFinancialReleaseUseCase;
import br.com.almir.application.financialrelease.create.DefaultCreateFinancialReleaseUseCase;
import br.com.almir.application.financialrelease.delete.DefaultDeleteFinancialReleaseUseCase;
import br.com.almir.application.financialrelease.delete.DeleteFinancialReleaseUseCase;
import br.com.almir.application.financialrelease.retrieve.get.DefaultGetFinancialReleaseUseCase;
import br.com.almir.application.financialrelease.retrieve.get.GetFinancialReleaseUseCase;
import br.com.almir.application.financialrelease.retrieve.list.DefaultListFinancialReleaseUseCase;
import br.com.almir.application.financialrelease.retrieve.list.ListFinancialReleaseUseCase;
import br.com.almir.application.financialrelease.update.DefaultUpdateFinancialReleaseUseCase;
import br.com.almir.application.financialrelease.update.UpdateFinancialReleaseUseCase;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FinancialReleaseUseCase {

  private final FinancialReleaseGateway financialReleaseGateway;

  public FinancialReleaseUseCase(FinancialReleaseGateway financialReleaseGateway) {
    this.financialReleaseGateway = Objects.requireNonNull(financialReleaseGateway);
  }

  @Bean
  public CreateFinancialReleaseUseCase createFinancialReleaseUseCase() {
    return new DefaultCreateFinancialReleaseUseCase(financialReleaseGateway);
  }

  @Bean
  public DeleteFinancialReleaseUseCase deleteFinancialReleaseUseCase() {
    return new DefaultDeleteFinancialReleaseUseCase(financialReleaseGateway);
  }

  @Bean
  public GetFinancialReleaseUseCase getFinancialReleaseUseCase() {
    return new DefaultGetFinancialReleaseUseCase(financialReleaseGateway);
  }

  @Bean
  public ListFinancialReleaseUseCase listFinancialReleaseUseCase() {
    return new DefaultListFinancialReleaseUseCase(financialReleaseGateway);
  }

  @Bean
  public UpdateFinancialReleaseUseCase updateFinancialReleaseUseCase() {
    return new DefaultUpdateFinancialReleaseUseCase(financialReleaseGateway);
  }

}
