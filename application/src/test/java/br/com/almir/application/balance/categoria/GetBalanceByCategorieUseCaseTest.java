package br.com.almir.application.balance.categoria;

import br.com.almir.application.UseCaseTest;
import br.com.almir.domain.category.CategoryGateway;
import br.com.almir.domain.financialrelease.FinancialReleaseGateway;
import br.com.almir.domain.subcategory.SubcategoryGateway;
import java.util.List;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class GetBalanceByCategorieUseCaseTest extends UseCaseTest {

  @InjectMocks
  private DefaultGetBalanceByCategorieUseCase useCase;

  @Mock
  private CategoryGateway categoryGateway;
  @Mock
  private SubcategoryGateway subcategoryGateway;
  @Mock
  private FinancialReleaseGateway financialReleaseGateway;

  @Override
  protected List<Object> getMocks() {
    return List.of(categoryGateway, subcategoryGateway, financialReleaseGateway);
  }
}