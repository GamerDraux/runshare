package com.akproject.runshare.models;

import com.akproject.runshare.models.staticMethods.DistanceConversion;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Trail extends AbstractEntity {

    @NotBlank(message="Trail name required")
    private String name;

    private double miles;

    private double kilometers;

    private String address;

    @Size(max=5, min=5, message="Must use 5-digit zip code")
    private String zipCode;

    private Integer numberZipCode;

    @OneToMany(mappedBy="trail")
    private final List<Comment> comments= new ArrayList<>();

//    @OneToMany(mappedBy = "commentRunSessionTags")
//    private final List<RunSession> runSessions = new ArrayList<>();

    public Trail (String name, double miles, String address, String zipCode ){
        this.name=name;
        this.miles=miles;
        this.kilometers= DistanceConversion.milesToKilometers(miles);
        this.address=address;
        this.zipCode=zipCode;
        this.numberZipCode= Integer.parseInt(zipCode);
    }

    public Trail (){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMiles() {
        return miles;
    }

    public void setMiles(double miles) {
        this.miles = miles;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public double getKilometers() {
        return kilometers;
    }

    public void setKilometers(double kilometers) {
        this.kilometers = kilometers;
    }

    public Integer getNumberZipCode() { return numberZipCode; }

    public void setNumberZipCode(Integer numberZipCode) { this.numberZipCode = numberZipCode; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o)) return false;
        Trail trail = (Trail) o;
        return name.equals(trail.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
