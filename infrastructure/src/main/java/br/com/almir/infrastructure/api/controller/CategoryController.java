package br.com.almir.infrastructure.api.controller;

import br.com.almir.application.category.create.CreateCategoryCommand;
import br.com.almir.application.category.create.CreateCategoryOutput;
import br.com.almir.application.category.create.CreateCategoryUseCase;
import br.com.almir.application.category.delete.DeleteCategoryUseCase;
import br.com.almir.application.category.retrieve.get.GetCategoryByIdUseCase;
import br.com.almir.application.category.retrieve.list.ListCategoriesUseCase;
import br.com.almir.application.category.update.UpdateCategoryCommand;
import br.com.almir.application.category.update.UpdateCategoryOutput;
import br.com.almir.application.category.update.UpdateCategoryUseCase;
import br.com.almir.domain.category.CategoryFilter;
import br.com.almir.domain.pagination.Pagination;
import br.com.almir.domain.pagination.SearchQuery;
import br.com.almir.infrastructure.api.CategoryAPI;
import br.com.almir.infrastructure.category.model.CategoryResponse;
import br.com.almir.infrastructure.category.model.CreateCategoryApiInput;
import br.com.almir.infrastructure.category.model.UpdateCategoryRequest;
import br.com.almir.infrastructure.category.presenters.CategoryApiPresenter;
import java.net.URI;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController implements CategoryAPI {

  private final CreateCategoryUseCase createCategoryUseCase;
  private final ListCategoriesUseCase listCategoriesUseCase;
  private final GetCategoryByIdUseCase getCategoryByIdUseCase;
  private final UpdateCategoryUseCase updateCategoryUseCase;
  private final DeleteCategoryUseCase deleteCategoryUseCase;

  public CategoryController(CreateCategoryUseCase createCategoryUseCase,
      ListCategoriesUseCase listCategoriesUseCase,
      GetCategoryByIdUseCase getCategoryByIdUseCase,
      UpdateCategoryUseCase updateCategoryUseCase,
      DeleteCategoryUseCase deleteCategoryUseCase) {

    this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
    this.listCategoriesUseCase = Objects.requireNonNull(listCategoriesUseCase);
    this.getCategoryByIdUseCase = Objects.requireNonNull(getCategoryByIdUseCase);
    this.updateCategoryUseCase = Objects.requireNonNull(updateCategoryUseCase);
    this.deleteCategoryUseCase = Objects.requireNonNull(deleteCategoryUseCase);
  }


  @Override
  public ResponseEntity<?> createCategory(final CreateCategoryApiInput input) {
    CreateCategoryCommand command = CreateCategoryCommand.with(input.name());

    CreateCategoryOutput output = createCategoryUseCase.execute(command);

    return ResponseEntity.created(URI.create("/categories/" + output.id())).body(output);
  }

  @Override
  public Pagination<?> listCategories(int page, int perPage, String sort, String dir,
      String searchName) {
    return listCategoriesUseCase.execute(
            new SearchQuery<>(page, perPage, new CategoryFilter(searchName), sort, dir))
        .map(CategoryApiPresenter::present);
  }

  @Override
  public CategoryResponse getById(Long id) {
    return CategoryApiPresenter.present(getCategoryByIdUseCase.execute(id));
  }

  @Override
  public ResponseEntity<?> updateById(Long id, UpdateCategoryRequest input) {

    UpdateCategoryCommand command = UpdateCategoryCommand.with(id, input.name());
    UpdateCategoryOutput output = updateCategoryUseCase.execute(command);

    return ResponseEntity.ok(output);
  }

  @Override
  public void deleteById(Long id) {
    this.deleteCategoryUseCase.execute(id);
  }
}
