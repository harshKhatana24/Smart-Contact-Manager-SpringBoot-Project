package com.SCM.SCM20.Services.Impl;


import com.SCM.SCM20.Entity.User;
import com.SCM.SCM20.Helper.AppConstants;
import com.SCM.SCM20.Helper.Helper;
import com.SCM.SCM20.Helper.ResourceNotFoundException;
import com.SCM.SCM20.Repository.UserRepository;
import com.SCM.SCM20.Services.EmailService;
import com.SCM.SCM20.Services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;


    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public User saveUser(User user) {
        //user id : have to generate
        String userId = UUID.randomUUID().toString(); //random userid produce karega lambi si
        user.setUserId(userId);
        //password encode
        //user.setPassword(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //set the user role

        user.setRoleList(List.of(AppConstants.ROLE_USER));

        logger.info(user.getProvider().toString());

        String emailToken=UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        User saveUser = userRepository.save(user);
        String emailLink=Helper.getLinkForEmailVerification(emailToken);
        emailService.sendEmail(saveUser.getEmail(),"Verify Account : Smart Contact Manager",emailLink);

        return saveUser;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {

        //ya tu user milaga ya phir exception throw karenga
        //ResourceNotFoundException --> yea aak custome class banaiye hui hai to deal with this exception
        User user2 = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        //update karenge user2 from user ---> updated user
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
//        user2.setRole(user.getRole());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());


        //save the user in database
        User saveUser=userRepository.save(user2);
        return Optional.ofNullable(saveUser);
    }

    @Override
    public void deleteUser(String id) {
        User user2 = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user2);
    }

    @Override
    public boolean isUserExists(String id) {
        User user2 = userRepository.findById(id)
                .orElse(null);

        return user2 != null ? true : false;
    }

    @Override
    public boolean isUSerExistsByEmail(String email) {
        User user2=userRepository.findByEmail(email)
                .orElse(null);

        if (user2 == null)
            return false;
        else
            return true;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String username) {
        return userRepository.findByEmail(username).orElseThrow(null);//null ko bi handle kara ha humne rootcontroller mea
    }


}
