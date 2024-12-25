package com.SCM.SCM20.Controller;


import com.SCM.SCM20.Entity.Contact;
import com.SCM.SCM20.Entity.User;
import com.SCM.SCM20.Forms.ContactForm;
import com.SCM.SCM20.Forms.ContactSearchForm;
import com.SCM.SCM20.Helper.AppConstants;
import com.SCM.SCM20.Helper.Helper;
import com.SCM.SCM20.Helper.Message;
import com.SCM.SCM20.Helper.MessageType;
import com.SCM.SCM20.Services.ContactService;
import com.SCM.SCM20.Services.ImageService;
import com.SCM.SCM20.Services.UserServices;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private final ContactService contactService;
    private final UserServices userServices;
    private final ImageService imageService;

    public ContactController(ContactService contactService, UserServices userServices, ImageService imageService) {
        this.contactService = contactService;
        this.userServices = userServices;
        this.imageService = imageService;
    }

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    //add contact page : handler
    @RequestMapping("/add")
    public String addContactView(Model model){

        ContactForm contactForm=new ContactForm();
//        contactForm.setName("Samrat Harsh Gujjar");
//        contactForm.setFavourite(true);

        model.addAttribute("contactForm",contactForm);

        return "user/add_contact";
    }



    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm,
                              BindingResult bindingResult,
                              Authentication authentication,
                              HttpSession httpSession){

        //validation
        if (bindingResult.hasErrors()){

            bindingResult.getAllErrors().forEach(errors -> System.out.println(errors));

            Message message = new Message();
            message.setType(MessageType.red);
            message.setContent("Please correct the following errors");

            httpSession.setAttribute("message",message);
            return "user/add_contact";
        }


        //process the form data
        String username = Helper.getEmailOfLoggedInUser(authentication);

        //form ---> contact
        User user=userServices.getUserByEmail(username);

        // image process

//        System.out.println();
//        System.out.println("file information : "+contactForm.getContactImg().getOriginalFilename());
//        System.out.println();

        //upload karne ka code

        // Handle image upload
        String fileURL = null;
        String filename = UUID.randomUUID().toString();
        if (contactForm.getContactImg() != null && !contactForm.getContactImg().isEmpty()) {
            try {
                fileURL = imageService.uploadImage(contactForm.getContactImg(), filename);
            } catch (RuntimeException e) {
                e.getMessage();
                return "redirect:/user/contacts/add";
            }
        }




        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavourite(contactForm.isFavourite());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());

        contact.setPicture(fileURL);
        contact.setCloudinaryImagePublicId(filename);

        contact.setUser(user);

        contactService.save(contact);
        System.out.println(contactForm);

        Message message1 = new Message();
        message1.setType(MessageType.green);
        message1.setContent("Contact added successfully !");

        httpSession.setAttribute("message", message1);
        return "redirect:/user/contacts/add";
    }


    //view contacts
    @RequestMapping("/view")
    public String viewContacts(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = AppConstants.CONTACT_PAZE_SIZE) int size,
            @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
            @RequestParam(value = "direction",defaultValue = "asc") String direction,
            Authentication authentication, Model model){

        //load all the user contacts
        String username=Helper.getEmailOfLoggedInUser(authentication);

        User user=userServices.getUserByEmail(username);

        Page<Contact> pageContact=contactService.getByUser(user,page,size,sortBy,direction);

        model.addAttribute("contactSearchForm",new ContactSearchForm());

        model.addAttribute("pageContact",pageContact);
        model.addAttribute("pageSize",AppConstants.CONTACT_PAZE_SIZE);
        model.addAttribute("previousPage",pageContact.getNumber()-1);
        model.addAttribute("nextPage",pageContact.getNumber()+1);

        return "user/contacts";
    }



    //search handler
    @RequestMapping("/search")
    public String searchHandler(
            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "size", defaultValue = AppConstants.CONTACT_PAZE_SIZE) int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication
    ){

        logger.info("field {} keyword {}",contactSearchForm.getField(),contactSearchForm.getValue());

        //agar yea nhi karenge tu yea pure db mea search karenga na ki sirf uss user ke data ko
        var user=userServices.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContact = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")){
            pageContact = contactService.searchByName(contactSearchForm.getValue(),size,page,sortBy,direction,user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(),size,page,sortBy,direction,user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(),size,page,sortBy,direction,user);
        }

        logger.info("pageContact {}",pageContact);

        model.addAttribute("contactSearchForm",contactSearchForm);

        model.addAttribute("pageContact", pageContact);

        model.addAttribute("pageSize", AppConstants.CONTACT_PAZE_SIZE);
        model.addAttribute("previousPage",pageContact.getNumber()-1);
        model.addAttribute("nextPage",pageContact.getNumber()+1);


        return "user/search";
    }



    //delete handler
    @RequestMapping("/delete/{contactId}")
    public String deleteHandler(@PathVariable("contactId") String id,
                                HttpSession session
                                ){

        contactService.delete(id);
        logger.info("contact {} deleted",id);

        Message message = new Message();
        message.setType(MessageType.green);
        message.setContent("Contact Deleted Successfully !");
        session.setAttribute("message",message);

        return "redirect:/user/contacts/view";
    }



    //update contact form view
    @GetMapping("/view/{contactId}")
    public String updateContactFormView(@PathVariable("contactId") String contactId,
                                        Model model){

        var contact=contactService.getById(contactId);

        ContactForm contactForm=new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setFavourite(contact.isFavourite());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setWebsiteLink(contact.getWebsiteLink());

        contactForm.setPicture(contact.getPicture());
        contactForm.setDescription(contact.getDescription());

        model.addAttribute("contactForm",contactForm);
        model.addAttribute("contactId",contactId);

        return "user/update-contact-view";
    }


    @RequestMapping(value = "/update/{contactId}", method = RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,
                                @Valid @ModelAttribute ContactForm contactForm,
                                BindingResult bindingResult,
                                Model model) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {

            Message message=new Message();
            message.setType(MessageType.red);
            message.setContent("please correct the errors below");

            model.addAttribute("message",message);

            return "user/update-contact-view"; // Return view for correcting form inputs
        }

        // Fetch the existing contact by ID
        Contact existingContact = contactService.getById(contactId);
        if (existingContact == null) {
            logger.error("Contact with ID {} not found.", contactId);
            model.addAttribute("error", "Contact not found.");
            return "redirect:/user/contacts";
        }

        // Update fields with new data
        existingContact.setName(contactForm.getName());
        existingContact.setEmail(contactForm.getEmail());
        existingContact.setPhoneNumber(contactForm.getPhoneNumber());
        existingContact.setAddress(contactForm.getAddress());
        existingContact.setFavourite(contactForm.isFavourite());
        existingContact.setLinkedInLink(contactForm.getLinkedInLink());
        existingContact.setWebsiteLink(contactForm.getWebsiteLink());
        existingContact.setDescription(contactForm.getDescription());

        // Process and update the image if provided
        if (contactForm.getContactImg() != null && !contactForm.getContactImg().isEmpty()) {
            String fileName = UUID.randomUUID().toString(); // Generate unique file name
            String imageURL = imageService.uploadImage(contactForm.getContactImg(), fileName);

            // Update the image details in the contact
            existingContact.setCloudinaryImagePublicId(fileName);
            existingContact.setPicture(imageURL);
            contactForm.setPicture(imageURL); // Update the form object as well
        } else {
            logger.info("No new image provided. Retaining existing image.");
        }

        // Save the updated contact
        Contact updatedContact = contactService.update(existingContact);
        logger.info("Updated contact with ID {}", updatedContact.getId());

        // Add success message to the model
        Message message = new Message();
        message.setType(MessageType.green);
        message.setContent("Contact updated successfully.");
        model.addAttribute("message", message);

        // Redirect to the updated contact's view page
        return "redirect:/user/contacts/view/" + contactId;
    }


}
