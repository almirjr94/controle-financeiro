package br.com.almir.infrastructure.api;

import br.com.almir.domain.pagination.Pagination;
import br.com.almir.infrastructure.category.model.CategoryResponse;
import br.com.almir.infrastructure.category.model.CreateCategoryApiInput;
import br.com.almir.infrastructure.category.model.UpdateCategoryRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RequestMapping(value = "v1/categories")
@Tag(name = "Categories")
public interface CategoryAPI {


  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @Operation(summary = "Create a new category")
  @Parameter(in = ParameterIn.HEADER, name = "api-key")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Created successfully"),
      @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
      @ApiResponse(responseCode = "500", description = "An Internal server error was thrown"),
  })
  ResponseEntity<?> createCategory(@RequestBody CreateCategoryApiInput input);


  @GetMapping
  @Parameter(in = ParameterIn.HEADER, name = "api-key")
  @Operation(summary = "List all catergories paginated")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Listed successfully"),
      @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
      @ApiResponse(responseCode = "500", description = "An Internal server error was thrown"),
  })
  Pagination<?> listCategories(
      @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
      @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
      @RequestParam(name = "sort", required = false, defaultValue = "name") final String sort,
      @RequestParam(name = "dir", required = false, defaultValue = "asc") final String dir,
      @RequestParam(name = "name", required = false) final String searchName
  );

  @GetMapping(
      value = "{id}",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @Operation(summary = "Get a category by it's identifier")
  @Parameter(in = ParameterIn.HEADER, name = "api-key")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Category retrieved successfully"),
      @ApiResponse(responseCode = "404", description = "Category was not found"),
      @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
  })
  CategoryResponse getById(@PathVariable(name = "id") Long id);


  @PutMapping(
      value = "{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @Operation(summary = "Update a category by it's identifier")
  @Parameter(in = ParameterIn.HEADER, name = "api-key")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Category updated successfully"),
      @ApiResponse(responseCode = "404", description = "Category was not found"),
      @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
  })
  ResponseEntity<?> updateById(@PathVariable(name = "id") Long id,
      @RequestBody UpdateCategoryRequest input);


  @DeleteMapping(value = "{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Parameter(in = ParameterIn.HEADER, name = "api-key")
  @Operation(summary = "Delete a category by it's identifier")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Category was not found"),
      @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
  })
  void deleteById(@PathVariable(name = "id") Long id);

}
