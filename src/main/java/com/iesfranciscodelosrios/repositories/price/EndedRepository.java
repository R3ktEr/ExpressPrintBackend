package com.iesfranciscodelosrios.repositories.price;

import com.iesfranciscodelosrios.model.price.Ended;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.iesfranciscodelosrios.model.price.Enums.EndedType;

public interface EndedRepository extends JpaRepository<Ended, Long> {
    @Query(nativeQuery = true, value = "SELECT id, price, valid, ended_type FROM ended WHERE ended_type=?1 AND valid=true ORDER BY id DESC")
    Ended getLatestEnded(int endedType);
}
