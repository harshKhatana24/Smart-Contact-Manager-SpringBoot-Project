package com.SCM.SCM20.Forms;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.multipart.MultipartFile;

public class ContactForm {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "invalid email address [ example@gmail.com ]")
    private String email;

    @NotBlank(message = "phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "invalid phone number")
    private String phoneNumber;

    private String address;

    private String description;

    private boolean favourite;

    private String websiteLink;

    private String linkedInLink;


    //annotation create karenge jo file ko validate karenga
    //size
    //resolution
    //type
    private MultipartFile contactImg;


    //yea contact image update ho jaye us mea help karenga
    private String picture;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public String getLinkedInLink() {
        return linkedInLink;
    }

    public void setLinkedInLink(String linkedInLink) {
        this.linkedInLink = linkedInLink;
    }

    public MultipartFile getContactImg() {
        return contactImg;
    }

    public void setContactImg(MultipartFile contactImg) {
        this.contactImg = contactImg;
    }


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public ContactForm() {
    }

    public ContactForm(String name, String email, String phoneNumber, String address, String description, boolean favourite, String websiteLink, String linkedInLink, MultipartFile profilePic, String picture) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.description = description;
        this.favourite = favourite;
        this.websiteLink = websiteLink;
        this.linkedInLink = linkedInLink;
        this.contactImg = profilePic;
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "ContactForm{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", favourite=" + favourite +
                ", websiteLink='" + websiteLink + '\'' +
                ", linkedInLink='" + linkedInLink + '\'' +
                ", profilePic=" + contactImg +
                '}';
    }
}
