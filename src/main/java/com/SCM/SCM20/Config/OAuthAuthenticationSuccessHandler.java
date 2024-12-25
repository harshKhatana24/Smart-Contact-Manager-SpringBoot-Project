package com.SCM.SCM20.Config;


import com.SCM.SCM20.Entity.Providers;
import com.SCM.SCM20.Entity.User;
import com.SCM.SCM20.Helper.AppConstants;
import com.SCM.SCM20.Repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepository userRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthAuthenticationSuccessHandler");
//        response.sendRedirect("/home");


        //identify the provider

        var oAuth2AuthenticationToken=(OAuth2AuthenticationToken) authentication;

        String authorizedClientRegistrationId=oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

        logger.info(authorizedClientRegistrationId);

        var oauthUser=(DefaultOAuth2User) authentication.getPrincipal();

        oauthUser.getAttributes().forEach((key,value) -> {
            logger.info(key + " => " + value);
        });


        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setPassword("dummyPasswordForGoogle&Github");


        if(authorizedClientRegistrationId.equalsIgnoreCase("google")){

            //google
            //google attributes

            user.setEmail(oauthUser.getAttribute("email").toString());
            user.setProfilePic(oauthUser.getAttribute("picture").toString());
            user.setName(oauthUser.getAttribute("name").toString());
            user.setProviderUserId(oauthUser.getName());
            user.setProvider(Providers.GOOGLE);
            user.setAbout("This account is created using google");


        }
        else if (authorizedClientRegistrationId.equalsIgnoreCase("github")){

            //github
            //github attributes
            //user name ko as a email create kar rhe hai
            String email = oauthUser.getAttribute("email") != null
                    ? oauthUser.getAttribute("email").toString()
                    : oauthUser.getAttribute("login").toString()+"github@gmail.com";

            String picture = oauthUser.getAttribute("avatar_url").toString();
            String name = oauthUser.getAttribute("login").toString();
            String providerUserId = oauthUser.getName(); //name ko hi as a provider user id

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderUserId(providerUserId);
            user.setProvider(Providers.GITHUB);
            user.setAbout("This is account is created using github");

        }
        else if (authorizedClientRegistrationId.equalsIgnoreCase("linkedin")){

            //linkedin
            //linkedin attributes

        }
        else{

            //unknown provider
            logger.info("OAuthAuthenticationSuccessHandler: Unknown Provider");

        }

        //google
        //google attributes




        //github
        //github attributes




        //facebook
        //facebook attributes







/*

        DefaultOAuth2User user=(DefaultOAuth2User)authentication.getPrincipal();

//        logger.info(user.getName()); //name print kiya
//
//        user.getAttributes().forEach((key,value) -> { //attributes print kiye
//            logger.info("{} => {}", key, value) ;
//        });
//
//        logger.info(user.getAuthorities().toString());

        //data database mea save

        String email = user.getAttribute("email").toString();
        String name = user.getAttribute("name").toString();
        String picture = user.getAttribute("picture").toString();


        //create user and save in database
        User user1 = new User();
        user1.setEmail(email);
        user1.setName(name);
        user1.setProfilePic(picture);
        user1.setPassword("password");
        user1.setUserId(UUID.randomUUID().toString());
        user1.setProvider(Providers.GOOGLE);
        user1.setEnabled(true);

        user1.setEmailVerified(true);
        user1.setProviderUserId(user.getName());
        user1.setRoleList(List.of(AppConstants.ROLE_USER));
        user1.setAbout("This account is created using google...");


        User user2 = userRepository.findByEmail(user1.getEmail()).orElse(null);
        if (user2==null){
            userRepository.save(user1);
            logger.info("User Saved: "+ user1.getEmail());
        }


 */

        User user2 = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (user2==null){
            userRepository.save(user);
            System.out.println("user saved: "+user.getEmail());
        }





        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

    }
}
