package com.iesfranciscodelosrios.repositories.price;

import com.iesfranciscodelosrios.model.price.Copy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CopyRepository extends JpaRepository<Copy, Long> {
    @Query(nativeQuery = true, value = "SELECT id, price, valid FROM copy WHERE valid=true")
    Copy getLatestCopy();
}
