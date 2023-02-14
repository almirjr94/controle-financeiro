package br.com.almir.infrastructure.configuration.usecases;

import br.com.almir.application.subcategory.create.CreateSubcategoryUseCase;
import br.com.almir.application.subcategory.create.DefaultCreateSubcategoryUseCase;
import br.com.almir.application.subcategory.delete.DefaultDeleteSubcategoryUseCase;
import br.com.almir.application.subcategory.delete.DeleteSubcategoryUseCase;
import br.com.almir.application.subcategory.retrieve.get.DefaultGetSubcategoryByIdUseCase;
import br.com.almir.application.subcategory.retrieve.get.GetSubcategoryByIdUseCase;
import br.com.almir.application.subcategory.retrieve.list.DefaultListSubategoriesUseCase;
import br.com.almir.application.subcategory.retrieve.list.ListSubcategoriesUseCase;
import br.com.almir.application.subcategory.update.DefaultUpdateSubcategoryUseCase;
import br.com.almir.application.subcategory.update.UpdateSubcategoryUseCase;
import br.com.almir.domain.category.CategoryGateway;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.subcategory.SubcategoryGateway;
import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubcategoryUseCaseConfig {

  private final CategoryGateway categoryGateway;
  private final SubcategoryGateway subcategoryGateway;
  private final FinancialReleaseGateway financialReleaseGateway;


  public SubcategoryUseCaseConfig(SubcategoryGateway subcategoryGateway,
      CategoryGateway categoryGateway,
      FinancialReleaseGateway financialReleaseGateway) {
    this.subcategoryGateway = Objects.requireNonNull(subcategoryGateway);
    this.categoryGateway = Objects.requireNonNull(categoryGateway);
    this.financialReleaseGateway = Objects.requireNonNull(financialReleaseGateway);
  }

  @Bean
  public CreateSubcategoryUseCase createSubcategoryUseCase() {
    return new DefaultCreateSubcategoryUseCase(subcategoryGateway, categoryGateway);
  }

  @Bean
  public DeleteSubcategoryUseCase deleteSubcategoryUseCase() {
    return new DefaultDeleteSubcategoryUseCase(subcategoryGateway, financialReleaseGateway);
  }

  @Bean
  public GetSubcategoryByIdUseCase getSubcategoryByIdUseCase() {
    return new DefaultGetSubcategoryByIdUseCase(subcategoryGateway);
  }

  @Bean
  public ListSubcategoriesUseCase listSubcategoriesUseCase() {
    return new DefaultListSubategoriesUseCase(subcategoryGateway);
  }

  @Bean
  public UpdateSubcategoryUseCase updateSubcategoryUseCase() {
    return new DefaultUpdateSubcategoryUseCase(subcategoryGateway);
  }

}
