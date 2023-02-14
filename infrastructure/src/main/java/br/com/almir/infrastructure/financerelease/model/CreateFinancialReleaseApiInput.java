package br.com.almir.infrastructure.financerelease.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateFinancialReleaseApiInput(
    @JsonProperty("amount") BigDecimal money,
    @JsonProperty("subcategory_id") Long subcategoryID,
    @JsonProperty("description") String description,
    @JsonProperty("date") LocalDate releasedAt
) {

}
