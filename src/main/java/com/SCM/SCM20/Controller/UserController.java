package com.SCM.SCM20.Controller;


import com.SCM.SCM20.Entity.User;
import com.SCM.SCM20.Helper.Helper;
import com.SCM.SCM20.Services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController {

    //print karne ke liye ya tu hum logger use kar sakte hai ya phir sout
    private Logger logger= LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserServices userServices;





    //user dashboard page
    @GetMapping("/dashboard")
    public String userDashboard(){

        return "user/dashboard";
    }

    //user profile page
    @GetMapping("/profile")
    public String userProfile(){
        return "user/profile";
    }

    //user add contact page



    //user view contacts



    //user edit contacts



    //user delete contact

}
