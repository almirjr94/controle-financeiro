package br.com.almir.application.subcategory.retrieve.get;


import br.com.almir.domain.category.CategoryID;
import br.com.almir.domain.subcategory.Subcategory;
import br.com.almir.domain.subcategory.SubcategoryID;
import java.time.Instant;

public record SubcategoryOutput(
    SubcategoryID id,
    CategoryID categoryID,
    String name,
    Instant createdAt,
    Instant updatedAt
) {

  public static SubcategoryOutput from(final Subcategory subcategory) {
    return new SubcategoryOutput(
        subcategory.getId(),
        subcategory.getCategoryID(),
        subcategory.getName(),
        subcategory.getCreatedAt(),
        subcategory.getUpdatedAt()
    );
  }
}
