package com.iesfranciscodelosrios.repositories.price;

import com.iesfranciscodelosrios.model.price.Enums;
import com.iesfranciscodelosrios.model.price.ImpressionPerSide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImpressionPerSideRepository extends JpaRepository<ImpressionPerSide, Long> {
    @Query(nativeQuery = true, value = "SELECT i FROM impression_per_side i WHERE ended_type=?1 AND valid=true ORDER BY id DESC")
    ImpressionPerSide getLatestImpressionPerSide(Enums.ImpressionsTypes impressionsTypes);
}
