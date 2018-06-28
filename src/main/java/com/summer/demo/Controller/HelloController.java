package com.summer.demo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping (value ="/hello")
    public String say()
    {
        return "hello";
    }
}
