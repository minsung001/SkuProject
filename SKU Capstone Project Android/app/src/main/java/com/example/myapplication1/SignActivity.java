package com.example.myapplication1;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class SignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge 등 불필요한 코드는 지우고 깔끔하게 유지
        setContentView(R.layout.activity_sign); // 회원가입 정보 입력 xml 연결

        // --- UI 요소 찾기 ---
        ImageView btnBack = findViewById(R.id.btn_back);

        EditText etVerifyCode = findViewById(R.id.et_verify_code);
        AppCompatButton btnVerifyRequest = findViewById(R.id.btn_verify_request);
        AppCompatButton btnVerifyCheck = findViewById(R.id.btn_verify_check);
        AppCompatButton btnSignupFinish = findViewById(R.id.btn_signup_finish);

        // 1. 뒤로가기 버튼 기능
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 화면 닫기
            }
        });

        // 2. 인증요청 버튼 클릭 시 -> 숨겨진 입력칸 보여주기
        btnVerifyRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignActivity.this, "인증번호가 발송되었습니다.", Toast.LENGTH_SHORT).show();

                // 숨겨져 있던 인증번호 입력창과 확인 버튼을 보이게 설정
                etVerifyCode.setVisibility(View.VISIBLE);
                btnVerifyCheck.setVisibility(View.VISIBLE);
            }
        });

        // 3. 인증번호 확인 버튼 클릭 시
        btnVerifyCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // (테스트용 로직) 입력된 값이 있으면 인증 성공 처리
                if (etVerifyCode.getText().toString().length() > 0) {
                    Toast.makeText(SignActivity.this, "인증되었습니다!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignActivity.this, "인증번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 4. 회원가입 완료 버튼
        btnSignupFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignActivity.this, "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show();
                finish(); // 가입 끝나면 화면 닫기
            }
        });
    }
}