package com.accenture.accenture.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "hours")
public class Hour {

    @EmbeddedId
    private HourID hourID;
    @NotNull
    @Column(name = "submittingdate")
    private Timestamp submittingdate;
    @NotNull
    @Column(name = "totalhours")
    private long totalhours;
    @NotNull
    @Column(name = "week1")
    private long week1;
    @NotNull
    @Column(name = "week2")
    private long week2;
    @NotNull
    @Column(name = "week3")
    private long week3;
    @NotNull
    @Column(name="week4")
    private long week4;

    public Hour(int month, int year, Timestamp date, int week1, int week2, int week3, int week4, int employeeId){
        HourID hourID = new HourID(month, year, employeeId);
        this.hourID= hourID;
        this.submittingdate = date;
        this.week1= week1;
        this.week2=week2;
        this.week3=week3;
        this.week4=week4;
        this.totalhours= this.week1+this.week2+this.week3+this.week4;
    }

    public Hour() {
    }

    public HourID getHourID() {
        return hourID;
    }

    public void setHourID(HourID hourID) {
        this.hourID = hourID;
    }

    public Timestamp getSubmittingdate() {
        return submittingdate;
    }

    public void setSubmittingdate(Timestamp submittingdate) {
        this.submittingdate = submittingdate;
    }

    public long getTotalhours() {
        return totalhours;
    }

    public void setTotalhours(long totalhours) {
        this.totalhours = totalhours;
    }

    public long getWeek1() {
        return week1;
    }

    public void setWeek1(long week1) {
        this.week1 = week1;
    }

    public long getWeek2() {
        return week2;
    }

    public void setWeek2(long week2) {
        this.week2 = week2;
    }

    public long getWeek3() {
        return week3;
    }

    public void setWeek3(long week3) {
        this.week3 = week3;
    }

    public long getWeek4() {
        return week4;
    }

    public void setWeek4(long week4) {
        this.week4 = week4;
    }
}
