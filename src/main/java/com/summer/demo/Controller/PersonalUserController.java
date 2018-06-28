package com.summer.demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.summer.demo.Entity.PersonalUser;
import com.summer.demo.Repository.PersonalUserRepository;
import com.summer.demo.Security.PasswordStorage;

@CrossOrigin
@RestController
public class PersonalUserController {
    @Autowired
    private PersonalUserRepository userRepo;

    @PostMapping(value = "/personaluser")
    public Boolean userAdd(@RequestBody PersonalUser user)
    {
        PersonalUser user1=new PersonalUser();

        //昵称重复
        if( userRepo.getNumberOfusername(user.getUsername())>0 )
            return false;
        else
        {
            user1.setUsername(user.getUsername());
            try {
                user1.setPassword(PasswordStorage.createHash(user.getPassword()));
            } catch (PasswordStorage.CannotPerformOperationException e) {
                e.printStackTrace();
                return false;
            }
            user1.setEmail(user.getEmail());
            userRepo.save(user1);
            return true;
        }
    }

    //用户登录函数, 正确返回用户id，错误返回0
    @GetMapping(value = "/personaluser")
    public int userExistence(@RequestParam("username") String username, @RequestParam("password") String password)
    {
        PersonalUser user1=userRepo.findByusername(username);
        if( user1!=null)
        {
            try
            {
                if( PasswordStorage.verifyPassword(password,user1.getPassword()) )
                {
                    return user1.getUserId();
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
