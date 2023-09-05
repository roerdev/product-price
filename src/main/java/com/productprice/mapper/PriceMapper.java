package com.productprice.mapper;

import com.productprice.domain.Price;
import com.productprice.dto.PriceDTO;
import org.mapstruct.Mapper;

@Mapper(uses = BrandMapper.class)
public interface PriceMapper {

    PriceDTO priceToPriceDTO(Price price);
    Price priceDTOtoPrice(PriceDTO priceDto);

}
