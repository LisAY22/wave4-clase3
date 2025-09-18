package com.guatemaltek.postgres.clase3.repo;

import com.guatemaltek.postgres.clase3.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Page<Client> findByActiveTrue(Pageable pageable);

    @Query("""
    SELECT c FROM Client c
    WHERE (:name IS NULL OR c.name LIKE %:name%)
      AND (:address IS NULL OR c.address LIKE %:address%)
      AND (:phone IS NULL OR c.phone LIKE %:phone%)
      AND (:active IS NULL OR c.active = :active)
    """)
    Page<Client> search(
            @Param("name") String name,
            @Param("address") String address,
            @Param("phone") String phone,
            @Param("active") Boolean active,
            Pageable pageable
    );

    Optional<Client> findByIdAndActiveTrue(Long id);

}
