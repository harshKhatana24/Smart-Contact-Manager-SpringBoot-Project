package com.SCM.SCM20.Services.Impl;


import com.SCM.SCM20.Entity.Contact;
import com.SCM.SCM20.Entity.User;
import com.SCM.SCM20.Forms.ContactForm;
import com.SCM.SCM20.Helper.ResourceNotFoundException;
import com.SCM.SCM20.Repository.ContactRepository;
import com.SCM.SCM20.Services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactService {

    private ContactRepository contactRepository;

    @Autowired
    ContactServiceImpl(ContactRepository contactRepository){
        this.contactRepository = contactRepository;
    }


    @Override
    public Contact save(Contact contact) {

        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepository.save(contact);

    }



    //update karnge contact
    @Override
    public Contact update(Contact contact) {

        var contactOld=contactRepository.findById(contact.getId())
                .orElseThrow(() -> new ResourceNotFoundException("contact not found"));

        contactOld.setName(contact.getName());
        contactOld.setEmail(contact.getEmail());
        contactOld.setPhoneNumber(contact.getPhoneNumber());
        contactOld.setAddress(contact.getAddress());
        contactOld.setFavourite(contact.isFavourite());
        contactOld.setLinkedInLink(contact.getLinkedInLink());
        contactOld.setWebsiteLink(contact.getWebsiteLink());
        contactOld.setDescription(contact.getDescription());
        contactOld.setPicture(contact.getPicture());

        contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());

        return contactRepository.save(contactOld);
    }



    @Override
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    @Override
    public Contact getById(String id) {
        return contactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("contact not found with given id: "+ id));
    }

    @Override
    public void delete(String id) {
        var contact = contactRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("contact not found with given id: "+ id));

        contactRepository.delete(contact);
    }


    @Override
    public List<Contact> getByUserId(String id) {
        return contactRepository.findByUserId(id);
    }

    @Override
    public Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction) {

        // Ensure a valid sort direction
        Sort sort = "desc".equalsIgnoreCase(direction) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        // Create a pageable instance
        var pageable = PageRequest.of(page, size, sort);

        // Fetch the contacts by user with pagination
        return contactRepository.findByUser(user, pageable);
    }





    //search methods

    @Override
    public Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user) {
        Sort sort = "desc".equalsIgnoreCase(order) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable=PageRequest.of(page, size, sort);
        return contactRepository.findByUserAndNameContaining(user, nameKeyword, pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user) {
        Sort sort = "desc".equalsIgnoreCase(order) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable=PageRequest.of(page, size, sort);
        return contactRepository.findByUserAndEmailContaining(user, emailKeyword, pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneKeyword, int size, int page, String sortBy, String order, User user) {
        Sort sort = "desc".equalsIgnoreCase(order) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable=PageRequest.of(page, size, sort);
        return contactRepository.findByUserAndPhoneNumberContaining(user, phoneKeyword, pageable);
    }


}
