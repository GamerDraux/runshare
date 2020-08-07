package com.akproject.runshare.models;

import com.akproject.runshare.models.staticMethods.DateConversion;
import com.akproject.runshare.models.staticMethods.TimeConversion;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Comment extends AbstractEntity {

    @NotNull
    @Size(max=50, message="Sorry, message titles must be kept under 50 characters")
    private String messageTitle;

    @NotNull(message="A message is requred")
    @Size(max=10000, message="Sorry, messages must be kept under 10000 characters")
    private String message;

    @OneToOne
    @NotNull(message="Comment must be associated with a Runner")
    @Valid
    private Runner messageCreator;

    @NotNull
    private LocalDate dateCreated;

    @NotNull
    private LocalTime timeCreated;

//    @NotNull
//    private String displayTimeCreated;
//
//    @ManyToMany
//    private final List<Runner> commentRunnerTags = new ArrayList<>();


//    constructors
    public Comment (){}

    public Comment (String messageTitle, String message, Runner messageCreator, LocalDate dateCreated, LocalTime timeCreated){
        this.messageTitle=messageTitle;
        this.message=  message;
        this.messageCreator= messageCreator;
        this.dateCreated=dateCreated;
        this.timeCreated=timeCreated;
    }

//    getters


    public String getMessageTitle() { return messageTitle; }

    public String getMessage() {
        return message;
    }

    public Runner getMessageCreator() { return messageCreator; }

    public LocalDate getDateCreated() { return dateCreated; }

    public LocalTime getTimeCreated() { return timeCreated; }

    //    Setters


    public void setMessageTitle(String messageTitle) { this.messageTitle = messageTitle; }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageCreator(Runner messageCreator) { this.messageCreator = messageCreator; }

    public void setDateCreated(LocalDate dateCreated) { this.dateCreated = dateCreated; }

    public void setTimeCreated(LocalTime timeCreated) { this.timeCreated = timeCreated; }

    public String displayStringDate(){
        return DateConversion.convertYYYYMMDDToDisplayString(dateCreated.toString());
    }

    public String displayStringTime(){
        return TimeConversion.displayLocalTimeAsString(timeCreated);
    }
}
