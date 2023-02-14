package br.com.almir.infrastructure.api;

import br.com.almir.domain.pagination.Pagination;
import br.com.almir.infrastructure.financerelease.model.CreateFinancialReleaseApiInput;
import br.com.almir.infrastructure.financerelease.model.FinancialReleaseResponse;
import br.com.almir.infrastructure.financerelease.model.UpdateFinancialReleaseRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping(value = "v1/financial-release")
@Tag(name = "Financial Releases")
public interface FinancialReleaseAPI {

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @Operation(summary = "Create a new FinancialRelease")
  @Parameter(in = ParameterIn.HEADER, name = "api-key")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Created successfully"),
      @ApiResponse(responseCode = "400", description = "A validation error was thrown"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "500", description = "An Internal server error was thrown"),
  })
  ResponseEntity<?> createFinancialRelease(@RequestBody CreateFinancialReleaseApiInput input);

  @GetMapping
  @Operation(summary = "List all FinancialRelease paginated")
  @Parameter(in = ParameterIn.HEADER, name = "api-key")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Listed successfully"),
      @ApiResponse(responseCode = "400", description = "A validation error was thrown"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "500", description = "An Internal server error was thrown"),
  })
  Pagination<?> listFinancialReleases(
      @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
      @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
      @RequestParam(name = "sort", required = false, defaultValue = "releasedAt") final String sort,
      @RequestParam(name = "dir", required = false, defaultValue = "asc") final String dir,
      @RequestParam(name = "initDate", required = false) @Parameter(example = "2022-02-15") @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate initDate,
      @RequestParam(name = "endDate", required = false) @Parameter(example = "2022-02-19") @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate endDate,
      @RequestParam(name = "subcategoryId", required = false) final Long searchSubcategoryId
  );

  @GetMapping(
      value = "{id}",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @Parameter(in = ParameterIn.HEADER, name = "api-key")
  @Operation(summary = "Get a FinancialRelease by it's identifier")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "FinancialRelease retrieved successfully"),
      @ApiResponse(responseCode = "404", description = "FinancialRelease was not found"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
  })
  FinancialReleaseResponse getById(@PathVariable(name = "id") Long id);

  @PutMapping(
      value = "{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @Parameter(in = ParameterIn.HEADER, name = "api-key")
  @Operation(summary = "Update a FinancialRelease by it's identifier")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "FinancialRelease updated successfully"),
      @ApiResponse(responseCode = "404", description = "FinancialRelease was not found"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
  })
  ResponseEntity<?> updateById(@PathVariable(name = "id") Long id,
      @RequestBody UpdateFinancialReleaseRequest input);

  @DeleteMapping(value = "{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Parameter(in = ParameterIn.HEADER, name = "api-key")
  @Operation(summary = "Delete a FinancialRelease by it's identifier")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "FinancialRelease deleted successfully"),
      @ApiResponse(responseCode = "404", description = "FinancialRelease was not found"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
  })
  void deleteById(@PathVariable(name = "id") Long id);
}
