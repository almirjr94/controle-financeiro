package br.com.almir.infrastructure.financerelease.persistence;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialReleaseRepository extends JpaRepository<FinancialReleaseJpaEntity, Long> {

  List<FinancialReleaseJpaEntity> findByReleasedAtBetween(LocalDate start, LocalDate end);

  List<FinancialReleaseJpaEntity> findByReleasedAtBetweenAndSubcategoryIdIn(LocalDate start,
      LocalDate end, List<Long> categoryId);

  List<FinancialReleaseJpaEntity> findBySubcategoryIdIn(Iterable<Long> ids);

  Page<FinancialReleaseJpaEntity> findAll(Specification<FinancialReleaseJpaEntity> whereClause,
      Pageable page);

}
