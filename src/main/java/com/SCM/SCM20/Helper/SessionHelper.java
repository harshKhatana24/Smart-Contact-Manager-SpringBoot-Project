package com.SCM.SCM20.Helper;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SessionHelper {

    public static void removeMessage(){
        System.out.println("Removing message from session");
        HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
                .getRequest().getSession();

        try{
            session.removeAttribute("message");
        }catch (Exception e){
            System.out.println("Error in SessionHelper: "+e);
            e.printStackTrace();
        }

    }

}

//ye jo message aa rha hai "Registration Successful" tu usko refresh karne par htane e kam aata hai
//Session helper se hum vo message hta sakte hai