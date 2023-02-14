package br.com.almir.infrastructure.category.presenters;

import br.com.almir.application.category.retrieve.get.CategoryOutput;
import br.com.almir.application.category.retrieve.list.CategoryListOutput;
import br.com.almir.infrastructure.category.model.CategoryListResponse;
import br.com.almir.infrastructure.category.model.CategoryResponse;

public interface CategoryApiPresenter {

  static CategoryResponse present(final CategoryOutput output) {
    return new CategoryResponse(output.id().getValue(), output.name());
  }

  static CategoryListResponse present(final CategoryListOutput output) {
    return new CategoryListResponse(output.id().getValue(), output.name());
  }


}
