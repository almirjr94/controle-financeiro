package br.com.almir.infrastructure.configuration.usecases;

import br.com.almir.application.balance.all.BalanceUseCase;
import br.com.almir.application.balance.all.DefaultBalanceUseCase;
import br.com.almir.application.balance.categoria.BalanceByCategorieUseCase;
import br.com.almir.application.balance.categoria.DefaultBalanceByCategorieUseCase;
import br.com.almir.domain.category.CategoryGateway;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.subcategory.SubcategoryGateway;
import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BalanceUseCaseConfigure {

  private final FinancialReleaseGateway financialReleaseGateway;
  private final CategoryGateway categoryGateway;
  private final SubcategoryGateway subcategoryGateway;

  public BalanceUseCaseConfigure(FinancialReleaseGateway financialReleaseGateway,
      CategoryGateway categoryGateway, SubcategoryGateway subcategoryGateway) {
    this.financialReleaseGateway = Objects.requireNonNull(financialReleaseGateway);
    this.categoryGateway = Objects.requireNonNull(categoryGateway);
    this.subcategoryGateway = Objects.requireNonNull(subcategoryGateway);
  }

  @Bean
  public BalanceUseCase balanceUseCase() {
    return new DefaultBalanceUseCase(financialReleaseGateway);
  }

  @Bean
  public BalanceByCategorieUseCase balanceByCategorieUseCase() {
    return new DefaultBalanceByCategorieUseCase(
        categoryGateway,
        subcategoryGateway,
        financialReleaseGateway);
  }

}
