package com.iesfranciscodelosrios.services;

import com.iesfranciscodelosrios.model.Discount;
import com.iesfranciscodelosrios.repositories.DiscountRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {
    @Autowired
    DiscountRepository discountRepository;
    
    //Método que se usa para traer una lista de descuentos
    public List<Discount> getAllDiscounts() {
		List<Discount> Discounts=discountRepository.findAll();
		return Discounts;
	}
    
    /**
     * 
     * @param id Este parámetro es el identificador del descuento que queremos traer
     * @return Devuelve un descuento
     * @throws Devuelve un mensaje en el que pone que el descuento no existe
     */
    public Discount getDiscountById(Long id) throws Exception {
		Optional<Discount> discount=discountRepository.findById(id);
		if(discount.isPresent()) {
			return discount.get();
		}else {
			throw new Exception("El descuento no existe");
		}
	}
    
    /**
     * 
     * @param discount Le pasas al método un descuento para crearlo o updatearlo
     * @return devuelve dicho descuento.
     */
    public Discount createOrUpdatediscount(Discount discount) {
		if(discount.getId()!=null && discount.getId()>0) {
			Optional<Discount> d=discountRepository.findById(discount.getId());
			if(d.isPresent()) { //Update
				Discount newdiscount = d.get();
				newdiscount.setId(discount.getId());
				newdiscount.setName(discount.getName());
				newdiscount.setPercentage(discount.getPercentage());
				newdiscount.setFixedValue(discount.getFixedValue());
				newdiscount.setFixed(discount.isFixed());				
				newdiscount = discountRepository.save(newdiscount);
				return newdiscount;
			}else { //Insert
				discount.setId(null);
				discount=discountRepository.save(discount);
				return discount;
			}
		}else {
			discount=discountRepository.save(discount);
			return discount;
		}
	}
    
    /**
     * 
     * @param id Este parámetro es el identificador del descuento que queremos borrar
     * @throws Exception devuelve un mensaje de descuento inexistente
     */
	public void deleteDiscountById(Long id) throws Exception{
		Optional<Discount> discount=discountRepository.findById(id);
		if(discount.isPresent()) {
			discountRepository.deleteById(id);
		}else {
			throw new Exception("El descuento no existe");
		}
	}

}
