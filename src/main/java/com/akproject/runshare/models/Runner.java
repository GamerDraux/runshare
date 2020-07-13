package com.akproject.runshare.models;

import com.akproject.runshare.controllers.RunnerController;
import com.akproject.runshare.models.data.RunnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

@Entity
public class Runner extends AbstractEntity{


//Fields
    @NotNull(message="Runner callsign is required")
    @Size(min=2, max=30, message="Runner callsign must be between 2 and 30 characters")
    private String callsign;

    @Size(min=2, max=30, message="First name must be between 2 and 30 characters")
    private String firstName;

    @Size(min=2, max=30, message="Last Name must be between 2 and 30 characters")
    private String lastName;

    @NotNull
    private boolean callsignOnly;

    @NotNull
    private String pwHash;

    private int age;
    //TODO -Add additional fields for weight, gender, running level, zip

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

//Constructors
    public Runner() {
    }

    public Runner (String callsign, String firstName, String lastName, Boolean callsignOnly, String password, int age){
        this.callsign=callsign;
        this.firstName=firstName;
        this.lastName=lastName;
        this.callsignOnly=callsignOnly;
        this.pwHash= encoder.encode(password);
        this.age=age;
    }

//getters

    public String getCallsign() {
        return callsign;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isCallsignOnly() {
        return callsignOnly;
    }

    public String getPwHash() {
        return pwHash;
    }

    public Integer getAge() {
        return age;
    }

//setters

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCallsignOnly(boolean callsignOnly) {
        this.callsignOnly = callsignOnly;
    }

    public void setPassword(String password) {
        this.pwHash = encoder.encode(password);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMatchingPassword(String password){
        return encoder.matches(password, pwHash);
    }

}
