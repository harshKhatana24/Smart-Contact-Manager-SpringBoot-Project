package com.SCM.SCM20.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Contact {

    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;
    @Column(length = 500)
    private String description;
    private boolean favourite = false;
    private String websiteLink;
    private String linkedInLink;

    private String cloudinaryImagePublicId;

    @ManyToOne
    @JsonIgnore //varna recursivley json data aayega ex-/api/contact/e807fc53-b4c4-47a2-8e43-26503c793664
    private User user;

    @OneToMany(mappedBy = "contacts",cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SocialLinks> socialLinks = new ArrayList<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<SocialLinks> getSocialLinks() {
        return socialLinks;
    }

    public void setSocialLinks(List<SocialLinks> socialLinks) {
        this.socialLinks = socialLinks;
    }

    public String getCloudinaryImagePublicId() {
        return cloudinaryImagePublicId;
    }

    public void setCloudinaryImagePublicId(String cloudinaryImagePublicId) {
        this.cloudinaryImagePublicId = cloudinaryImagePublicId;
    }

    public Contact(String id, String name, String email, String phoneNumber, String address, String picture, String description, boolean favourite, String websiteLink, String linkedInLink, String cloudinaryImagePublicId, User user, List<SocialLinks> socialLinks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.picture = picture;
        this.description = description;
        this.favourite = favourite;
        this.websiteLink = websiteLink;
        this.linkedInLink = linkedInLink;
        this.cloudinaryImagePublicId = cloudinaryImagePublicId;
        this.user = user;
        this.socialLinks = socialLinks;
    }

    public Contact() {
    }
}
