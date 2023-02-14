package br.com.almir.infrastructure.api.controller;

import br.com.almir.application.subcategory.create.CreateSubcategoryCommand;
import br.com.almir.application.subcategory.create.CreateSubcategoryOutput;
import br.com.almir.application.subcategory.create.CreateSubcategoryUseCase;
import br.com.almir.application.subcategory.delete.DeleteSubcategoryUseCase;
import br.com.almir.application.subcategory.retrieve.get.GetSubcategoryByIdUseCase;
import br.com.almir.application.subcategory.retrieve.list.ListSubcategoriesUseCase;
import br.com.almir.application.subcategory.update.UpdateSubcategoryCommand;
import br.com.almir.application.subcategory.update.UpdateSubcategoryOutput;
import br.com.almir.application.subcategory.update.UpdateSubcategoryUseCase;
import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.pagination.Pagination;
import br.com.almir.domain.pagination.SearchQuery;
import br.com.almir.domain.subcategory.SubcategoryFilter;
import br.com.almir.domain.subcategory.SubcategoryID;
import br.com.almir.infrastructure.api.SubcategoryAPI;
import br.com.almir.infrastructure.subcategory.model.CreateSubcategoryApiInput;
import br.com.almir.infrastructure.subcategory.model.SubcategoryResponse;
import br.com.almir.infrastructure.subcategory.model.UpdateSubcategoryRequest;
import br.com.almir.infrastructure.subcategory.presenter.SubCategoryApiPresenter;
import java.net.URI;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubcategoryController implements SubcategoryAPI {

  private final CreateSubcategoryUseCase createSubcategoryUseCase;
  private final UpdateSubcategoryUseCase updateSubcategoryUseCase;
  private final GetSubcategoryByIdUseCase getSubcategoryByIdUseCase;
  private final ListSubcategoriesUseCase listSubcategoriesUseCase;
  private final DeleteSubcategoryUseCase deleteSubcategoryUseCase;

  public SubcategoryController(CreateSubcategoryUseCase createSubcategoryUseCase,
      UpdateSubcategoryUseCase updateSubcategoryUseCase,
      GetSubcategoryByIdUseCase getSubcategoryByIdUseCase,
      ListSubcategoriesUseCase listSubcategoriesUseCase,
      DeleteSubcategoryUseCase deleteSubcategoryUseCase) {

    this.createSubcategoryUseCase = Objects.requireNonNull(createSubcategoryUseCase);
    this.updateSubcategoryUseCase = Objects.requireNonNull(updateSubcategoryUseCase);
    this.getSubcategoryByIdUseCase = Objects.requireNonNull(getSubcategoryByIdUseCase);
    this.listSubcategoriesUseCase = Objects.requireNonNull(listSubcategoriesUseCase);
    this.deleteSubcategoryUseCase = Objects.requireNonNull(deleteSubcategoryUseCase);
  }

  @Override
  public ResponseEntity<?> createSubcategory(CreateSubcategoryApiInput input) {
    CreateSubcategoryCommand command = CreateSubcategoryCommand.with(input.name(),
        CategoryID.from(input.categoryId()));
    CreateSubcategoryOutput output = createSubcategoryUseCase.execute(command);
    return ResponseEntity.created(URI.create("v1/subcategories/" + output.id())).body(output);
  }

  @Override
  public Pagination<?> listCategories(int page, int perPage, String sort,
      String dir, String searchName, Long searchSubcategoryId) {

    SubcategoryID subcategoryId =
        searchSubcategoryId != null ? SubcategoryID.from(searchSubcategoryId) : null;

    SubcategoryFilter filter = new SubcategoryFilter(subcategoryId, searchName);
    return listSubcategoriesUseCase.execute(new SearchQuery<>(page, perPage, filter, sort, dir))
        .map(SubCategoryApiPresenter::present);
  }

  @Override
  public SubcategoryResponse getById(Long id) {
    return SubCategoryApiPresenter.present(getSubcategoryByIdUseCase.execute(id));
  }

  @Override
  public ResponseEntity<?> updateById(Long id, UpdateSubcategoryRequest input) {

    UpdateSubcategoryCommand command = UpdateSubcategoryCommand.with(id, input.name());
    UpdateSubcategoryOutput output = updateSubcategoryUseCase.execute(command);

    return ResponseEntity.ok(output);
  }

  @Override
  public void deleteById(Long id) {
    deleteSubcategoryUseCase.execute(id);
  }
}
