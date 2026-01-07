package com.example.myapplication1;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull; // 추가
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

    private static final String TAG = "PolicyActivity_Debug";
    private RecyclerView recyclerView;
    private policyAdapter adapter;
    private List<AuthModels.PolicyResponse> policyList = new ArrayList<>();

    // 페이징을 위한 변수 (나중에 사용)
    private int currentPage = 1;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_policy);

        recyclerView = findViewById(R.id.recyclerView);
        if (recyclerView == null) {
            Log.e(TAG, "에러: XML에서 recyclerView를 찾을 수 없습니다.");
        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            // 💡 [스크롤 리스너 추가] 리스트의 끝을 감지합니다.
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    // dy > 0 은 아래로 스크롤 중임을 의미합니다.
                    if (dy > 0) {
                        int visibleItemCount = layoutManager.getChildCount();
                        int totalItemCount = layoutManager.getItemCount();
                        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                        // 바닥에 거의 다다랐는지 확인 (마지막 아이템이 보일 때)
                        if (!isLoading) {
                            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                    && firstVisibleItemPosition >= 0) {

                                Log.d(TAG, "리스트의 끝에 도달했습니다!");
                                // 여기서 loadMoreData() 같은 함수를 실행하여 데이터를 더 가져올 수 있습니다.
                                // Toast.makeText(policy.this, "마지막 페이지입니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fetchPolicies();
    }

    private void fetchPolicies() {
        isLoading = true;
        String BASE_URL = "http://10.0.2.2:3000/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        apiService.getPolicies().enqueue(new Callback<List<AuthModels.PolicyResponse>>() {
            @Override
            public void onResponse(Call<List<AuthModels.PolicyResponse>> call, Response<List<AuthModels.PolicyResponse>> response) {
                isLoading = false;
                if (response.isSuccessful() && response.body() != null) {
                    policyList = response.body();

                    if (policyList.isEmpty()) {
                        Toast.makeText(policy.this, "등록된 정책이 없습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter = new policyAdapter(policyList);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AuthModels.PolicyResponse>> call, Throwable t) {
                isLoading = false;
                Log.e(TAG, "네트워크 통신 실패: " + t.getMessage());
            }
        });
    }
}