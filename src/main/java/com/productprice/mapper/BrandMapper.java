package com.productprice.mapper;

import com.productprice.domain.Brand;
import com.productprice.dto.BrandDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BrandMapper {

    BrandDTO brandToBrandDTO(Brand brand);
    Brand brandDTOtoBrand(BrandDTO brandDto);
}
