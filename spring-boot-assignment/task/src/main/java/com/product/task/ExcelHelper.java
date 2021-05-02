package com.product.task;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.product.task.models.Price;
import com.product.task.models.Product;
import com.product.task.services.ProductPriceService;

@Component
public class ExcelHelper {

    @Autowired
    ProductPriceService productPriceService;

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "Date", "Price Per Lot", "Product ID" };
    static String SHEET = "Prices Per Day";

    public boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public List<Price> excelToPriceHistory(InputStream is) {
        HashMap<Long, Product> product_cache = new HashMap<Long, Product>();
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Price> price_list = new ArrayList<Price>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Price price = new Price();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    if(String.valueOf(currentCell) == ""){
                        continue;
                    }

                    switch (cellIdx) {
                        case 0:
                            try{
                                price.setHistoryDate(new SimpleDateFormat("dd-MMM-yyyy").parse(String.valueOf(currentCell)));
                            }
                            catch(Exception e){
                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "date format "+ currentCell.getStringCellValue() +" is wrong! ");
                            }
                            break;

                        case 1:
                            price.setDayPrice(Double.parseDouble(String.valueOf(currentCell)));
                            break;

                        case 2:
                            String local_id = String.valueOf(currentCell);
                            if(local_id.indexOf(".") != -1){
                                local_id = local_id.substring(0, local_id.indexOf("."));
                            }
                            long product_id = Long.parseLong(local_id);
                            Product current_product = null;
                            if(product_cache.containsKey(product_id)){
                                current_product = product_cache.get(product_id);
                            }
                            else{
                                Optional<Product> optional_product = productPriceService.getByProductId(product_id);
                                if(optional_product.isPresent()){
                                    current_product = optional_product.get();
                                    product_cache.put(product_id, current_product);
                                }
                                else{
                                    System.out.println("product id not available");
                                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "product id "+ String.valueOf(product_id) +" does not exits ");
                                }

                            }
                            price.setProduct(current_product);
                            break;

                        default:
                            break;
                    }

                    cellIdx++;
                }

                if(price.getProduct() != null){
                    price_list.add(price);
                }
            }

            workbook.close();

            return price_list;
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "fail to parse Excel file: " + e.getMessage());
        }
    }
}