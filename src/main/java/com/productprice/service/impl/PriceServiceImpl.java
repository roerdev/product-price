package com.productprice.service.impl;

import com.productprice.domain.Price;
import com.productprice.dto.PriceDTO;
import com.productprice.mapper.PriceMapper;
import com.productprice.repository.BrandRepository;
import com.productprice.repository.PriceRepository;
import com.productprice.service.PriceService;
import com.productprice.util.DatesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private PriceMapper priceMapper;

    @Autowired
    private BrandRepository brandRepository;

    public List<PriceDTO> getAllPrices() {
        var listPrices = priceRepository.findAll();
        return listPrices.stream().map(price -> this.priceMapper.priceToPriceDTO(price)).collect(Collectors.toList());
    }

    public Optional<PriceDTO> getPriceById(Long id) {
        var price = priceRepository.findById(id);
        if (price.isPresent()) {
            return Optional.ofNullable(this.priceMapper.priceToPriceDTO(priceRepository.findById(id).get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<PriceDTO> getPriceByDateAndProductAndChain(Date date, Long product, String chain) {
        var brain = this.brandRepository.findByDescription(chain);
        if (brain.isPresent()) {
            var price = this.priceRepository.findByDateAndProductAndChain(date, product, brain.get().getId());
            if(price.isEmpty()) {
                return Optional.empty();
            }
            var listPricePriority = price.stream().filter(price1 -> price1.getPriority() == 1).collect(Collectors.toList());
            if(listPricePriority.isEmpty()){
                return Optional.ofNullable(this.getPriceToApply(price));
            } else {
                return Optional.ofNullable(this.getPriceToApply(listPricePriority));
            }
        } else {
            return Optional.empty();
        }
    }

    private PriceDTO getPriceToApply(List<Price> priceList){
        if (priceList.size() > 1) {
            var listPriceOrder = DatesUtil.orderByDateDifferenceAscending(priceList);
            return this.priceMapper.priceToPriceDTO(listPriceOrder.stream().findFirst().orElse(null));
        } else {
            return this.priceMapper.priceToPriceDTO(priceList.get(0));
        }
    }

    public PriceDTO createPrice(PriceDTO priceDto) {
        var price = priceRepository.save(this.priceMapper.priceDTOtoPrice(priceDto));
        return this.priceMapper.priceToPriceDTO(price);
    }

    public PriceDTO updatePrice(Long id, PriceDTO priceDto) {
        Optional<Price> existingPrice = priceRepository.findById(id);
        if (existingPrice.isPresent()) {
            priceDto.setId(id);
            var price = priceRepository.save(this.priceMapper.priceDTOtoPrice(priceDto));
            return this.priceMapper.priceToPriceDTO(price);
        }
        return null;
    }

    public void deletePrice(Long id) {
        priceRepository.deleteById(id);
    }
}
