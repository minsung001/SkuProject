package com.example.myapplication1;

import com.google.gson.annotations.SerializedName;

public class AuthModels {

    // 1. 이메일 인증번호 요청용 (email만 보냄)
    public static class VerifyRequest {
        String email;
        public VerifyRequest(String email) { this.email = email; }
    }

    // 2. 인증번호 코드 확인용 (email과 code 보냄)
    public static class CodeCheckRequest {
        String email;
        String code;
        public CodeCheckRequest(String email, String code) {
            this.email = email;
            this.code = code;
        }
    }

    // 3. 회원가입용
    public static class SignupRequest {
        String email;
        String username;
        String password;
        boolean consent;

        public SignupRequest(String email, String username, String password, boolean consent) {
            this.email = email;
            this.username = username;
            this.password = password;
            this.consent = consent;
        }
    }

    // 4. 로그인용
    public static class LoginRequest {
        String username;
        String password;
        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    // 5. 서버로부터 받을 응답 (공통)
    public static class UserResponse {
        @SerializedName("ok") public boolean ok;
        @SerializedName("message") public String message;
        @SerializedName("accessToken") public String accessToken;
        @SerializedName("refreshToken") public String refreshToken;
        @SerializedName("userId") public String userId;
    }

    // -----------------------------------------------------------
    // 6. [새로 추가] 복지 정책 데이터 응답용 모델
    // -----------------------------------------------------------
    public static class PolicyResponse implements java.io.Serializable { // 💡 Serializable 추가
        @SerializedName("서비스명")
        public String title;
        @SerializedName("서비스요약")
        public String summary;
        @SerializedName("소관부처명")
        public String department;
        @SerializedName("소관조직명")
        public String subDepartment;
        @SerializedName("서비스URL")
        public String url;
    }
}