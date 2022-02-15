package com.iesfranciscodelosrios.repositories.price;

import com.iesfranciscodelosrios.model.price.Enums;
import com.iesfranciscodelosrios.model.price.Thickness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ThicknessRepository extends JpaRepository<Thickness, Long> {
    @Query("SELECT t FROM Thickness t WHERE t.thicknessType=?1 AND t.valid=true ORDER BY t.id DESC")
    Thickness getLatestThickness(Enums.ThicknessType thicknessType);
}
