package com.akproject.runshare.models;

import com.akproject.runshare.models.staticMethods.TimeConversion;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class RunSession extends AbstractEntity{

    @NotBlank(message = "Run Session needs to be named")
    @NotNull(message ="Run Session needs to be named")
    private String name;

    @NotNull
    @NotBlank
    private String date;

    @ManyToOne
    private Runner runner;

    @ManyToOne
    private Trail trail;

    private Integer time;

    public RunSession (String name, String date, Runner runner, Trail trail, Integer time){
        this.name = name;
        this.date = date;
        this.runner= runner;
        this.trail = trail;
        this.time = time;
    }

    public RunSession (){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Runner getRunner() {
        return runner;
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
    }

    public Trail getTrail() {
        return trail;
    }

    public void setTrail(Trail trail) {
        this.trail = trail;
    }

    public String getTime() {
        return TimeConversion.displayTimeAsString(time);
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
