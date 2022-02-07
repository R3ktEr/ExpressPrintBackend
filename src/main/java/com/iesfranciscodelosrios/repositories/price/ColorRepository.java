package com.iesfranciscodelosrios.repositories.price;

import com.iesfranciscodelosrios.model.price.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ColorRepository extends JpaRepository<Color, Long> {
    @Query(nativeQuery = true, value = "SELECT c FROM color c WHERE iscolor=?1 AND valid=true ORDER BY id DESC")
    Color getColorPrice(boolean isColor);

}
