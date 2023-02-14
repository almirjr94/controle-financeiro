package br.com.almir.infrastructure.api.controller;

import br.com.almir.application.financialrelease.create.CreateFinancialReleaseCommand;
import br.com.almir.application.financialrelease.create.CreateFinancialReleaseOutput;
import br.com.almir.application.financialrelease.create.CreateFinancialReleaseUseCase;
import br.com.almir.application.financialrelease.delete.DeleteFinancialReleaseUseCase;
import br.com.almir.application.financialrelease.retrieve.get.GetFinancialReleaseUseCase;
import br.com.almir.application.financialrelease.retrieve.list.ListFinancialReleaseUseCase;
import br.com.almir.application.financialrelease.update.UpdateFinancialReleaseCommand;
import br.com.almir.application.financialrelease.update.UpdateFinancialReleaseOutput;
import br.com.almir.application.financialrelease.update.UpdateFinancialReleaseUseCase;
import br.com.almir.domain.financialrelease.FinancialReleaseFilter;
import br.com.almir.domain.financialrelease.FinancialReleaseID;
import br.com.almir.domain.pagination.Pagination;
import br.com.almir.domain.pagination.SearchQuery;
import br.com.almir.domain.subcategory.SubcategoryID;
import br.com.almir.infrastructure.api.FinancialReleaseAPI;
import br.com.almir.infrastructure.financerelease.model.CreateFinancialReleaseApiInput;
import br.com.almir.infrastructure.financerelease.model.FinancialReleaseResponse;
import br.com.almir.infrastructure.financerelease.model.UpdateFinancialReleaseRequest;
import br.com.almir.infrastructure.financerelease.presenters.FinancialReleaseApiPresenter;
import java.net.URI;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FinancialReleaseController implements FinancialReleaseAPI {

  private final CreateFinancialReleaseUseCase createFinancialReleaseUseCase;
  private final GetFinancialReleaseUseCase getFinancialReleaseUseCase;
  private final ListFinancialReleaseUseCase listFinancialReleaseUseCase;
  private final UpdateFinancialReleaseUseCase updateFinancialReleaseUseCase;
  private final DeleteFinancialReleaseUseCase deleteFinancialReleaseUseCase;

  public FinancialReleaseController(CreateFinancialReleaseUseCase createFinancialReleaseUseCase,
      GetFinancialReleaseUseCase getFinancialReleaseUseCase,
      ListFinancialReleaseUseCase listFinancialReleaseUseCase,
      UpdateFinancialReleaseUseCase updateFinancialReleaseUseCase,
      DeleteFinancialReleaseUseCase deleteFinancialReleaseUseCase) {
    this.createFinancialReleaseUseCase = Objects.requireNonNull(createFinancialReleaseUseCase);
    this.getFinancialReleaseUseCase = Objects.requireNonNull(getFinancialReleaseUseCase);
    this.listFinancialReleaseUseCase = Objects.requireNonNull(listFinancialReleaseUseCase);
    this.updateFinancialReleaseUseCase = Objects.requireNonNull(updateFinancialReleaseUseCase);
    this.deleteFinancialReleaseUseCase = Objects.requireNonNull(deleteFinancialReleaseUseCase);
  }

  @Override
  public ResponseEntity<?> createFinancialRelease(CreateFinancialReleaseApiInput input) {
    CreateFinancialReleaseCommand command = CreateFinancialReleaseCommand.with(
        input.money(), SubcategoryID.from(input.subcategoryID()), input.description(),
        input.releasedAt()
    );

    CreateFinancialReleaseOutput output = createFinancialReleaseUseCase.execute(command);

    return ResponseEntity.created(URI.create("v1/financial-release/" + output.id())).body(output);
  }

  @Override
  public Pagination<?> listFinancialReleases(int page, int perPage, String sort,
      String dir, LocalDate initDate, LocalDate endDate,
      Long searchSubcategoryId) {

    SubcategoryID subcategoryID =
        searchSubcategoryId != null ? SubcategoryID.from(searchSubcategoryId) : null;

    FinancialReleaseFilter filter = FinancialReleaseFilter.with(initDate, endDate, subcategoryID);

    return listFinancialReleaseUseCase.execute(
        new SearchQuery<>(page, perPage, filter, sort, dir)
    ).map(FinancialReleaseApiPresenter::present);
  }

  @Override
  public FinancialReleaseResponse getById(Long id) {
    return FinancialReleaseApiPresenter.present(getFinancialReleaseUseCase.execute(id));
  }

  @Override
  public ResponseEntity<?> updateById(Long id, UpdateFinancialReleaseRequest input) {

    UpdateFinancialReleaseCommand command = UpdateFinancialReleaseCommand.from(
        FinancialReleaseID.from(id), input.money(), input.describe(), input.releasedAt());

    UpdateFinancialReleaseOutput output = updateFinancialReleaseUseCase.execute(command);

    return ResponseEntity.ok(output);
  }

  @Override
  public void deleteById(Long id) {
    deleteFinancialReleaseUseCase.execute(id);
  }
}
