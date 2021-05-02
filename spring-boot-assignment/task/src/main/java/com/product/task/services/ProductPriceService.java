package com.product.task.services;

import com.product.task.models.Product;
import com.product.task.models.Price;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

public interface ProductPriceService {

    // Product SERVICES
    Optional<Product> getByProductId(Long productId);


    // Price SERVICES
    List<Price> findByProductIdBetweenDates(Long productId, Date from_date, Date to_date);
    Price getPriceByProductAndDate(Product product, Date historyDate);
    void savePriceList(MultipartFile file);
}