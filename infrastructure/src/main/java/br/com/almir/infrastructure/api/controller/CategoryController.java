package br.com.almir.infrastructure.api.controller;

import br.com.almir.application.category.create.CreateCategoryCommand;
import br.com.almir.application.category.create.CreateCategoryUseCase;
import br.com.almir.domain.pagination.Pagination;
import br.com.almir.infrastructure.api.CategoryAPI;
import br.com.almir.infrastructure.category.model.CreateCategoryApiInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController implements CategoryAPI {

  private final CreateCategoryUseCase createCategoryUseCase;

  public CategoryController(CreateCategoryUseCase createCategoryUseCase) {
    this.createCategoryUseCase = createCategoryUseCase;
  }


  @Override
  public ResponseEntity<?> createCategory(final CreateCategoryApiInput input) {
    CreateCategoryCommand command = CreateCategoryCommand.with(input.name());

    return ResponseEntity.ok(createCategoryUseCase.execute(command));
  }

  @Override
  public Pagination<?> listCategories(int page, int perPage, String sort,
      String dir) {
    return null;
  }
}
