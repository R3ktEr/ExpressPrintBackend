package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.services.PriceService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value = "Obtener precios vigentes", notes = "Devuelve una lista de objetos con sus atributos correspondientes con el id y su precio")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Petición correcta"),
            @ApiResponse(code = 500, message = "Error interno")
    })
    @GetMapping
    public ResponseEntity<List<Object>> getActualPrices(){
        return new ResponseEntity<>(priceService.getAllPricesValidated(), new HttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation(value = "Cambiar precios", notes = "Devuelve la misma lista de objetos con sus atributos correspondientes además con el id generado en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Petición correcta"),
            @ApiResponse(code = 500, message = "Error interno")
    })
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<List<Object>> createOrUpdatePrices(@Valid @RequestBody List<Object> prices){
        return new ResponseEntity<>(priceService.changeAllPrices(prices), new HttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation(value = "Historial de precios", notes = "Devuelve una lista de objetos con el historial de precios de todos los elementos que contengan precio de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Petición correcta"),
            @ApiResponse(code = 500, message = "Error interno")
    })
    @GetMapping("/history/{offset}")
    public ResponseEntity<List<Object>> getHistoricalDataPrices(@ApiParam(value = "Página",example = "10", required = true) @PathVariable("offset")int offSet) {
        return new ResponseEntity<>(priceService.getAllHistoricalPrices(offSet), new HttpHeaders(), HttpStatus.OK);
    }

}
