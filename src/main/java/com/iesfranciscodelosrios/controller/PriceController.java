package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.price.Price;
import com.iesfranciscodelosrios.services.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<Price>> getActualPrices(){
        return null;
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<Price> createOrUpdatePrices(@Valid @RequestBody List<Price> prices){
        List<Price> p = priceService.changeAllPrices(prices);
        return null;
    }


}
