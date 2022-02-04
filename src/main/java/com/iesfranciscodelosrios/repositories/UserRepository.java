package com.iesfranciscodelosrios.repositories;

import com.iesfranciscodelosrios.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
	
	
	/*//buscar usuario segun el correo
	@Query(nativeQuery= false, value= "SELECT u FROM User u WHERE mail= ?1")
	Optional<User> findByMail(String mail);
	//
	
	//buscar usuario segun el correo
	@Query(nativeQuery= false, value= "SELECT u FROM User u WHERE name= ?1")
	Optional<User> findByName(String name);
	//*/
		
}
