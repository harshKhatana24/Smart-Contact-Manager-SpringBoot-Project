package com.SCM.SCM20.Controller;


import com.SCM.SCM20.Entity.User;
import com.SCM.SCM20.Helper.Helper;
import com.SCM.SCM20.Services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice //iske method har ek request ke liye excute hoge
public class RootController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private UserServices userServices;

    @Autowired //construction injection
    public RootController(UserServices userServices){
        this.userServices = userServices;
    }


    //hum jab bhi koi user ka profile, dashboard call kare tu yea call ho jaye
    //agar model attribute likh diya tu yea controller ke has method se phele chalega
    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication){
        //agar login  nhi hai tu phir execute nhi karega means koi info add he nhi karega
        if (authentication == null){
            return;
        }


        System.out.println("adding logged in user information");

        String username= Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User logged in : {}",username);
        //database se data ko fetch kar sakte hai
        User user=userServices.getUserByEmail(username);
        System.out.println(user);
        model.addAttribute("loggedInUser",user);
        System.out.println(user.getName());
        System.out.println(user.getEmail());
    }

}
