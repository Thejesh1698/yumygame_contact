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

import java.util.*;
import java.text.SimpleDateFormat;

@RestController
public class ApplicationController {

    @Autowired
    private ProductPriceService productPriceService;
    @Autowired
    private ExcelHelper excelHelper;

    @PostMapping("/add_product_data")
    public String addProductData() {
        List<Product> product_list = new ArrayList<Product>();
        try{
            product_list.add(new Product(Long.parseLong("1"), "product one", Double.parseDouble("100"), new SimpleDateFormat("dd-MMM-yyyy").parse("03-MAY-2021"), new SimpleDateFormat("dd-MMM-yyyy").parse("15-JUN-2021")));
            product_list.add(new Product(Long.parseLong("2"), "GrowFix Gold Dec", Double.parseDouble("11"), new SimpleDateFormat("dd-MMM-yyyy").parse("04-MAY-2021"), new SimpleDateFormat("dd-MMM-yyyy").parse("17-JUN-2022")));
            product_list.add(new Product(Long.parseLong("3"), "GrowFix Wheels Mar", Double.parseDouble("10.25"), new SimpleDateFormat("dd-MMM-yyyy").parse("05-MAY-2021"), new SimpleDateFormat("dd-MMM-yyyy").parse("03-MAR-2023")));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        productPriceService.saveProducts(product_list);
        return "products created!!";
    }


    @PostMapping("/api_one")
    public String apiOne(@RequestParam("file") MultipartFile file) {
        if (excelHelper.hasExcelFormat(file)) {
            productPriceService.savePriceList(file);
        }
        return "Data saved in db successfully!";
    }

    @PostMapping("/api_two")
    public Map<String, String> apiTwo(@RequestBody Map<String, Object> payLoad) {
        String query_date_string, bad_request = "";
        Date query_date = new Date();
        long product_id = 0;
        try {
            bad_request = "Query date ";
            query_date_string = (String) payLoad.get("query_date");
            query_date = new SimpleDateFormat("dd-MMM-yyyy").parse(query_date_string);

            bad_request = "Product id ";
            product_id = Long.parseLong((String) payLoad.get("product_id"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bad_request + "is not in proper format");
        }

        Optional<Product> optional_product = productPriceService.getByProductId(product_id);
        if (optional_product.isPresent()) {
            Product product_obj = optional_product.get();
            Price price_obj = productPriceService.getPriceByProductAndDate(product_obj, query_date);
            if(price_obj == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Price Object doesn't exist");
            }
            Map<String, String> result = new HashMap<String, String>();
            result.put("name", product_obj.getProductName());
            result.put("interest rate", product_obj.getInterestRate().toString());
            result.put("price", price_obj.getDayPrice().toString());

            return result;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product id doesn't exist");
        }
    }

    @RequestMapping("/api_three/{product_id}")
    public List<Price> apiThree(@PathVariable String product_id) {
        long productId = 0;
        try {
            productId = Long.parseLong(product_id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product Id is not in proper format");
        }

        Date current_date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(current_date);
        cal.add(Calendar.DATE, 3);
        Date future_date = cal.getTime();
        try {
            List<Price> price_list = productPriceService.findByProductIdBetweenDates(productId, current_date, future_date);
            return price_list;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find given product id");
        }
    }
}