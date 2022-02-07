package com.iesfranciscodelosrios.repositories.price;

import com.iesfranciscodelosrios.model.price.Enums;
import com.iesfranciscodelosrios.model.price.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SizeRepository extends JpaRepository<Size, Long> {
    @Query(nativeQuery = true, value = "SELECT s FROM size s WHERE ended_type=?1 AND valid=true ORDER BY id DESC")
    Size getLatestSize(Enums.sheetSize sheetSize);
}
