package br.com.almir.application.category.retrieve.list;


import br.com.almir.domain.category.Category;
import br.com.almir.domain.category.CategoryID;
import java.time.Instant;

public record CategoryListOutput(
    CategoryID id,
    String name,
    Instant createdAt,
    Instant updatedAt
) {

  public static CategoryListOutput from(final Category aCategory) {
    return new CategoryListOutput(
        aCategory.getId(),
        aCategory.getName(),
        aCategory.getCreatedAt(),
        aCategory.getUpdatedAt()
    );
  }
}
