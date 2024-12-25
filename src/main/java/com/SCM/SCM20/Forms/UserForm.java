package com.SCM.SCM20.Forms;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserForm {

    @NotBlank(message = "username is required")
    @Size(min = 3,message = "minimum 3 characters are required")
    private String name;

    @Email(message = "invalid email")
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "password required")
    @Size(min = 6, message = "min 6 digit password is required")
    private String password;


    private String about;
    @Size(min = 8, max = 12, message = "enter valid phone number")
    private String phoneNumber;


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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Override
    public String toString() {
        return "UserForm{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", about='" + about + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}

