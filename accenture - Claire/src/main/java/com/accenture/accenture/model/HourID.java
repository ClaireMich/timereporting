package com.accenture.accenture.model;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class HourID implements Serializable {

    @NotNull
    @Column(name = "month")
    private int month;

    @NotNull
    @Column(name = "year")
    private int year;

    @NotNull
    @Column(name = "referencepk")
    private int referencepk;

    public HourID(@NotNull int month, @NotNull int year, @NotNull int referencepk) {
        this.month = month;
        this.year = year;
        this.referencepk = referencepk;
    }

    public HourID() {
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getReferencepk() {
        return referencepk;
    }

    public void setReferencepk(int referencepk) {
        this.referencepk = referencepk;
    }
}
