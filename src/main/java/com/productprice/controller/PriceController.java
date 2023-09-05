package com.productprice.controller;

import com.productprice.dto.PriceDTO;
import com.productprice.service.PriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "/", tags = "Product Price operations")
@RequestMapping("/prices")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @ApiOperation(value = "Get all Product Prices", notes = "Returns all Product Prices")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Created"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Error") })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<PriceDTO> getAllPrices() {
        return priceService.getAllPrices();
    }

    @ApiOperation(value = "Get a Product Prices by ID", notes = "Get a Product Prices by ID")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Error") })
    @GetMapping("/{id}")
    public ResponseEntity<PriceDTO> getPriceById(@PathVariable Long id) {
        Optional<PriceDTO> price = priceService.getPriceById(id);
        return price.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Get a Product Prices by date, product and chain", notes = "Returns a Product Prices by date, product and chain")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Error") })
    @GetMapping("/{date}/{product}/{chain}")
    public ResponseEntity<PriceDTO> getPriceByDateAndProductAndChain(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date,
                                                                     @PathVariable Long product,
                                                                     @PathVariable String chain) {
        Optional<PriceDTO> price = priceService.getPriceByDateAndProductAndChain(date, product, chain);
        return price.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Save a Product Price", notes = "Save a Product Price")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Error") })
    @PostMapping
    public ResponseEntity<PriceDTO> createPrice(@RequestBody PriceDTO price) {
        PriceDTO createdPrice = priceService.createPrice(price);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPrice);
    }

    @ApiOperation(value = "Update a Product Price", notes = "Update a Product Price")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Error") })
    @PutMapping("/{id}")
    public ResponseEntity<PriceDTO> updatePrice(@PathVariable Long id, @RequestBody PriceDTO updatedPrice) {
        PriceDTO updated = priceService.updatePrice(id, updatedPrice);
        return updated != null ?
                ResponseEntity.ok().body(updated) :
                ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Update a Product Price", notes = "Update a Product Price")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Error") })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable Long id) {
        priceService.deletePrice(id);
        return ResponseEntity.noContent().build();
    }
}

