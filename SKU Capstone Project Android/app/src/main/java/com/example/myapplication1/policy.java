package com.example.myapplication1;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class policy extends AppCompatActivity {

    private static final String TAG = "PolicyActivity_Debug"; // 로그 태그 정의
    private RecyclerView recyclerView;
    private policyAdapter adapter;
    private List<AuthModels.PolicyResponse> policyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_policy);

        // 1. 뷰 초기화
        recyclerView = findViewById(R.id.recyclerView);
        if (recyclerView == null) {
            Log.e(TAG, "에러: XML에서 recyclerView를 찾을 수 없습니다.");
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        // 시스템바 인셋 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 2. 서버 데이터 호출
        fetchPolicies();
    }

    private void fetchPolicies() {
        // 에뮬레이터 접속 주소 (안드로이드 공식 Localhost 우회 IP)
        String BASE_URL = "http://10.0.2.2:3000/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Log.d(TAG, "서버에 데이터 요청을 시작합니다. URL: " + BASE_URL + "api/policies");

        apiService.getPolicies().enqueue(new Callback<List<AuthModels.PolicyResponse>>() {
            @Override
            public void onResponse(Call<List<AuthModels.PolicyResponse>> call, Response<List<AuthModels.PolicyResponse>> response) {
                // HTTP 응답 코드 확인 (200 OK 등)
                Log.d(TAG, "응답 코드: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    policyList = response.body();

                    if (policyList.isEmpty()) {
                        Log.w(TAG, "성공했으나 서버에서 보낸 데이터가 비어있습니다(size 0).");
                        Toast.makeText(policy.this, "등록된 정책이 없습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "데이터 로드 성공! 개수: " + policyList.size());
                        // 3. 어댑터 연결
                        adapter = new policyAdapter(policyList);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Log.e(TAG, "서버 응답 에러 발생: " + response.message());
                    Toast.makeText(policy.this, "서버 응답 에러: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AuthModels.PolicyResponse>> call, Throwable t) {
                // 네트워크 연결 자체가 실패한 경우 (주소 오타, 서버 꺼짐, 권한 부족 등)
                Log.e(TAG, "네트워크 통신 실패! 원인: " + t.getMessage());
                Toast.makeText(policy.this, "서버 연결에 실패했습니다.", Toast.LENGTH_LONG).show();
            }
        });
    }
}