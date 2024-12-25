package com.SCM.SCM20.Helper;

import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Principal;


public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {
        // Check if the authentication object is for an OAuth2 login
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            String clientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();
            String username = "";

            // Sign in with Google
            if ("google".equalsIgnoreCase(clientRegistrationId)) {
                username = oAuth2User.getAttribute("email");
                System.out.println("Getting email from google");
            }
            // Sign in with GitHub
            else if ("github".equalsIgnoreCase(clientRegistrationId)) {
                username = oAuth2User.getAttribute("email") != null
                        ? oAuth2User.getAttribute("email")
                        : oAuth2User.getAttribute("login") + "github@gmail.com";
                System.out.println("Getting email from github");
            }
            // Additional cases can be handled here (e.g., LinkedIn)

            return username;
        } else {
            System.out.println("Getting data from local database");
            return authentication.getName();
        }
    }

    public static String getLinkForEmailVerification(String emailToken){

        String link = "http://localhost:8081/auth/verify-email?token=" + emailToken;

        return link;
    }

}
