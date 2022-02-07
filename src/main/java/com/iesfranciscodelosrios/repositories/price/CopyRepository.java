package com.iesfranciscodelosrios.repositories.price;

import com.iesfranciscodelosrios.model.price.Copy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CopyRepository extends JpaRepository<Copy, Long> {
    @Query(nativeQuery = true, value = "SELECT c FROM copy c WHERE valid=true")
    Copy getLatestCopy();
}
