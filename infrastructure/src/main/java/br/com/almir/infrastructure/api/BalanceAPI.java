package br.com.almir.infrastructure.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "v1/balance")
@Tag(name = "balance")

public interface BalanceAPI {

  @GetMapping(
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @Operation(summary = "Get a balance between dates")
  @Parameter(in = ParameterIn.HEADER, name = "api-key")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "balance retrieved successfully"),
      @ApiResponse(responseCode = "404", description = "balance was not found"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
  })
  ResponseEntity<?> getByDateBetween(
      @RequestParam(name = "initDate", required = true) @Parameter(example = "2022-02-15") @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate initDate,
      @RequestParam(name = "endDate", required = true) @Parameter(example = "2022-02-19") @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate endDate,
      @RequestParam(name = "categoryId", required = false) final Long categoryId);


}
