package com.product.task.repositories;

import com.product.task.models.Product;
import com.product.task.models.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface PriceRepository extends JpaRepository<Price, Long> {
    Price findByProductAndHistoryDate(Product product, Date historyDate);
    List<Price> findByProductAndHistoryDateLessThanEqualAndHistoryDateGreaterThanEqual(Product product, Date from_date, Date to_date);
}