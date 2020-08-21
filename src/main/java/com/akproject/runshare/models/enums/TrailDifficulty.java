package com.akproject.runshare.models.enums;

public enum TrailDifficulty {

    BEGINNER("Beginner", "Flat, short, padded, shaded, indoors, or otherwise easy", 1, "&#9733 &#9734 &#9734 &#9734 &#9734"),
    NOVICE("Novice", "Slight hills, up to a mile, solid running surface, etc.", 2, "&#9733 &#9733 &#9734 &#9734 &#9734"),
    AVERAGE("Average", "Some significant hills, half exposed, somewhat broken or rough surface", 3, "&#9733 &#9733 &#9733 &#9734 &#9734"),
    CHALLENGING("Challenging", "Very few flat locations, almost no shade, gravel or uneven surfaces", 4, "&#9733 &#9733 &#9733 &#9733 &#9734"),
    EXPERT("Expert", "All hills, no shade at all, dirt or unmarked trail", 5, "&#9733 &#9733 &#9733 &#9733 &#9733");
//todo-add star ratings to enum for easier view display

    private final String displayName;
    private final String description;
    private final Integer numberLevel;
    private final String starLevel;

    TrailDifficulty(String displayName, String description, Integer numberLevel, String starLevel){
        this.displayName=displayName;
        this.description=description;
        this.numberLevel=numberLevel;
        this.starLevel=starLevel;
    }

    public String getDisplayName() { return displayName; }

    public String getDescription() { return description; }

    public Integer getNumberLevel() { return numberLevel; }

    public String getStarLevel() { return starLevel; }
}
