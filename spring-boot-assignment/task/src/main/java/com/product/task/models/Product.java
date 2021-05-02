package com.product.task.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
public class Product {

    @Id
    @Column(name = "ProductId")
    @GeneratedValue(generator = "incrementator")
    @GenericGenerator(name = "incrementator", strategy = "increment")
    private Long productId;

    @Column(name = "ProductName")
    private String productName;

    @Column(name = "InterestRate")
    private Double interestRate;

    @Column(name = "StartDate")
    private Date startDate;

    @Column(name = "MaturityDate")
    private Date maturityDate;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "product")
        private List<Price> priceHistory;


    public Product(Long productId, String productName, Double interestRate, Date startDate, Date maturityDate) {
        this.productId = productId;
        this.productName = productName;
        this.interestRate = interestRate;
        this.startDate = startDate;
        this.maturityDate = this.maturityDate;
    }

    public Product() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }
}