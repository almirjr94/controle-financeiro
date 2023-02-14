package br.com.almir.infrastructure.financerelease.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public record FinancialReleaseResponse(
    @JsonProperty("release_id") Long financialReleaseID,
    @JsonProperty("amount") double money,
    @JsonProperty("subcategory_id") Long subcategoryID,
    @JsonProperty("description") String describe,
    @JsonProperty("date") LocalDate releasedAt
) {

}
