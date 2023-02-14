package br.com.almir.infrastructure.subcategory.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SubcategoryListResponse(
    @JsonProperty("subcategory_id") Long id,
    @JsonProperty("name") String name,
    @JsonProperty("category_id") Long categoryId
) {

}
