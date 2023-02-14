package br.com.almir.infrastructure.category.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCategoryApiInput(
    @JsonProperty("name") String name
) {

}
