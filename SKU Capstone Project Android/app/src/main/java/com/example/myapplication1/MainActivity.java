package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText etId, etPassword;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(R.id.et_id);
        etPassword = findViewById(R.id.et_password);

        Button btnLogin = findViewById(R.id.btn_login);
        View btnSignup = findViewById(R.id.btn_signup);

        // API 설정은 남겨두어도 무방합니다. (나중에 다시 쓸 수 있으니까요)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        // 로그인 버튼 클릭 리스너
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 서버 통신 없이 바로 화면 전환
                Toast.makeText(MainActivity.this, "로그인 정보 확인 생략 (개발 모드)", Toast.LENGTH_SHORT).show();

                // Intent를 사용하여 Menuactivity로 바로 이동
                Intent intent = new Intent(MainActivity.this, Menuactivity.class);
                startActivity(intent);

                // 로그인 화면을 스택에서 제거 (뒤로가기 눌러도 로그인 화면 안 나오게)
                finish();
            }
        });

        // 회원가입 버튼 클릭 리스너
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TermsActivity.class);
                startActivity(intent);
            }
        });
    }
}