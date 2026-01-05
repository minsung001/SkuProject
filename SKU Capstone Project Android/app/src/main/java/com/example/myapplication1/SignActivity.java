package com.example.myapplication1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignActivity extends AppCompatActivity {

    private static final String TAG = "SignActivity_Debug"; // 로그 필터용 태그
    private ApiService apiService;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        // 0. Retrofit 서비스 초기화
        apiService = RetrofitClient.getApiService();

        // 1. 뷰 바인딩
        ImageView btnBack = findViewById(R.id.btn_back);
        EditText etName = findViewById(R.id.et_sign_name);
        EditText etEmail = findViewById(R.id.et_sign_email);
        EditText etVerifyCode = findViewById(R.id.et_verify_code);
        EditText etUsername = findViewById(R.id.et_sign_id);
        EditText etPassword = findViewById(R.id.et_sign_pw);
        EditText etPasswordCheck = findViewById(R.id.et_sign_pw_check);

        AppCompatButton btnVerifyRequest = findViewById(R.id.btn_verify_request);
        AppCompatButton btnVerifyCheck = findViewById(R.id.btn_verify_check);
        AppCompatButton btnSignupFinish = findViewById(R.id.btn_signup_finish);

        btnBack.setOnClickListener(v -> finish());

        // 2. [인증요청] 버튼 로직
        btnVerifyRequest.setOnClickListener(v -> {
            userEmail = etEmail.getText().toString().trim();
            if (userEmail.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            AuthModels.VerifyRequest request = new AuthModels.VerifyRequest(userEmail);
            apiService.requestVerify(request).enqueue(new Callback<AuthModels.UserResponse>() {
                @Override
                public void onResponse(Call<AuthModels.UserResponse> call, Response<AuthModels.UserResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().ok) {
                        Toast.makeText(SignActivity.this, "인증번호가 발송되었습니다.", Toast.LENGTH_SHORT).show();
                        etVerifyCode.setVisibility(View.VISIBLE);
                        btnVerifyCheck.setVisibility(View.VISIBLE);
                    } else {
                        // 구체적인 에러 로그 출력
                        handleErrorResponse(response);
                        Toast.makeText(SignActivity.this, "인증 요청 실패 (Logcat 확인)", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AuthModels.UserResponse> call, Throwable t) {
                    Log.e(TAG, "인증요청 네트워크 실패: " + t.getMessage());
                    Toast.makeText(SignActivity.this, "네트워크 연결 확인이 필요합니다.", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // 3. [인증확인] 버튼 로직
        btnVerifyCheck.setOnClickListener(v -> {
            String code = etVerifyCode.getText().toString().trim();
            if (code.isEmpty()) {
                Toast.makeText(this, "인증번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            AuthModels.CodeCheckRequest request = new AuthModels.CodeCheckRequest(userEmail, code);
            apiService.verifyCode(request).enqueue(new Callback<AuthModels.UserResponse>() {
                @Override
                public void onResponse(Call<AuthModels.UserResponse> call, Response<AuthModels.UserResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().ok) {
                        Toast.makeText(SignActivity.this, "이메일 인증 성공!", Toast.LENGTH_SHORT).show();
                        etEmail.setEnabled(false);
                        btnVerifyRequest.setEnabled(false);
                    } else {
                        handleErrorResponse(response);
                        Toast.makeText(SignActivity.this, "인증번호가 틀리거나 만료되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AuthModels.UserResponse> call, Throwable t) {
                    Log.e(TAG, "인증확인 네트워크 실패: " + t.getMessage());
                }
            });
        });

        // 4. [회원가입 완료] 버튼 로직
        btnSignupFinish.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String passwordCheck = etPasswordCheck.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || !password.equals(passwordCheck)) {
                Toast.makeText(this, "입력 정보를 확인해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            AuthModels.SignupRequest request = new AuthModels.SignupRequest(userEmail, username, password, true);
            apiService.signup(request).enqueue(new Callback<AuthModels.UserResponse>() {
                @Override
                public void onResponse(Call<AuthModels.UserResponse> call, Response<AuthModels.UserResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().ok) {
                        Toast.makeText(SignActivity.this, "회원가입 완료!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        handleErrorResponse(response);
                        Toast.makeText(SignActivity.this, "가입 실패 (중복 혹은 인증 미완료)", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AuthModels.UserResponse> call, Throwable t) {
                    Log.e(TAG, "회원가입 네트워크 실패: " + t.getMessage());
                }
            });
        });
    }

    /**
     * 서버 에러 응답을 분석하여 Logcat에 출력하는 헬퍼 메소드
     */
    private void handleErrorResponse(Response<?> response) {
        Log.e(TAG, "에러 코드 (Status Code): " + response.code());
        try {
            if (response.errorBody() != null) {
                Log.e(TAG, "에러 내용 (Error Body): " + response.errorBody().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}