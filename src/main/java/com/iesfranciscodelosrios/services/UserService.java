package com.iesfranciscodelosrios.services;

import com.iesfranciscodelosrios.model.User;
import com.iesfranciscodelosrios.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
        
	
	public User createOrUpdateUser(User user)throws Exception{
		if(user.getId()!=null && user.getId()>0) {
			Optional<User> n=  userRepository.findById(user.getId());
			if(n.isPresent()) {
				User newUser=n.get();
				newUser.getId();
				newUser.getMail();
				newUser.getName();
				newUser.getPhoneNumber();
				newUser.getUserOrders();
				newUser=userRepository.save(newUser);
				return newUser;
			}else {
				user.setId(null);
				user=userRepository.save(user);
				return user;
			}
		}else {
			user=userRepository.save(user);
			return user;
		}
	}
	
	public List<User> getAllUsers(){
		List<User> result=userRepository.findAll();
		return result;
	}
	
	public User findUserById(Long Id)throws Exception{
		Optional<User> user=userRepository.findById(Id);
		if(user.isPresent()) {
			return user.get();
		}else{
			throw  new Exception("Usuario no encontrado") ;
		}
	
	}

	
	
	public void deleteUserById(Long id)throws Exception	{
		Optional<User> user=userRepository.findById(id);
		if(user.isPresent()) {
			userRepository.deleteById(id);
		}else {
			throw  new Exception("Usuario no encontrado") ;
		}
	}
}
