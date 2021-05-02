package com.product.task.services;

import com.product.task.models.Product;
import com.product.task.models.Price;
import com.product.task.repositories.ProductRepository;
import com.product.task.repositories.PriceRepository;
import com.product.task.ExcelHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.*;
import java.io.IOException;

@Service
public class ProductPriceServiceImpl implements ProductPriceService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceRepository priceRepository;


    @Override
    public Optional<Product> getByProductId(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Price getPriceByProductAndDate(Product product, Date historyDate) {
        return priceRepository.findByProductAndHistoryDate(product,  historyDate);
    }

    @Override
    public List<Price> findByProductIdBetweenDates(Long productId, Date from_date, Date to_date) {
        Optional<Product> product_obj = productRepository.findById(productId);
        if(product_obj.isPresent()){
            return priceRepository.findByProductAndHistoryDateLessThanEqualAndHistoryDateGreaterThanEqual(product_obj.get(), from_date, to_date);
        }
        return new ArrayList<Price>();
    }

    @Override
    public void savePriceList(MultipartFile file) {
        ExcelHelper excelHelper = new ExcelHelper();
        try{
            List<Price> price_list = excelHelper.excelToPriceHistory(file.getInputStream());
            priceRepository.saveAll(price_list);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "fail to parse Excel file: " + e.getMessage());
        }
    }

//
//    @Override
//    public void saveCart(Cart cart) {
//        cartRepository.save(cart);
//    }
//
//    @Override
//    public void deleteCart(Cart cart) {
//        cartRepository.delete(cart);
//    }
//

//
//    @Override
//    public void deleteWishList(WishList wishList) {
//        wishListRepository.delete(wishList);
//    }
//
//    @Override
//    public WishList getWishlistByUser(User user) {
//        return wishListRepository.findByUser(user);
//    }
//

}