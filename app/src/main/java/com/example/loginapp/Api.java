package com.example.loginapp;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("register.php")
    Call<User> register(@Field("moodleId") String moodleId,
                         @Field("fname") String fname,
                        @Field("lname") String lname,
                         @Field("dept") String dept,
                         @Field("password") String password);

    @FormUrlEncoded
    @POST("login.php/")
    Call<User> login(@Field("moodleId") String moodleId,
                     @Field("password") String password);

    @Headers("Content-Type: text/html")
    @GET("departmentitems.php")
    Call<JsonObject> getDepartmentItems();  //return 'response' in form of JSON directly

    @FormUrlEncoded
    @POST("scanCheck.php/")
    Call<JsonObject> scanDetails(@Field("SessionID") String sessionId,
                                  @Field("qrcode") String qrcode,
                                  @Field("qrdate") String currentDate,
                                  @Field("timestamp") String currentTime,
                                  @Field("DepartQID") String departId,
                                  @Field("latitude") Double latitude,
                                  @Field("longitude") Double longitude,
                                  @Field("SubjectQID") String subjectId,
                                  @Field("MoodleId") String moodleId);
}
