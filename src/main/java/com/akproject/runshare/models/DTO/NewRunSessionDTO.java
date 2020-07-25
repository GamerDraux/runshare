package com.akproject.runshare.models.DTO;

import com.akproject.runshare.models.AbstractEntity;
import com.akproject.runshare.models.Runner;
import com.akproject.runshare.models.Trail;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewRunSessionDTO extends AbstractEntity {

    @NotBlank(message="Run Session needs to be named")
    private String name;

    @NotBlank(message="Must supply a date")
    private String date;

    @NotNull
    private Runner runner;

    @NotNull
    private Trail trail;

    private Integer seconds;
    private Integer minutes;
    private Integer hours;

    public NewRunSessionDTO (){}

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

    public Integer getSeconds() { return seconds; }

    public void setSeconds(Integer seconds) { this.seconds = seconds; }

    public Integer getMinutes() { return minutes; }

    public void setMinutes(Integer minutes) { this.minutes = minutes; }

    public Integer getHours() { return hours; }

    public void setHours(Integer hours) { this.hours = hours; }
}
