package com.productprice.service;

import com.productprice.dto.PriceDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PriceService {
    List<PriceDTO> getAllPrices();

    Optional<PriceDTO> getPriceById(Long id);

    Optional<PriceDTO> getPriceByDateAndProductAndChain(Date date, Long product, String chain);

    PriceDTO createPrice(PriceDTO price);

    PriceDTO updatePrice(Long id, PriceDTO updatedPrice);

    void deletePrice(Long id);

}

