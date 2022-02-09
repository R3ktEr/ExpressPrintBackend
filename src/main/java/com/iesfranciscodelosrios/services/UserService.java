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
        
	/**
	 * 
	 * @param user
	 * @return devuelve un usuario nuevo o actualiza uno ya existente
	 * @throws Exception
	 */
	public User createOrUpdateUser(User user)throws Exception{
		if(user.getId()!=null && user.getId()>0) {
			Optional<User> n=  userRepository.findById(user.getId());
			if(n.isPresent()) {
				User newUser=n.get();
				newUser.setMail(user.getMail());
				newUser.setName(user.getName());
				newUser.setPhoneNumber(user.getPhoneNumber());
				newUser.setUserOrders(user.getUserOrders());
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
	/**
	 * 
	 * @return Devuelve todos los usuarios registrados
	 * @throws Exception devuelve mensaje de error en caso de que no existan
	 */
	public List<User> getAllUsers() throws Exception{
		List<User> result=userRepository.findAll();
		if(!result.isEmpty()) {
			return result;
		}else {
			throw  new Exception("No hay usuarios registrados") ;
		}
		
		
		
	}
	
	/**
	 * 
	 * @param Id
	 * @return devuelve el usuario que coincida con el id
	 * @throws Exception devuelve mensaje de error en caso de que no exista
	 */
	/*public User findUserById(Long Id)throws Exception{
		Optional<User> user=userRepository.findById(Id);
		if(user.isPresent()) {
			return user.get();
		}else{
			throw  new Exception("Usuario no encontrado") ;
		}
	
	}*/
	
	/**
	 * 
	 * @param mail
	 * @return devuelve un solo usuario el cual contiene el mail
	 * @throws Exception devuelve mensaje de error en caso de que no exista
	 */
	public User findUserByMail(String mail)throws Exception{
		Optional<User> user=userRepository.findByMail(mail);
		if(user.isPresent()) {
			return user.get();
		}else{
			throw  new Exception("Usuario no encontrado") ;
		}
	
	}
	
	
	/**
	 * 
	 * @param id Elimina de la base de datos al usuario cuyo id coincida con el de la busqueda
	 * @throws Exception devuelve mensaje de error en caso de que no exista
	 */
	public void deleteUserById(Long id)throws Exception	{
		Optional<User> user=userRepository.findById(id);
		if(user.isPresent()) {
			userRepository.deleteById(id);
		}else {
			throw  new Exception("Usuario no encontrado") ;
		}
	}
}
