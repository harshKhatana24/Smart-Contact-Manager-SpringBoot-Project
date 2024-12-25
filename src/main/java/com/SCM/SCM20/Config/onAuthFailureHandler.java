package com.SCM.SCM20.Config;

import com.SCM.SCM20.Helper.Message;
import com.SCM.SCM20.Helper.MessageType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class onAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        if (exception instanceof DisabledException){
            //user is disabled

            HttpSession session=request.getSession();
            Message message=new Message("User is Disabled, Email with verification link is sent to your email id !!" ,MessageType.red);
            session.setAttribute("message",message);

            response.sendRedirect("/login?error=disabled");
        }
        else {
            response.sendRedirect("/login?error=true");
        }

    }
}
