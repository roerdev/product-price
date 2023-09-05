package com.productprice.repository;

import com.productprice.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query("SELECT p FROM Price p WHERE :date >= p.startDate AND :date <= p.endDate " +
            "AND p.productId = :product AND p.brandId.id = :chain ORDER BY p.priority DESC")
    List<Price> findByDateAndProductAndChain(Date date, Long product, Long chain);
}