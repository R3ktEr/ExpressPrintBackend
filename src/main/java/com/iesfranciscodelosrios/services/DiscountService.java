package com.iesfranciscodelosrios.services;

import com.iesfranciscodelosrios.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {
    @Autowired
    DiscountRepository discountRepository;

}
