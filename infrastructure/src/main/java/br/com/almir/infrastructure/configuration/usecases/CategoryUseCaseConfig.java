package br.com.almir.infrastructure.configuration.usecases;

import br.com.almir.application.category.create.CreateCategoryUseCase;
import br.com.almir.application.category.create.DefaultCreateCategoryUseCase;
import br.com.almir.application.category.delete.DefaultDeleteCategoryUseCase;
import br.com.almir.application.category.delete.DeleteCategoryUseCase;
import br.com.almir.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import br.com.almir.application.category.retrieve.get.GetCategoryByIdUseCase;
import br.com.almir.application.category.retrieve.list.DefaultListCategoriesUseCase;
import br.com.almir.application.category.retrieve.list.ListCategoriesUseCase;
import br.com.almir.application.category.update.DefaultUpdateCategoryUseCase;
import br.com.almir.application.category.update.UpdateCategoryUseCase;
import br.com.almir.domain.category.CategoryGateway;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.subcategory.SubcategoryGateway;
import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {

  private final CategoryGateway categoryGateway;
  private final SubcategoryGateway subcategoryGateway;
  private final FinancialReleaseGateway financialReleaseGateway;

  public CategoryUseCaseConfig(CategoryGateway categoryGateway,
      SubcategoryGateway subcategoryGateway, FinancialReleaseGateway financialReleaseGateway) {
    this.categoryGateway = Objects.requireNonNull(categoryGateway);
    this.subcategoryGateway = Objects.requireNonNull(subcategoryGateway);
    this.financialReleaseGateway = Objects.requireNonNull(financialReleaseGateway);
  }

  @Bean
  public CreateCategoryUseCase createCategoryUseCase() {
    return new DefaultCreateCategoryUseCase(categoryGateway);
  }

  @Bean
  public UpdateCategoryUseCase updateCategoryUseCase() {
    return new DefaultUpdateCategoryUseCase(categoryGateway);
  }

  @Bean
  public GetCategoryByIdUseCase getCategoryByIdUseCase() {
    return new DefaultGetCategoryByIdUseCase(categoryGateway);
  }

  @Bean
  public ListCategoriesUseCase listCategoriesUseCase() {
    return new DefaultListCategoriesUseCase(categoryGateway);
  }

  @Bean
  public DeleteCategoryUseCase deleteCategoryUseCase() {
    return new DefaultDeleteCategoryUseCase(
        categoryGateway,
        subcategoryGateway,
        financialReleaseGateway);
  }

}
