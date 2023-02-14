package br.com.almir.infrastructure.subcategory.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateSubcategoryApiInput(
    @JsonProperty("name") String name,
    @JsonProperty("category_id") Long categoryId
) {

}
