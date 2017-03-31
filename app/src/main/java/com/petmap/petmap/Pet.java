package com.petmap.petmap;

/**
 * Created by User on 2017.03.18.
 */

public class Pet {

    // Pet Data
    private String name;
    private int year;
    private int month;
    private int day;

    // Pet Collar Information
    private String telNo;
    private String code;

    // Database Primary Key
    private int id;

    public Pet(String name, String telNo, String code) {
        this.name = name;
        this.telNo = telNo;
        this.code = code;
    }

    public Pet() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
