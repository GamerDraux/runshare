package com.akproject.runshare.models;

import com.akproject.runshare.models.staticMethods.DateConversion;
import com.akproject.runshare.models.staticMethods.TimeConversion;

import javax.persistence.*;
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

    @ManyToOne
    private Trail trail;

    @ManyToOne
    private RunSession runSession;

    @ManyToMany
    private List<Runner> runners =new ArrayList<>();


//    constructors
    public Comment (){}

    public Comment (String messageTitle, String message, Runner messageCreator, LocalDate dateCreated, LocalTime timeCreated, Trail trail, RunSession runsession, List<Runner> runners){
        this.messageTitle=messageTitle;
        this.message=  message;
        this.messageCreator= messageCreator;
        this.dateCreated=dateCreated;
        this.timeCreated=timeCreated;
        this.trail=trail;
        this.runSession=runsession;
        this.runners = runners;

    }

//    getters


    public String getMessageTitle() { return messageTitle; }

    public String getMessage() { return message; }

    public Runner getMessageCreator() { return messageCreator; }

    public LocalDate getDateCreated() { return dateCreated; }

    public LocalTime getTimeCreated() { return timeCreated; }

    public Trail getTrail() { return trail; }

    public RunSession getRunSession() { return runSession; }

    public List<Runner> getRunners() { return runners; }

    //    Setters


    public void setMessageTitle(String messageTitle) { this.messageTitle = messageTitle; }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageCreator(Runner messageCreator) { this.messageCreator = messageCreator; }

    public void setDateCreated(LocalDate dateCreated) { this.dateCreated = dateCreated; }

    public void setTimeCreated(LocalTime timeCreated) { this.timeCreated = timeCreated; }

    public void setTrail(Trail trail) { this.trail = trail; }

    public void setRunSession(RunSession runSession) { this.runSession = runSession; }

    public String displayStringDate(){
        return DateConversion.convertYYYYMMDDToDisplayString(dateCreated.toString());
    }

    public String displayStringTime(){
        return TimeConversion.displayLocalTimeAsString(timeCreated);
    }

    public void addRunner(Runner runner) { this.runners.add(runner);}



}
