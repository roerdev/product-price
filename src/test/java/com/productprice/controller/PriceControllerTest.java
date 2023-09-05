package com.productprice.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.productprice.dto.PriceDTO;
import com.productprice.service.PriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PriceService priceService;
    
    @InjectMocks
    private PriceController priceController;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(priceController).build();
    }

    @Test
    public void testGetAllPrices() throws Exception {

        URL resource1 = getClass().getClassLoader().getResource("Price1.json");
        URL resource2 = getClass().getClassLoader().getResource("Price2.json");

        PriceDTO price1 = this.objectMapper.readValue(resource1, PriceDTO.class);
        PriceDTO price2 = this.objectMapper.readValue(resource2, PriceDTO.class);

        var list = List.of(price1, price2);
        when(priceService.getAllPrices()).thenReturn(list);

        ResultActions result = mockMvc.perform(get("/prices")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(this.objectMapper.writeValueAsString(list)));
    }

    @Test
    public void testGetPriceDTOById() throws Exception {
        URL resource2 = getClass().getClassLoader().getResource("Price2.json");
        PriceDTO price2 = this.objectMapper.readValue(resource2, PriceDTO.class);
        when(priceService.getPriceById(any())).thenReturn(Optional.of(price2));

        ResultActions result = mockMvc.perform(get("/prices/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testCreatePriceDTO() throws Exception {
        URL resource2 = getClass().getClassLoader().getResource("Price2.json");
        PriceDTO price2 = this.objectMapper.readValue(resource2, PriceDTO.class);
        when(priceService.createPrice(price2)).thenReturn(price2);

        ResultActions result = mockMvc.perform(post("/prices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(price2)));

        result.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(this.objectMapper.writeValueAsString(price2)));
    }

    @Test
    public void testUpdatePriceDTO() throws Exception {
        URL resource2 = getClass().getClassLoader().getResource("Price2.json");
        PriceDTO price2 = this.objectMapper.readValue(resource2, PriceDTO.class);
        when(priceService.updatePrice(1L, price2)).thenReturn(price2);

        ResultActions result = mockMvc.perform(put("/prices/{id}", 1L)
                .content(this.objectMapper.writeValueAsString(price2))
                .contentType(MediaType.APPLICATION_JSON)
                );

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(this.objectMapper.writeValueAsString(price2)));
    }

    @Test
    public void testUpdatePriceDTOReturnNull() throws Exception {
        URL resource2 = getClass().getClassLoader().getResource("Price2.json");
        PriceDTO price2 = this.objectMapper.readValue(resource2, PriceDTO.class);
        when(priceService.updatePrice(1L, price2)).thenReturn(null);

        ResultActions result = mockMvc.perform(put("/prices/{id}", 1L)
                .content(this.objectMapper.writeValueAsString(price2))
                .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isNotFound());
    }

    @Test
    public void testDeletePriceDTO() throws Exception {
        Long priceId = 1L;
        doNothing().when(priceService).deletePrice(priceId);

        ResultActions result = mockMvc.perform(delete("/prices/{id}", priceId)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNoContent());
    }

    @Test
    void getPriceByDateAndProductAndChain() throws Exception{
        URL resource2 = getClass().getClassLoader().getResource("Price2.json");
        PriceDTO price2 = this.objectMapper.readValue(resource2, PriceDTO.class);
        when(priceService.getPriceByDateAndProductAndChain(any(), any(), any())).thenReturn(Optional.of(price2));

        ResultActions result = mockMvc.perform(get("/prices/{date}/{product}/{chain}",
                "2023-09-01 12:04:55",
                price2.getProductId(),
                price2.getBrandId().getDescription())
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L));
    }
}