package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.price.Product;
import com.iesfranciscodelosrios.services.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/prices")
public class PriceController {

    @Autowired
    PriceService priceService;

    @GetMapping
    public ResponseEntity<List<Object>> getActualPrices(){
        return new ResponseEntity<>(priceService.getAllPrices(), new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<List<Object>> createOrUpdatePrices(@Valid @RequestBody List<Object> prices){
        return new ResponseEntity<>(priceService.changeAllPrices(prices), new HttpHeaders(), HttpStatus.OK);
    }


}
