package br.com.almir.infrastructure.financerelease.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateFinancialReleaseRequest(
    @JsonProperty("amount") BigDecimal money,
    @JsonProperty("description") String describe,
    @JsonProperty("date") LocalDate releasedAt
) {

}
