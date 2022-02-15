package com.iesfranciscodelosrios.repositories.price;

import com.iesfranciscodelosrios.model.price.Enums;
import com.iesfranciscodelosrios.model.price.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SizeRepository extends JpaRepository<Size, Long> {
    @Query("SELECT s FROM Size s WHERE s.sizeSheet=?1 AND s.valid=true ORDER BY s.id DESC")
    Size getLatestSize(Enums.sheetSize sheetSize);
}
