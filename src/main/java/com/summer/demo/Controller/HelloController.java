package com.summer.demo.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.summer.demo.AssitClass.TryMeeting;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class HelloController {
    private String filePath="C:\\Users\\ljiangf\\Documents\\c\\";
    @RequestMapping (value ="/hello")
    public String say()
    {
        return "hello";
    }

    @PostMapping(value = "/test")
    public String tryfun(TryMeeting trymeeting){
        String s="";
        s=s+trymeeting.getUsername()+"\n";
        s=s+trymeeting.getInstitution()+"\n";
        if (trymeeting.getFile()==null||trymeeting.getFile().isEmpty()){
            System.out.println("in if");
            return s;
        }
        MultipartFile file=trymeeting.getFile();

        String fileName = file.getOriginalFilename();
        // 文件后缀
        //String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 重新生成唯一文件名，用于存储数据库
        //String newFileName = UUID.randomUUID().toString()+suffixName;

        String url=filePath + fileName;
        //创建文件
        File dest = new File(url);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
