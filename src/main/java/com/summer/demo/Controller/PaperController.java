package com.summer.demo.Controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
public class PaperController {
    @PostMapping(value ="/paper/submission")
    public void paperSubmit(@RequestParam("userId") int userId, MultipartHttpServletRequest request)
    {

    }

}
