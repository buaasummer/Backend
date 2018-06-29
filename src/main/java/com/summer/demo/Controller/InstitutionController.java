package com.summer.demo.Controller;
import com.summer.demo.AssitClass.CostomizedInstitution;
import com.summer.demo.Security.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.summer.demo.Entity.InstitutionUser;
import com.summer.demo.Repository.InstitutionUserRepository;
import com.summer.demo.Repository.InstitutionRepository;
import com.summer.demo.Entity.Institution;

@CrossOrigin
@RestController
public class InstitutionController {
    @Autowired
    private InstitutionUserRepository userRepo;

    @Autowired
    private InstitutionRepository institutionRepo;

    @PostMapping(value = "/institution/register")
    public boolean userRegister(@RequestBody CostomizedInstitution info){
        //相当于新建一个单位用户和一个单位
        InstitutionUser newuser=new InstitutionUser();
        Institution newinstitution = new Institution();

        //昵称重复
        if(userRepo.getNumberOfUsername(info.getUsername())>0){
            return false;
        }else{
            //重复的单位
            if(institutionRepo.getNumberOfInstitutionName(info.getInstitution())>0){
                return false;
            }
            //不正确的信息
            try {
                newuser.setPassword(PasswordStorage.createHash(info.getPassword()));
            } catch (PasswordStorage.CannotPerformOperationException e) {
                e.printStackTrace();
                return false;
            }
            newinstitution.setInstitutionName(info.getInstitution());
            newinstitution.setDownloadUrl("");
            newinstitution.setLegalPersonEmail(info.getEmail());
            newinstitution.setLegalPersonName(info.getLegalPerson());
            newinstitution.setLegalPersonPhoneNumber(info.getContact());
            newinstitution.setOrganizationCode(info.getInstitutionCode());
            newinstitution.setPostalAddress(info.getAddress());
            newinstitution.setTaxPayerCode(info.getTaxpayer());
            newuser.setUsername(info.getUsername());
            newuser.setInstitution(newinstitution);
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
}
