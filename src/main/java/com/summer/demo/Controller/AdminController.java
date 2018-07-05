package com.summer.demo.Controller;

import com.summer.demo.Entity.Administrator;
import com.summer.demo.Entity.Application;
import com.summer.demo.Entity.Meeting;
import com.summer.demo.Repository.AdministratorRepository;
import com.summer.demo.Repository.AdministratorRepository;
import com.summer.demo.Repository.ApplicationRepository;
import com.summer.demo.Security.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.summer.demo.Entity.Institution;
import com.summer.demo.Repository.InstitutionRepository;
import com.summer.demo.Entity.InstitutionUser;
import com.summer.demo.Repository.InstitutionUserRepository;
import java.util.List;

@CrossOrigin
@RestController
public class AdminController {
    @Autowired
    private AdministratorRepository adminRepo;

    @Autowired
    private ApplicationRepository applyRepo;

    @Autowired
    private InstitutionRepository institutionRepo;

    @Autowired
    private InstitutionUserRepository userRepo;

    @GetMapping(value = "/admin/login")
    public int adminLogin(@RequestParam("username") String username, @RequestParam("password") String password){
        Administrator admin=adminRepo.findByUsername(username);
        if(admin!=null){
            try
            {
                if( PasswordStorage.verifyPassword(password,admin.getPassword()) )
                {
                    return admin.getAdministratorId();
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


    @GetMapping(value = "/admin/register")
    public Administrator adminRegister(){
        Administrator admin=new Administrator();
        admin.setUsername("ljiangf");
        try {
            admin.setPassword(PasswordStorage.createHash("password"));
        } catch (PasswordStorage.CannotPerformOperationException e) {
            e.printStackTrace();
            return null;//后台错误
        }
        adminRepo.save(admin);
        return admin;
    }


    @GetMapping(value = "/admin/apply")
    public List<Application> getAllApply(){
        return applyRepo.findAll();
    }

    @GetMapping(value = "admin/get_all_institution")
    public List<Institution> getAllInstitution(){
        return institutionRepo.findAll();
    }

    @GetMapping(value = "admin/check_apply")
    public Boolean checkApply(@RequestParam("apply_id") int applyId, @RequestParam("result") int res){
        if(applyId==0)return false;
        Application apply=applyRepo.findByApplicationId(applyId);
        if(res==0){
            userRepo.delete(apply.getInstitutionuser());
            institutionRepo.delete(apply.getInstitution());
        }else{

        }
        applyRepo.deleteById(applyId);
        return true;
    }

}
