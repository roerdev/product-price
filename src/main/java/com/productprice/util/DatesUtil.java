package com.productprice.util;

import com.productprice.domain.Price;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DatesUtil {

    private DatesUtil() {
    }

    public static List<Price> orderByDateDifferenceAscending(List<Price> priceList) {

        List<Price> modifiableList = new ArrayList<>(priceList);
        Collections.sort(modifiableList, new Comparator<Price>() {
            @Override
            public int compare(Price price1, Price price2) {
                long daysDifference1 = ChronoUnit.DAYS.between(price1.getStartDate().toInstant(), price1.getEndDate().toInstant());
                long daysDifference2 = ChronoUnit.DAYS.between(price2.getStartDate().toInstant(), price2.getEndDate().toInstant());

                return Long.compare(daysDifference1, daysDifference2);
            }
        });

        return modifiableList;
    }
}
