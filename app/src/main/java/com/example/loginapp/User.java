package com.example.loginapp;

public class User {

    private String moodleId, fname, lname, dept, password;

    private int isSuccess;
    private String message;

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public User(String moodleId, String fname, String lname, String dept, String password, int isSuccess, String message) {
        this.moodleId = moodleId;
        this.fname = fname;
        this.lname = lname;
        this.dept = dept;
        this.password = password;
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public String getMoodleId() {
        return moodleId;
    }

    public void setMoodleId(String moodleId) {
        this.moodleId = moodleId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(int isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}