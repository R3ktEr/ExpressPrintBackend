package com.iesfranciscodelosrios.repositories.price;

import com.iesfranciscodelosrios.model.price.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ColorRepository extends JpaRepository<Color, Long> {
    @Query("SELECT c FROM Color c WHERE c.isColor =?1 AND c.valid=true ORDER BY c.id DESC")
    Color getColorPrice(boolean isColor);

}
