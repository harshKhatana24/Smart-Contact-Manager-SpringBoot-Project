package com.SCM.SCM20.Config;


import com.SCM.SCM20.Config.onAuthFailureHandler;
import com.SCM.SCM20.Services.Impl.SecurityCustomUserDetailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityCustomUserDetailService userDetailService;


    @Autowired
    private OAuthAuthenticationSuccessHandler oAuthAuthenticationSuccessHandler;

    @Autowired
    private onAuthFailureHandler authFailureHandler;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/user/**").authenticated();
                    authorize.anyRequest().permitAll();
                })

                .formLogin(formLogin -> {
                    formLogin.loginPage("/login") // Custom login page endpoint
                            .loginProcessingUrl("/authenticate") // Endpoint for login form submission
                            .defaultSuccessUrl("/user/dashboard", true) // Redirect to dashboard on success
                            .failureUrl("/login?error=true") // Redirect to login page with error on failure
                            .usernameParameter("email") // Customize parameter names
                            .passwordParameter("password");


                    //user unabled
                    formLogin.failureHandler(authFailureHandler);

                });




        httpSecurity.csrf(AbstractHttpConfigurer::disable); //csrf disable hai puri website mea
        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });


        //Oauth Configuration
        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login")
                    .successHandler(oAuthAuthenticationSuccessHandler);

        });




        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
















    /*

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.core.userdetails.User;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.provisioning.InMemoryUserDetailsManager;




    //user create and login using java code with in memory service
    @Bean
    public UserDetailsService userDetailsService(){



        //not used in production
        UserDetails user1 = User
                .withDefaultPasswordEncoder()
                .username("admin123")
                .password("admin123")
                .roles("ADMIN","USER")
                .build();

        UserDetails user2 = User
                .withDefaultPasswordEncoder()
                .username("user123")
                .password("user123")
//                .roles("ADMIN")
                .build();


        var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1, user2);
        return inMemoryUserDetailsManager;
             */








}
