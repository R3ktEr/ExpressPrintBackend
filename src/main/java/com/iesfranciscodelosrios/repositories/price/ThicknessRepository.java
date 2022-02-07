package com.iesfranciscodelosrios.repositories.price;

import com.iesfranciscodelosrios.model.price.Enums;
import com.iesfranciscodelosrios.model.price.Thickness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ThicknessRepository extends JpaRepository<Thickness, Long> {
    @Query(nativeQuery = true, value = "SELECT t FROM thickness t WHERE thickness_type=?1 AND valid=true ORDER BY id DESC")
    Thickness getLatestThickness(Enums.ThicknessType thicknessType);
}
