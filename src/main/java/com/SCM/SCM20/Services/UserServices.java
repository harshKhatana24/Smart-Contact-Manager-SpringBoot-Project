package com.SCM.SCM20.Services;


import com.SCM.SCM20.Entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserServices {

    User saveUser(User user);

    Optional<User> getUserById(String id);

    Optional<User> updateUser(User user);

    void deleteUser(String id);

    boolean isUserExists(String id);

    boolean isUSerExistsByEmail(String email);

    List<User> getAllUsers();

    User getUserByEmail(String username);


    //add more methods here related user service[logic]

}
