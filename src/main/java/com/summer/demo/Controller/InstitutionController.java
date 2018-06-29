package com.summer.demo.Controller;
import com.summer.demo.AssitClass.CostomizedInstitution;
import com.summer.demo.Security.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.summer.demo.Entity.InstitutionUser;
import com.summer.demo.Repository.InstitutionUserRepository;
import com.summer.demo.Repository.InstitutionRepository;
import com.summer.demo.Entity.Institution;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.http.*;

@CrossOrigin
@RestController
public class InstitutionController{
    private String filePath="C:\\Users\\Administrator\\Documents\\uploads\\";

    @Autowired
    private InstitutionUserRepository userRepo;

    @Autowired
    private InstitutionRepository institutionRepo;

    @PostMapping(value = "/institution/register")
    public boolean userRegister(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        //相当于新建一个单位用户和一个单位
        InstitutionUser newuser=new InstitutionUser();
        Institution newinstitution = new Institution();

        //昵称重复
        if(userRepo.getNumberOfUsername(request.getParameter("username"))>0){
            return false;
        }else{
            //重复的单位
            if(institutionRepo.getNumberOfInstitutionName(request.getParameter("institution"))>0){
                return false;
            }
            //不正确的信息
            try {
                newuser.setPassword(PasswordStorage.createHash(request.getParameter("password")));
            } catch (PasswordStorage.CannotPerformOperationException e) {
                e.printStackTrace();
                return false;
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
            url=dest.getPath();
            newinstitution.setDownloadUrl(url);
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            institutionRepo.save(newinstitution);
            userRepo.save(newuser);

            return true;
        }
    }

    //单位用户登录函数, 正确返回单位id，错误返回0
    @GetMapping(value = "/institution/login")
    public int userLogin(@RequestParam("username") String username, @RequestParam("password") String password)
    {
        InstitutionUser user1=userRepo.findByUsername(username);
        if( user1!=null)
        {
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
}
