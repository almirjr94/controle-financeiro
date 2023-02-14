package br.com.almir.infrastructure.subcategory.presenter;

import br.com.almir.application.subcategory.retrieve.get.SubcategoryOutput;
import br.com.almir.application.subcategory.retrieve.list.SubcategoryListOutput;
import br.com.almir.infrastructure.subcategory.model.SubcategoryListResponse;
import br.com.almir.infrastructure.subcategory.model.SubcategoryResponse;

public interface SubCategoryApiPresenter {

  static SubcategoryResponse present(final SubcategoryOutput output) {
    return new SubcategoryResponse(output.id().getValue(), output.name(),
        output.categoryID().getValue());
  }

  static SubcategoryListResponse present(final SubcategoryListOutput output) {
    return new SubcategoryListResponse(output.id().getValue(), output.name(),
        output.categoryID().getValue());
  }


}
