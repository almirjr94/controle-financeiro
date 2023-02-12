package br.com.almir.application.subcategory.retrieve.list;


import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.subcategory.Subcategory;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.time.Instant;

public record SubcategoryListOutput(
    SubcategoryID id,
    CategoryID categoryID,
    String name,
    Instant createdAt,
    Instant updatedAt
) {

  public static SubcategoryListOutput from(final Subcategory subcategory) {
    return new SubcategoryListOutput(
        subcategory.getId(),
        subcategory.getCategoryID(),
        subcategory.getName(),
        subcategory.getCreatedAt(),
        subcategory.getUpdatedAt()
    );
  }
}
