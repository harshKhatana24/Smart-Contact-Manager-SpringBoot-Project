package com.SCM.SCM20.Controller;


import com.SCM.SCM20.Entity.Contact;
import com.SCM.SCM20.Services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //means hum json ko data return kar rhe hai
@RequestMapping("/api")
public class ApiController {

    //get contact of user

    @Autowired
    private ContactService contactService;

    @GetMapping("/contact/{contactId}")
    public Contact getContact(@PathVariable String contactId){
        return contactService.getById(contactId);
    }

}
