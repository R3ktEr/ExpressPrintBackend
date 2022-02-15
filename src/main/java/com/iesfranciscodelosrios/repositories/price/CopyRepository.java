package com.iesfranciscodelosrios.repositories.price;

import com.iesfranciscodelosrios.model.price.Copy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CopyRepository extends JpaRepository<Copy, Long> {
    @Query("SELECT c FROM Copy c WHERE c.valid=true")
    Copy getLatestCopy();
}
