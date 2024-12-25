package com.SCM.SCM20.Controller;

import com.SCM.SCM20.Entity.User;
import com.SCM.SCM20.Forms.UserForm;
import com.SCM.SCM20.Helper.Message;
import com.SCM.SCM20.Helper.MessageType;
import com.SCM.SCM20.Services.UserServices;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/home")
    public String home(Model model){
        System.out.println("home page handler");

        model.addAttribute("name","Substring Technologies");
        model.addAttribute("youtubeChannel", "xyz");
        model.addAttribute("gihub","https://github.com/harshKhatana24/login-registration-spring-boot-project");
        model.addAttribute("leetcode","https://leetcode.com/u/harsh_khatana/");

        return "home";
    }




    @GetMapping("/about")
    public String aboutPage(Model model){
        model.addAttribute("isLogin",true);
        return "about";
    }


    @GetMapping("/services")
    public String servicesPage(){
        return "services";
    }


    @GetMapping("/contacts")
    public String contactPage(){
        return "contacts";
    }

    //this is for login page
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }



    //process registration
    @GetMapping("/register")
    public String registerPage(Model model){

        UserForm userForm = new UserForm();
        //default data bhi dal sakte hai
        model.addAttribute("userForm",userForm);

        return "register";
    }


    //Processing Register
    @RequestMapping(value = "/do-register",method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm,
                                  BindingResult rbindingresult,
                                  HttpSession session
                                  ){

        //http session --> message ke liye


        System.out.println("Processing Registration");
        //fetch the form data
        //validate form data
        //save to database
        //message = "Registration Successful"
        //redirect to login page

        //step1.
        //UserForm class ---> data input le lenge
        System.out.println(userForm);

        //step2.
        //TO-do in next video
        //@Valid
        //validate the data
        //binding result
        if (rbindingresult.hasErrors()){
            return "register";
        }


        //step3.
        //UserService
        User user1 = new User();
        user1.setName(userForm.getName());
        user1.setEmail(userForm.getEmail());
        user1.setPassword(userForm.getPassword());
        user1.setAbout(userForm.getAbout());
        user1.setProfilePic("/images/DefaultProfilePic.jpg");
        user1.setPhoneNumber(userForm.getPhoneNumber());

        user1.setEnabled(false); //by default user not enabled

        // Save the user to the database
        User savedUser = userServices.saveUser(user1);
        System.out.println("User Saved");



        //add the message
        Message message = new Message();
        message.setContent("Registration Successful");
        message.setType(MessageType.green);
        session.setAttribute("message",message);





        return "redirect:/register";

    }

}
