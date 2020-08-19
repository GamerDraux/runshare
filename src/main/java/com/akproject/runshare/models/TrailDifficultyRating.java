package com.akproject.runshare.models;

import com.akproject.runshare.models.enums.TrailDifficulty;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class TrailDifficultyRating extends AbstractEntity {

    private TrailDifficulty difficulty;

    @ManyToOne
    private Runner runner;

    @ManyToOne
    private Trail trail;

    public TrailDifficultyRating(TrailDifficulty difficulty, Runner runner, Trail trail){
        this.difficulty=difficulty;
        this.runner=runner;
        this.trail=trail;
    }

    public TrailDifficultyRating(){}

    public TrailDifficulty getDifficulty() { return difficulty; }

    public void setDifficulty(TrailDifficulty difficulty) { this.difficulty = difficulty; }

    public Runner getRunner() { return runner; }

    public void setRunner(Runner runner) { this.runner = runner; }

    public Trail getTrail() { return trail; }

    public void setTrail(Trail trail) { this.trail = trail; }
}
