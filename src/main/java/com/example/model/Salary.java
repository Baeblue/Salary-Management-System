package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Date date;
    private Integer startTime;
    private Integer endTime;
    private Integer day;
    private Integer night;
    private Integer totalTime;

    public Salary(String name, Integer startTime, Integer endTime) {
        this.date = new Date();
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = CalculateDay();
        this.night = CalculateNight();
        this.totalTime = total();
    }

    public Integer CalculateDay() {
        day = 0;

        if(startTime >= 6 && endTime <= 22) {
            day = endTime - startTime;
        }
        if(endTime <= 22) {
            day = endTime - 6;
        }
        if(startTime >= 6) {
            day = 22 - startTime;
        }
        if(startTime < 6 && endTime > 22) {
            day = 16;
        }
        if(startTime < 22 && endTime > 6) {
            day = (22 - startTime) + (endTime - 6);
        }
        return day;
    }

    public Integer CalculateNight() {
        night = 0;

        if(endTime <= 22) {
            night = 6 - startTime;
        }
        if(startTime >= 6) {
            night = endTime - 22;
        }
        if(startTime >= 22 && endTime <= 6) {
            night = (24 - startTime) + endTime;
        }
        if(startTime < 6 && endTime > 22) {
            night = (6 - startTime) + (endTime - 22);
        }
        if(startTime < 22 && endTime > 6) {
            night = 8;
        }
        return night;
    }

    public Integer total() {
        totalTime = 0;

        totalTime = this.day + this.night;
        return totalTime;
    }
}
