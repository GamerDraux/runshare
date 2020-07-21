package com.akproject.runshare.models;

import com.akproject.runshare.RunshareApplication;
import com.akproject.runshare.controllers.RunnerController;
import com.akproject.runshare.models.data.RunnerRepository;
import com.akproject.runshare.models.enums.Gender;
import com.akproject.runshare.models.enums.RunnerLevel;
import com.mysql.cj.jdbc.Blob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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

    @NotNull(message="Age cannot be null")
    private int age;

    @Min(value = 0, message="Weight cannot be set lower than 0")
    private int weight;

    @NotNull(message="Gender cannot be null")
    private Gender gender;

    @NotNull(message="Runner level cannot be null")
    private RunnerLevel runnerLevel;

    @NotNull(message="Zip code cannot be null")
    private String zip;
    //todo-create input field for new runner zip code in add runner
    //todo-create display for runner zip code in runner table
    //todo-create display for runner zip code runner details

    @OneToMany(mappedBy="runner")
    private final List<RunSession> runSessions= new ArrayList<>();

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    //Constructors
    public Runner() {
    }

    public Runner (String callsign, String firstName, String lastName, Boolean callsignOnly, String password, int age, int weight, Gender gender, RunnerLevel runnerLevel, String zip){
        this.callsign=callsign;
        this.firstName=firstName;
        this.lastName=lastName;
        this.callsignOnly=callsignOnly;
        this.pwHash= encoder.encode(password);
        this.age=age;
        this.weight = weight;
        this.gender= gender;
        this.runnerLevel= runnerLevel;
        this.zip = zip;
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

    public int getWeight() { return weight; }

    public Gender getGender() { return gender; }

    public RunnerLevel getRunnerLevel() { return runnerLevel; }

    public String getZip() { return zip; }

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


    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setRunningLevel(RunnerLevel runnerLevel) {
        this.runnerLevel = runnerLevel;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public boolean isMatchingPassword(String password){
        return encoder.matches(password, pwHash);
    }


}
