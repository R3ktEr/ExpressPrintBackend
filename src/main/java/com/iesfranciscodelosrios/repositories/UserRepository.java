package com.iesfranciscodelosrios.repositories;

import com.iesfranciscodelosrios.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
