package com.product.task.repositories;

import com.product.task.models.Product;
import com.product.task.models.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}