package com.SCM.SCM20.Repository;

import com.SCM.SCM20.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {


    //extra methods db related operations
    //custom query methods
    //custom finder methods
    Optional<User> findByEmail(String email);


    Optional<User> findByEmailToken(String token);


}
