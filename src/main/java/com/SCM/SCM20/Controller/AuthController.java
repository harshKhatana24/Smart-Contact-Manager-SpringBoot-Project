package com.SCM.SCM20.Controller;

import com.SCM.SCM20.Entity.User;
import com.SCM.SCM20.Helper.Message;
import com.SCM.SCM20.Helper.MessageType;
import com.SCM.SCM20.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    //verify email
    @GetMapping("/verify-email")
    public String verifyEmail(
            @RequestParam("token") String token,
            HttpSession httpSession,
            Model model){

        User user=userRepository.findByEmailToken(token).orElse(null);

        if (user != null){
            //user fetch hua hai :: process karna hai
            if (user.getEmailToken().equals(token)){
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepository.save(user);

                return "success_page";
            }

            Message message=new Message();
            message.setType(MessageType.red);
            message.setContent("Email Not Verified ! Token is not associated with user .");
            httpSession.setAttribute("message",message);

            return "error_page";
        }



        Message message=new Message();
        message.setType(MessageType.red);
        message.setContent("Email Not Verified ! Token is not associated with user .");
        httpSession.setAttribute("message",message);

        return "error_page";
    }

}
