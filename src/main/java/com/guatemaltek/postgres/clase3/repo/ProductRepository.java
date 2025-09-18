package com.guatemaltek.postgres.clase3.repo;

import com.guatemaltek.postgres.clase3.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Page<Product> findByActiveTrue(Pageable pageable);

  @Query("""
      select p from Product p
      where (:name is null or lower(p.name) like lower(concat('%', cast(:name as string), '%')))
        and (:currency is null or p.currency = :currency)
        and (:active is null or p.active = :active)
      """)
  Page<Product> search(@Param("name") String name,
                       @Param("currency") String currency,
                       @Param("active") Boolean active,
                       Pageable pageable);

  Optional<Product> findByIdAndActiveTrue(Long id);

}
