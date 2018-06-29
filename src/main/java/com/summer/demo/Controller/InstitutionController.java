package com.summer.demo.Controller;
import com.summer.demo.AssitClass.CostomizedInstitution;
import com.summer.demo.Entity.PersonalUser;
import com.summer.demo.Repository.PersonalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.summer.demo.Entity.InstitutionUser;
import com.summer.demo.Repository.InstitutionUserRepository;

@CrossOrigin
@RestController
public class InstitutionController {
    @Autowired
    private InstitutionUserRepository userRepo;

    @Autowired
    private InstitutionRepository institutionRepo;

    @PostMapping(value = "/personal_user")
    public boolean userRegister(@RequestBody CostomizedInstitution info){
        //相当于新建一个单位用户和一个单位
        InstitutionUser newuser=new InstitutionUser();

        //昵称重复
        if(userRepo.getNumberOfusername(info.getUsername())>0){
            return false;
        }else{
            //重复的单位
            if(institutionRepo.getNumberOfInstitutionName(info.getInstitution())>0){
                return false;
            }

            //不正确的信息

            return true;
        }
    }
}
