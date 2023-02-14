package br.com.almir.infrastructure.subcategory.persistence;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoryRepository extends JpaRepository<SubcategoryJpaEntity, Long> {

  List<SubcategoryJpaEntity> findByCategoryId(Long id);

  Page<SubcategoryJpaEntity> findAll(Specification<SubcategoryJpaEntity> whereClause,
      Pageable page);

  boolean existsByCategoryIdAndName(Long value, String name);
}
