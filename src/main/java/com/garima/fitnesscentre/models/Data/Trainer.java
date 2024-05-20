package com.garima.fitnesscentre.models.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="trainers")
@Data
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String gender;

    private String city;

    private String state;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="centre_id")
    private String centreId;
    public Trainer(int id, String name, String gender, String city, String state, String phoneNumber, String centreId) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.city = city;
        this.state = state;
        this.phoneNumber = phoneNumber;
        this.centreId = centreId;
    }
    public Trainer() {
    }
}