package com.example.myapplication1;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/user/signup")
    Call<UserResponse> signup(@Body UserRequest request);

    @POST("/user/login")
    Call<UserResponse> login(@Body UserRequest request);
}
