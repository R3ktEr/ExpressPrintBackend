package com.iesfranciscodelosrios.repositories.price;

import com.iesfranciscodelosrios.model.price.Enums;
import com.iesfranciscodelosrios.model.price.ImpressionPerSide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImpressionPerSideRepository extends JpaRepository<ImpressionPerSide, Long> {
    @Query("SELECT i FROM ImpressionPerSide i WHERE i.impressionsTypes =?1 AND i.valid=true ORDER BY i.id DESC")
    ImpressionPerSide getLatestImpressionPerSide(Enums.ImpressionsTypes impressionsTypes);
}
