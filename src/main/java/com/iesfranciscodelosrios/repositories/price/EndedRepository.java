package com.iesfranciscodelosrios.repositories.price;

import com.iesfranciscodelosrios.model.price.Ended;
import com.iesfranciscodelosrios.model.price.Enums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EndedRepository extends JpaRepository<Ended, Long> {
    @Query("SELECT e FROM Ended e WHERE e.endedType=?1 AND e.valid=true ORDER BY e.id DESC")
    Ended getLatestEnded(Enums.EndedType endedType);
}
