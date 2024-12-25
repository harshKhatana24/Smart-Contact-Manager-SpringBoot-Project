package com.SCM.SCM20.Entity;


import jakarta.persistence.*;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Builder
public class User implements UserDetails {

    @Id
    private String userId;
    @Column(name = "user_name", nullable = false)
    private String name;
    @Column(name = "user_email", nullable = false)
    private String email;
    private String password;
    @Column(length = 500)
    private String about;
    private String profilePic;
    private String phoneNumber;

    //information
    private boolean enabled=false;
    private boolean emailVerified=false;
    private boolean phoneVerified=false;

    @Enumerated(value = EnumType.STRING)
    //SELF, GOOGLE, FACEBOOK, TWITTER, LINKEDIN, GTIHUB
    private Providers provider=Providers.SELF;
    private String providerUserId;

    //add more fields if needed
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
    //FetchType.LAZY --> jab tak contacts ki zrurat nhi tab tak fetch nhi karenge database se (Query)
    //cascade  type matlab agar user delete ho tu uske sare contacts delete ho jaye
    //When orphanRemoval is set to true, it automatically removes child entities that are no longer referenced by the parent entity.
    private List<Contact> contacts = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER) //element collection se save kar sakte hai
    private List<String> roleList = new ArrayList<>();

    private String emailToken;


    public User() {

    }

















    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //list of roles[USER,ADMIN]
        //Collection of SimpleGrantedAuthority[roles{ADMIN,USER}]
        Collection<SimpleGrantedAuthority> roles=roleList.stream()
                .map(role-> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
        return roles;
    }

    //for this project email id is our username
    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    public boolean isEnabled() {
        return this.enabled;
    }







    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public Providers getProvider() {
        return provider;
    }

    public void setProvider(Providers provider) {
        this.provider = provider;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    public String getEmailToken() {
        return emailToken;
    }

    public void setEmailToken(String emailToken) {
        this.emailToken = emailToken;
    }

    // Constructor with parameters
    public User(String name, String email, String password, String about, String profilePic, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.about = about;
        this.profilePic = profilePic;
        this.phoneNumber = phoneNumber;
    }

    public User(String userId, String name, String email, String password, String about, String profilePic, String phoneNumber, boolean enabled, boolean emailVerified, boolean phoneVerified, Providers provider, String providerUserId, List<Contact> contacts) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.about = about;
        this.profilePic = profilePic;
        this.phoneNumber = phoneNumber;
        this.enabled = enabled;
        this.emailVerified = emailVerified;
        this.phoneVerified = phoneVerified;
        this.provider = provider;
        this.providerUserId = providerUserId;
        this.contacts = contacts;
    }
}
