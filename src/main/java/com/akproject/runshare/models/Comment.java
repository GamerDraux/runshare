package com.akproject.runshare.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
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
//
//    @NotNull
//    private LocalDateTime messageDate;
//
//    @ManyToMany
//    private final List<Runner> commentRunnerTags = new ArrayList<>();


//    constructors
    public Comment (){}

    public Comment (String messageTitle, String message, Runner messageCreator){
        this.messageTitle=messageTitle;
        this.message=  message;
        this.messageCreator= messageCreator;
    }

//    getters


    public String getMessageTitle() { return messageTitle; }

    public String getMessage() {
        return message;
    }

    public Runner getMessageCreator() { return messageCreator; }



    //    Setters


    public void setMessageTitle(String messageTitle) { this.messageTitle = messageTitle; }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageCreator(Runner messageCreator) { this.messageCreator = messageCreator; }
}
