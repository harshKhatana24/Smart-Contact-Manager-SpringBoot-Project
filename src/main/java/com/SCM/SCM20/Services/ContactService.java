package com.SCM.SCM20.Services;


import com.SCM.SCM20.Entity.Contact;
import com.SCM.SCM20.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService {

    //save contact
    Contact save(Contact contact);

    //update contact
    Contact update(Contact contact);

    //get contact
    List<Contact> getAll();

    //get contact by id
    Contact getById(String id);

    //delete contact
    void delete(String id);

    //search contact
    Page<Contact> searchByName(String name, int size, int page, String sortBy, String order,User user);

    Page<Contact> searchByEmail(String email, int size, int page, String sortBy, String order, User user);

    Page<Contact> searchByPhoneNumber(String phoneNumber, int size, int page, String sortBy, String order, User user);

    //get contact by userId
    List<Contact> getByUserId(String id);

    //
    Page<Contact> getByUser(User user, int page, int size, String sortField, String sortDirection);

}
