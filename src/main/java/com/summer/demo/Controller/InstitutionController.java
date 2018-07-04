package com.summer.demo.Controller;
import com.summer.demo.AssitClass.CostomizedInstitution;
import com.summer.demo.AssitClass.CustomizedUser;
import com.summer.demo.Security.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.summer.demo.Entity.InstitutionUser;
import com.summer.demo.Repository.InstitutionUserRepository;
import com.summer.demo.Repository.InstitutionRepository;
import com.summer.demo.Entity.Institution;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import com.summer.demo.Entity.Meeting;
import com.summer.demo.Repository.MeetingRepository;
import com.summer.demo.Repository.ApplicationRepository;
import com.summer.demo.Entity.Application;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.http.*;
import java.util.List;

@CrossOrigin
@RestController
public class InstitutionController{
    private String filePath="C:\\Users\\Administrator\\Documents\\release1\\upload\\certify_file\\";

    @Autowired
    private InstitutionUserRepository userRepo;

    @Autowired
    private InstitutionRepository institutionRepo;

    @Autowired
    private MeetingRepository meetingRepo;

    @Autowired
    private ApplicationRepository applyRepo;

    @PostMapping(value = "/institution/register")
    public int userRegister(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        //相当于新建一个单位用户和一个单位
        InstitutionUser newuser=new InstitutionUser();
        Institution newinstitution = new Institution();
        Application apply = new Application();
        if(file==null)return 3;//附件上传错误
        //昵称重复
        if(userRepo.getNumberOfUsername(request.getParameter("username"))>0){
            return 4;//该用户名已存在
        }else{
            //重复的单位
            if(institutionRepo.getNumberOfInstitutionName(request.getParameter("institution"))>0){
                return 2;//已存在该单位名
            }
            //不正确的信息
            try {
                newuser.setPassword(PasswordStorage.createHash(request.getParameter("password")));
            } catch (PasswordStorage.CannotPerformOperationException e) {
                e.printStackTrace();
                return 0;//后台错误
            }
            newinstitution.setInstitutionName(request.getParameter("institution"));
            newinstitution.setLegalPersonEmail(request.getParameter("email"));
            newinstitution.setLegalPersonName(request.getParameter("legalPerson"));
            newinstitution.setLegalPersonPhoneNumber(request.getParameter("contact"));
            newinstitution.setOrganizationCode(request.getParameter("institutionCode"));
            newinstitution.setPostalAddress(request.getParameter("address"));
            newinstitution.setTaxPayerCode(request.getParameter("taxpayer"));
            newuser.setUsername(request.getParameter("username"));
            newuser.setInstitution(newinstitution);

            String fileName = file.getOriginalFilename();
            // 文件后缀
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            // 重新生成唯一文件名，用于存储数据库
            String newFileName = UUID.randomUUID().toString()+suffixName;

            String url=filePath + newFileName;
            //创建文件
            File dest = new File(url);
            url="154.8.211.55:8081/certify_file/"+newFileName;
            newinstitution.setDownloadUrl(url);
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            apply.setInstitution(newinstitution);
            apply.setTime(new java.util.Date().toString());
            institutionRepo.save(newinstitution);
            applyRepo.save(apply);
            userRepo.save(newuser);

            return 1;
        }
    }

    //单位用户登录函数, 正确返回单位id，错误返回0
    @GetMapping(value = "/institution/login")
    public int userLogin(@RequestParam("username") String username, @RequestParam("password") String password)
    {
        InstitutionUser user1=userRepo.findByUsername(username);
        if( user1!=null)
        {
            if(applyRepo.findByInstitution(user1.getInstitution())!=null)return 0;
            try
            {
                if( PasswordStorage.verifyPassword(password,user1.getPassword()) )
                {
                    return user1.getInstitution().getInstitutionId();
                }
            }
            catch (PasswordStorage.CannotPerformOperationException e)
            {
                e.printStackTrace();
                return 0;
            }
            catch (PasswordStorage.InvalidHashException e)
            {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    @GetMapping(value = "/institution/info")
    public Institution getInstitution(@RequestParam("institution_id") int institution_id){
        Institution institution=institutionRepo.findByInstitutionId(institution_id);
        if(institution!=null){
            return institution;
        }
        return null;
    }

    @GetMapping(value="/institution/meetings")
    public List<Meeting> getAllMeetings(@RequestParam("institution_id") int institution_id){
        Institution institution=institutionRepo.findByInstitutionId(institution_id);
        if(institution!=null){
            return meetingRepo.findAllByInstitution(institution);
        }
        return null;
    }

    @PostMapping(value = "/institution/addUser")
    public int addUser(@RequestParam("institutionId") int institutionId,@RequestBody CustomizedUser customizedUser)
    {
        Institution institution=institutionRepo.findByInstitutionId(institutionId);
        InstitutionUser institutionUser=new InstitutionUser();
        institutionUser.setInstitution(institution);
        if(userRepo.getNumberOfUsername(customizedUser.getUserName())>0) {
            return 2;//该用户名已存在
        }else {
            institutionUser.setUsername(customizedUser.getUserName());
            try {
                institutionUser.setPassword(PasswordStorage.createHash(customizedUser.getPassword()));
            } catch (PasswordStorage.CannotPerformOperationException e) {
                e.printStackTrace();
                return 0;//后台错误
            }
        }
            userRepo.save(institutionUser);
            return 1;
    }

}
