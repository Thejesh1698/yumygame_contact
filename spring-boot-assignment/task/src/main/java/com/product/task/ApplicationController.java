package com.product.task;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
public class ApplicationController{
    @PostMapping("/api_one")
    public String apiOne(){
        return "apiOne";
    }

    @PostMapping("/api_two")
    public String apiTwo(){
        return "apiTwo";
    }

    @RequestMapping("/api_three")
    public String apiThree(){
        return "apiThree";
    }
}