package com.example.loginapp;

public class ScanDetails {
    private static String sessionId, qrcode, currentDate, currentTime;
    private static String departId, status, subjectId;
    private Double latitude, longitude;

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(String sessionId) {
        ScanDetails.sessionId = sessionId;
    }

    public static String getQrcode() {
        return qrcode;
    }

    public static void setQrcode(String qrcode) {
        ScanDetails.qrcode = qrcode;
    }

    public static String getCurrentDate() {
        return currentDate;
    }

    public static void setCurrentDate(String currentDate) {
        ScanDetails.currentDate = currentDate;
    }

    public static String getCurrentTime() {
        return currentTime;
    }

    public static void setCurrentTime(String currentTime) {
        ScanDetails.currentTime = currentTime;
    }

    public static String getDepartId() {
        return departId;
    }

    public static void setDepartId(String departId) {
        ScanDetails.departId = departId;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        ScanDetails.status = status;
    }

    public static String getSubjectId() {
        return subjectId;
    }

    public static void setSubjectId(String subjectId) {
        ScanDetails.subjectId = subjectId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
