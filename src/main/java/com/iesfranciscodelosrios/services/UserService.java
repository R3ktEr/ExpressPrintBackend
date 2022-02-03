package com.iesfranciscodelosrios.services;

import com.iesfranciscodelosrios.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

}
