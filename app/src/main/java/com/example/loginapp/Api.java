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
    Call<JsonObject> getDepartmentItems();
}
