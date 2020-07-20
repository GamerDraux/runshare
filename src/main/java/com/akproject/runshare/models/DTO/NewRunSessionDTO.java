package com.akproject.runshare.models.DTO;

import com.akproject.runshare.models.AbstractEntity;
import com.akproject.runshare.models.Runner;
import com.akproject.runshare.models.Trail;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewRunSessionDTO extends AbstractEntity {

    @NotBlank
    private String name;

    private String date;

    @NotNull
    private Runner runner;

    @NotNull
    private Trail trail;

    public NewRunSessionDTO (){};

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
}
