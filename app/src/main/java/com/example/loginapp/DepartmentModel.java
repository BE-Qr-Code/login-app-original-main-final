package com.example.loginapp;

import com.google.gson.annotations.SerializedName;

public class DepartmentModel {
    private String DepartID;

    private String DepartName;

    private String year;

//    private int isSuccess;

    public String getDepartID() {
        return DepartID;
    }

    public void setDepartID(String departID) {
        DepartID = departID;
    }

    public String getDepartName() {
        return DepartName;
    }

    public void setDepartName(String departName) {
        DepartName = departName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

//    public int getIsSuccess() {
//        return isSuccess;
//    }
//
//    public void setIsSuccess(int isSuccess) {
//        this.isSuccess = isSuccess;
//    }
}
