package com.iesfranciscodelosrios.services;

import com.iesfranciscodelosrios.model.AndroidToken;
import com.iesfranciscodelosrios.model.User;
import com.iesfranciscodelosrios.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    
    private static final Logger logger= LogManager.getLogger(UserService.class);

	public void addAndroidToken(User user, String token) {
		if(user.getId()!=null && user.getId()>0) {
			Optional<User> n = userRepository.findById(user.getId());
			if (n.isPresent()) {
				User newUser = n.get();
				newUser.getAndroidTokens().add(new AndroidToken(token));
				userRepository.save(newUser);
			}
		}
	}
    
	/**
	 * 
	 * @param user
	 * @return devuelve un usuario nuevo o actualiza uno ya existente
	 * @throws Exception
	 */
	public User createOrUpdateUser(User user){
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
			logger.info("No hay usuarios registrados");
			throw new Exception("No hay usuarios registrados") ;
		}

	}
	
	
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
			logger.info("Usuario con correo: "+mail+" no encontrado");
			return new User();
		}
	
	}
	
	
	/**
	 * 
	 * @param id Elimina de la base de datos al usuario cuyo id coincida con el de la busqueda
	 * @throws Exception devuelve mensaje de error en caso de que no exista
	 */
	public void deleteUserById(Long id)throws Exception, IllegalArgumentException {
		Optional<User> user=userRepository.findById(id);
		if(user.isPresent()) {
			try {
				userRepository.deleteById(id);				
			}catch(Exception e) {
				logger.info("El usuario no se puede borrar");

				throw new IllegalArgumentException("Este usuario no se puede borrar") ;
			}
		}else {
			logger.info("Usuario con id: "+id+" no encontrado");

			throw new Exception("Usuario no encontrado") ;
		}
	}
}
