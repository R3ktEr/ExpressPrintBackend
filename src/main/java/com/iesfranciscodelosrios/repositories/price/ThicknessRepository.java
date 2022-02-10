package com.iesfranciscodelosrios.repositories.price;

import com.iesfranciscodelosrios.model.price.Enums;
import com.iesfranciscodelosrios.model.price.Thickness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ThicknessRepository extends JpaRepository<Thickness, Long> {
    @Query(nativeQuery = true, value = "SELECT id, price, valid, thickness_type, description FROM thickness WHERE thickness_type=?1 AND valid=true ORDER BY id DESC")
    Thickness getLatestThickness(int thicknessType);
}
