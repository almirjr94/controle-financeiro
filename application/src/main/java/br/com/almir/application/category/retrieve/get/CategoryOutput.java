package br.com.almir.application.category.retrieve.get;


import br.com.almir.domain.category.Category;
import br.com.almir.domain.category.CategoryID;
import java.time.Instant;

public record CategoryOutput(
    CategoryID id,
    String name,
    Instant createdAt,
    Instant updatedAt
) {

  public static CategoryOutput from(final Category aCategory) {
    return new CategoryOutput(
        aCategory.getId(),
        aCategory.getName(),
        aCategory.getCreatedAt(),
        aCategory.getUpdatedAt()
    );
  }
}
