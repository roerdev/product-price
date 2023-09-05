package com.productprice.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.productprice.domain.Brand;
import com.productprice.domain.Price;
import com.productprice.dto.PriceDTO;
import com.productprice.mapper.PriceMapper;
import com.productprice.mapper.PriceMapperImpl;
import com.productprice.repository.BrandRepository;
import com.productprice.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

    @InjectMocks
    private PriceServiceImpl priceService;

    @Mock
    private PriceRepository priceRepository;

    @Spy
    private PriceMapper priceMapper = new PriceMapperImpl();

    @Mock
    private BrandRepository brandRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void testGetAllPrices() {
        List<Price> prices = List.of(Price.builder().id(1L).build());
        when(priceRepository.findAll()).thenReturn(prices);
        when(priceMapper.priceToPriceDTO(any())).thenReturn(PriceDTO.builder().id(1L).build());

        List<PriceDTO> result = priceService.getAllPrices();

        assertEquals(prices.size(), result.size());
        assertEquals(prices.get(0).getId(), result.get(0).getId());
    }

    @Test
    public void testGetPriceById() {
        Long priceId = 1L;
        when(priceRepository.findById(priceId)).thenReturn(Optional.of(Price.builder().id(priceId).build()));
        when(priceMapper.priceToPriceDTO(any())).thenReturn(PriceDTO.builder().id(priceId).build());

        Optional<PriceDTO> result = priceService.getPriceById(priceId);

        assertEquals(1L, result.orElse(null).getId());
    }

    @Test
    public void testCreatePrice() {
        PriceDTO priceToCreate = PriceDTO.builder().id(1L).build();
        Price priceCreate = Price.builder().id(1L).build();
        when(priceRepository.save(priceCreate)).thenReturn(priceCreate);
        when(priceMapper.priceDTOtoPrice(any())).thenReturn(priceCreate);
        when(priceMapper.priceToPriceDTO(any())).thenReturn(priceToCreate);

        PriceDTO result = priceService.createPrice(priceToCreate);

        assertEquals(priceToCreate, result);
    }

    @Test
    public void testUpdatePrice() {
        Long priceId = 1L;
        Price existingPrice = new Price();
        existingPrice.setId(priceId);
        when(priceRepository.findById(priceId)).thenReturn(Optional.of(existingPrice));
        when(priceMapper.priceDTOtoPrice(any())).thenReturn(existingPrice);
        when(priceMapper.priceToPriceDTO(any())).thenReturn(PriceDTO.builder().id(1L).build());

        PriceDTO updatedPrice = new PriceDTO();
        updatedPrice.setId(priceId);

        PriceDTO result = priceService.updatePrice(priceId, updatedPrice);

        assertEquals(updatedPrice, result);
    }

    @Test
    public void testDeletePrice() {
        Long priceId = 1L;
        doNothing().when(priceRepository).deleteById(priceId);

        priceService.deletePrice(priceId);

        verify(priceRepository, times(1)).deleteById(priceId);
    }

    @Test
    void getPriceByDateAndProductAndChainNotChain() {

        when(brandRepository.findByDescription(any())).thenReturn(Optional.empty());

        Optional<PriceDTO> price = priceService.getPriceByDateAndProductAndChain(new Date(), 1L, "XYZ");

        assertEquals(Optional.empty(), price);
    }

    @Test
    void getPriceByDateAndProductAndChainNotPrice() {

        when(brandRepository.findByDescription(any())).thenReturn(Optional.ofNullable(Brand.builder().build()));
        when(priceRepository.findByDateAndProductAndChain(any(), any(), any())).thenReturn(new ArrayList<>());

        Optional<PriceDTO> price = priceService.getPriceByDateAndProductAndChain(new Date(), 1L, "XYZ");

        assertEquals(Optional.empty(), price);
    }

    @Test
    void getPriceByDateAndProductAndChainWithPricePrioritary() {

        Price price1 = Price.builder().id(1L).priority(1).build();
        Price price2 = Price.builder().id(2L).priority(0).build();

        PriceDTO priceDTO1 = PriceDTO.builder().id(1L).priority(1).build();

        when(brandRepository.findByDescription(any())).thenReturn(Optional.ofNullable(Brand.builder().build()));
        when(priceRepository.findByDateAndProductAndChain(any(), any(), any())).thenReturn(List.of(price1, price2));
        when(priceMapper.priceToPriceDTO(any())).thenReturn(priceDTO1);

        Optional<PriceDTO> price = priceService.getPriceByDateAndProductAndChain(new Date(), 1L, "XYZ");

        assertEquals(priceDTO1, price.orElse(null));
    }

    @Test
    void getPriceByDateAndProductAndChainWithPriceNotPrioritary() throws Exception{

        URL resource1 = getClass().getClassLoader().getResource("Price1.json");
        URL resource2 = getClass().getClassLoader().getResource("Price2.json");

        Price price1 = this.objectMapper.readValue(resource1, Price.class);
        Price price2 = this.objectMapper.readValue(resource2, Price.class);

        PriceDTO priceDTO1 = PriceDTO.builder().id(price2.getId()).priority(price2.getPriority()).build();

        when(brandRepository.findByDescription(any())).thenReturn(Optional.ofNullable(Brand.builder().build()));
        when(priceRepository.findByDateAndProductAndChain(any(), any(), any())).thenReturn(List.of(price1, price2));
        when(priceMapper.priceToPriceDTO(any())).thenReturn(priceDTO1);

        Optional<PriceDTO> price = priceService.getPriceByDateAndProductAndChain(new Date(), 1L, "XYZ");

        assertEquals(price2.getId(), price.orElse(null).getId());
    }

}