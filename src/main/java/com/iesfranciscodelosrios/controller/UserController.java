package com.iesfranciscodelosrios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.iesfranciscodelosrios.model.User;
import com.iesfranciscodelosrios.services.UserService;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiOperation(value="Find all user", notes="REturn all the user whithout paging")
@ApiResponses(value= {
		@ApiResponse(code=200, message="succeful operation", response=List.class)
})

@RestController
@RequestMapping("/user")

public class UserController {
	@Autowired
	UserService service;

	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
	public ResponseEntity<User> createOrUpdateUser(@Valid @RequestBody User U){
		User user;
		HttpStatus httpStatus;
		try{
			user = service.createOrUpdateUser(U);
			httpStatus = HttpStatus.OK;
		}catch (Exception e){
			user = new User();
			e.printStackTrace();
			httpStatus = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(user,  new HttpHeaders(), httpStatus);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> all;
		HttpStatus httpStatus;
		try{
			all = service.getAllUsers();
			httpStatus = HttpStatus.OK;
		}catch (Exception e){
			all = new ArrayList<>();
			httpStatus = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(all, new HttpHeaders(),httpStatus);
	}
	
	@DeleteMapping("/{id}")
	public HttpStatus deleteUserById(@PathVariable("id")Long id){
		HttpStatus httpStatus;
		try{
			service.deleteUserById(id);
			httpStatus = HttpStatus.OK;
		}catch (Exception e){
			e.printStackTrace();
			httpStatus = HttpStatus.NOT_FOUND;
		}
		return httpStatus;
	}
	
	/*@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id")Long id){
		User user;
		HttpStatus httpStatus;
		try{
			user = service.findUserById(id);
			httpStatus = HttpStatus.OK;
		}catch (Exception e){
			e.printStackTrace();
			user = new User();
			httpStatus = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(user, new HttpHeaders(), httpStatus);
	}*/
	
	@GetMapping("/{mail}")
	public ResponseEntity<User> getUserByMail(@PathVariable("mail")String mail) {
		User user;
		HttpStatus httpStatus;
		try{
			user = service.findUserByMail(mail);
			httpStatus = HttpStatus.OK;
		}catch (Exception e){
			e.printStackTrace();
			user = new User();
			httpStatus = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(user, new HttpHeaders(), httpStatus);
	}
}