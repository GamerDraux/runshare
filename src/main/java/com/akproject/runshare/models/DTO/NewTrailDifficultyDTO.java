package com.akproject.runshare.models.DTO;

import com.akproject.runshare.models.enums.TrailDifficulty;

public class NewTrailDifficultyDTO {

    private TrailDifficulty trailDifficulty;

    public NewTrailDifficultyDTO(){};

    public TrailDifficulty getTrailDifficulty() { return trailDifficulty; }

    public void setTrailDifficulty(TrailDifficulty trailDifficulty) { this.trailDifficulty = trailDifficulty; }
}
