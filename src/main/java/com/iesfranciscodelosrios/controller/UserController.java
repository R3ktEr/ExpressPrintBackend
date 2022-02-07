package com.iesfranciscodelosrios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
	
	
	@GetMapping
	public ResponseEntity<User> createOrUpdateUser(@Valid @RequestBody User U)throws Exception{
		User user = service.createOrUpdateUser(U);
		return new ResponseEntity<User>(U,  new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() throws Exception{
		List<User> all = service.getAllUsers();
		return new ResponseEntity<List<User>>(all, new HttpHeaders(),HttpStatus.OK); 
	}
	
	@GetMapping("/{id}")
	public HttpStatus deleteNotesById(@PathVariable("id")Long id) throws Exception {
		service.deleteUserById(id);
		return HttpStatus.OK;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getNotesById(@PathVariable("id")Long id)throws Exception {
		User user=service.findUserById(id);
		return new ResponseEntity<User>(user, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getNotesByMail(@PathVariable("mail")String mail)throws Exception {
		User user=service.findUserByMail(mail);
		return new ResponseEntity<User>(user, new HttpHeaders(), HttpStatus.OK);
	}
}