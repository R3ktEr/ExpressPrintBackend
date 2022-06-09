package com.iesfranciscodelosrios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.validation.Valid;

import com.iesfranciscodelosrios.model.User;
import com.iesfranciscodelosrios.services.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiOperation(value = "Find all user", notes = "Return all the user whithout paging")
@CrossOrigin(methods = {RequestMethod.GET,RequestMethod.PUT,RequestMethod.POST,RequestMethod.DELETE},allowedHeaders = "*")
@ApiResponses(value = { @ApiResponse(code = 200, message = "succeful operation", response = List.class) })

@RestController
@RequestMapping("/user")

public class UserController {
	@Autowired
	UserService service;

	@ApiOperation(value = "CreateUpdate", notes = "Create or update an User")
	@ApiResponses({ @ApiResponse(code = 200, message = "successful operation", response = List.class),
			@ApiResponse(code = 404, message = "Not Found", response = List.class) }
	)
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public ResponseEntity<User> createOrUpdateUser(@RequestBody User U) {
		User user;
		HttpStatus httpStatus;
		try {
			user = service.createOrUpdateUser(U);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			user = new User();
			e.printStackTrace();
			httpStatus = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(user, new HttpHeaders(), httpStatus);
	}

	@ApiOperation(value = "getAllUsers", notes = "Return all the Users")
	@ApiResponses({ @ApiResponse(code = 200, message = "successful operation", response = List.class),
			@ApiResponse(code = 404, message = "Not Found", response = List.class) }
	)
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> all;
		HttpStatus httpStatus;
		try {
			all = service.getAllUsers();
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			all = new ArrayList<>();
			e.printStackTrace();
			httpStatus = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(all, new HttpHeaders(), httpStatus);
	}

	@ApiOperation(value = "Delete", notes = "Delete an User")
	@ApiResponses({ @ApiResponse(code = 200, message = "successful operation", response = List.class),
			@ApiResponse(code = 403, message = "Action forbidden", response = List.class),
			@ApiResponse(code = 404, message = "Not Found", response = List.class)}
	)
	@DeleteMapping("/{id}")
	public HttpStatus deleteUserById(@PathVariable("id") Long id) {
		HttpStatus httpStatus;
		try {
			service.deleteUserById(id);
			httpStatus = HttpStatus.OK;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			httpStatus = HttpStatus.FORBIDDEN;
		} catch (Exception e) {
			e.printStackTrace();
			httpStatus = HttpStatus.NOT_FOUND;
		}
		return httpStatus;
	}
	
	@ApiOperation(value = "getUserByMail", notes = "Return an User by they mail")
	@ApiResponses({ @ApiResponse(code = 200, message = "successful operation", response = List.class),
			@ApiResponse(code = 404, message = "Not Found", response = List.class) }
	)
	@GetMapping("/{mail}")
	public ResponseEntity<User> getUserByMail(@PathVariable("mail") String mail) {
		User user;
		HttpStatus httpStatus;
		try {
			user = service.findUserByMail(mail);
			
			if(user.getId()!=-1L) {
				httpStatus = HttpStatus.OK;				
			}else {
				httpStatus = HttpStatus.NOT_FOUND;
			}
		} catch (Exception e) {
			e.printStackTrace();
			user = new User();
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(user, new HttpHeaders(), httpStatus);
	}

	@PutMapping("/addtoken")
	public ResponseEntity<HttpStatus> addAndroidToken(@Valid @RequestBody LinkedHashMap<String, String> keyValue){
		HttpStatus httpStatus;
		try {
			service.addAndroidToken(service.findUserByMail(keyValue.get("mail")), keyValue.get("token"));
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			e.printStackTrace();
			httpStatus = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(httpStatus);
	}
}