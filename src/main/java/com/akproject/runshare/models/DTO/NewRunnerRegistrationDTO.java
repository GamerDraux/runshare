package com.akproject.runshare.models.DTO;

import com.akproject.runshare.models.enums.Gender;
import com.mysql.cj.jdbc.Blob;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;

public class NewRunnerRegistrationDTO extends RunnerLoginDTO {
    @NotNull
    @NotBlank(message="First name is required.  If you do not wish your name to be displayed, please check the 'Callsign only' box below")
    @Size(min=2, max=30, message="First name must be 2-30 characters")
    private String firstName;@NotBlank(message="Last name is required.  If you do not wish your name to be displayed, please check the 'Callsign only' box below")

    @NotNull
    @NotBlank(message="Last name is required.  If you do not wish your name to be displayed, please check the 'Callsign only' box below")
    @Size(min=2, max=30, message="Last name must be 2-30 characters")
    private String lastName;

    @NotNull
    private boolean callsignOnly;

    @NotNull
    @NotBlank(message="Verification password is required")
    @Size(min=8, max=20,message="All Runner passwords are between 8 and 20 characters")
    private String verifyPassword;

    @NotNull(message="Age required for tracking")
    private int age;

    private int weight;

    private Gender gender;

    private String runningLevel;

    private String zip;

    public NewRunnerRegistrationDTO(){}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isCallsignOnly() {
        return callsignOnly;
    }

    public void setCallsignOnly(boolean callsignOnly) {
        this.callsignOnly = callsignOnly;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getRunningLevel() {
        return runningLevel;
    }

    public void setRunningLevel(String runningLevel) {
        this.runningLevel = runningLevel;
    }

    @Length(min=5, max=5, message="Must use 5-digit zip code")
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
