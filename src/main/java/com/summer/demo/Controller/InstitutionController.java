package com.summer.demo.Controller;
import com.summer.demo.AssitClass.CostomizedInstitution;
import com.summer.demo.AssitClass.CustomizedUser;
import com.summer.demo.Security.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import org.springframework.data.domain.Pageable;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import javax.servlet.http.*;
import java.util.List;

@CrossOrigin
@RestController
public class InstitutionController{
    private String filePath="C:\\Users\\Administrator\\Documents\\release1\\upload\\";

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

            String url=filePath +"certify_file//"+ newFileName;
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
            apply.setInstitutionuser(newuser);
            institutionRepo.save(newinstitution);

            userRepo.save(newuser);
            applyRepo.save(apply);

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
    public List<Meeting> getAllMeetings(@RequestParam("institution_id") int institution_id, @RequestParam("page") int page, @RequestParam("size") int size){
        Institution institution=institutionRepo.findByInstitutionId(institution_id);
        if(institution!=null){
            Pageable pageable=new PageRequest(page,size, Sort.Direction.ASC,"meetingId");
            List<Meeting> meetingList=new ArrayList<>();
            meetingList=meetingRepo.findAllByInstitution(institution, pageable).getContent();
            return meetingList;
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

    @PostMapping(value = "institution/set_logo/{institution_id}")
    public String setLogo(@PathVariable(value = "institution_id") int institution_id, @RequestParam("img") MultipartFile file){
        Institution institution=institutionRepo.findByInstitutionId(institution_id);
        if(institution!=null){
            if(!file.isEmpty()&&file!=null){
                String filename=file.getOriginalFilename();
                String url=filePath +"institution//"+ filename;
                //创建文件
                File dest = new File(url);
                url="http://154.8.211.55:8081/institution/"+filename;
                institution.setLogo(url);
                try {
                    file.transferTo(dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                institutionRepo.save(institution);
                return url;
            }
        }
        return null;
    }

    @PostMapping(value = "/institution/set_description/{institution_id}")
    public String setDescription(@PathVariable("institution_id") int institution_id, @RequestParam(value = "description") String s){
        Institution institution=institutionRepo.findByInstitutionId(institution_id);
        if(institution!=null){
            institution.setDescription(s);
            institutionRepo.save(institution);
            return institution.getDescription();
        }
        return null;
    }

}
