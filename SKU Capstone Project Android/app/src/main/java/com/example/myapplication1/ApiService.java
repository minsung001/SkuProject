package com.example.myapplication1;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    // 1. 이메일 인증번호 요청 (AuthModels 안에 있는 VerifyRequest 사용)
    @POST("/auth/request-verify")
    Call<AuthModels.UserResponse> requestVerify(@Body AuthModels.VerifyRequest body);

    // 2. 인증코드 확인 (AuthModels 안에 있는 CodeCheckRequest 사용)
    @POST("/auth/verify-code")
    Call<AuthModels.UserResponse> verifyCode(@Body AuthModels.CodeCheckRequest body);

    // 3. 회원가입 (AuthModels 안에 있는 SignupRequest 사용)
    @POST("/auth/signup")
    Call<AuthModels.UserResponse> signup(@Body AuthModels.SignupRequest body);

    // 4. 로그인 (AuthModels 안에 있는 LoginRequest 사용)
    @POST("/auth/login")
    Call<AuthModels.UserResponse> login(@Body AuthModels.LoginRequest body);

    // 5. 토큰 갱신 (Map 형태 그대로 유지하거나, 필요 시 전용 모델 사용 가능)
    @POST("/auth/refresh")
    Call<AuthModels.UserResponse> refresh(@Body Map<String, String> refreshToken);
}