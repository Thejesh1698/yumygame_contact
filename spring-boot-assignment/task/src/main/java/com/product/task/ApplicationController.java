package com.product.task;
import com.product.task.models.Product;
import com.product.task.models.Price;
import com.product.task.services.ProductPriceService;
import com.product.task.ExcelHelper;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;

import java.util.*;
import java.text.SimpleDateFormat;

@RestController
public class ApplicationController{

    @Autowired
    ProductPriceService productPriceService;

//    @PostMapping("/")
//    public ResponseEntity<?> api(){
//        HashMap<String, String> hm = new HashMap<String, String>();
//        hm.put("hehe", "hi");
//        hm.put("lmao","good");
//        return new ResponseEntity<>(hm, HttpStatus.OK);
//    }

    @PostMapping("/api_one")
    public String apiOne(@RequestParam("file") MultipartFile file){
        ExcelHelper excelHelper = new ExcelHelper();
        if (excelHelper.hasExcelFormat(file)) {
            productPriceService.savePriceList(file);
        }
        return "Data saved in db successfully!";
    }

    @PostMapping("/api_two")
    public String apiTwo(@RequestBody Map<String, Object> payLoad){
        String query_date_string, bad_request = "";
        Date query_date = new Date();
        long product_id = 0;
        try{
            bad_request = "Query date ";
            query_date_string = (String)payLoad.get("query_date");
            query_date = new SimpleDateFormat("dd-MM-yyyy").parse(query_date_string);

            bad_request = "Product id ";
            product_id = Long.parseLong((String)payLoad.get("product_id"));
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bad_request + "is not in proper format");
        }

        Optional<Product> optional_product = productPriceService.getByProductId(product_id);
        if(optional_product.isPresent()){
            Product product_obj = optional_product.get();  // retrieve name and interest from this
            Price price_obj = productPriceService.getPriceByProductAndDate(product_obj, query_date);  // retrieve price from this
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product id doesn't exist");
        }
        return "apiTwo";
    }

    @RequestMapping("/api_three/{product_id}")
    public String apiThree(@PathVariable String product_id){
        long productId = 0;
        try{
            productId = Long.parseLong(product_id);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product Id is not in proper format");
        }

        Date current_date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(current_date);
        cal.add(Calendar.DATE, 3);
        Date future_date = cal.getTime();
        try{
            List<Price> price_list = productPriceService.findByProductIdBetweenDates(productId, current_date, future_date);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "fail to parse Excel file: " + e.getMessage());
        }
        return "apiThree";
    }
}