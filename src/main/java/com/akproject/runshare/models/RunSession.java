package com.akproject.runshare.models;

import com.akproject.runshare.models.staticMethods.DateConversion;
import com.akproject.runshare.models.staticMethods.TimeConversion;
import org.hibernate.dialect.Ingres9Dialect;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RunSession extends AbstractEntity{

    //todo-create a runnerlist for runners on Runsessions
    @NotBlank(message = "Run Session needs to be named")
    @NotNull(message ="Run Session needs to be named")
    private String name;

    @NotNull
    @NotBlank
    private String date;

    @ManyToOne
    private Runner creator;

    @ManyToOne
    private Trail trail;

    private double laps;

    private double distance;

    private Integer time;

    private Integer pace;

    @OneToMany(mappedBy = "runSession")
    private final List<Comment> comments = new ArrayList<>();



    public RunSession (String name, String date, Runner creator, Trail trail, double laps, Integer time){
        this.name = Jsoup.clean(name, Whitelist.none());
        this.date = Jsoup.clean(date, Whitelist.none());
        this.creator= creator;
        this.trail = trail;
        this.laps = laps;
        this.time = time;
        this.distance = (Math.floor((laps*trail.getMiles())*100)/100);
        this.pace = Math.round(Math.round(this.time/this.distance));
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

    public Runner getCreator() { return creator; }

    public void setCreator(Runner creator) { this.creator = creator; }

    public Trail getTrail() {
        return trail;
    }

    public void setTrail(Trail trail) {
        this.trail = trail;
    }

    public double getLaps() { return laps; }

    public void setLaps(double laps) { this.laps = laps; }

    public double getDistance() { return distance; }

    public void setDistance(double distance) { this.distance = distance; }

    public String getPace() { return TimeConversion.displayTimeAsString(pace); }

    public void setPace(Integer pace) { this.pace = pace; }

    public String getTime() {
        return TimeConversion.displayTimeAsString(time);
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getDisplayDate () {return DateConversion.convertYYYYMMDDToDisplayString(date);}

//    public String getDisplayMiles () {return (Math.floor((this.laps*this.trail.getMiles())*100)/100)+" miles";}

}
