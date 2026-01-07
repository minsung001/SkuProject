package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class Menuactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuactivity);

        // 1. XML 레이아웃의 ID와 연결
        ImageView ivProfile = findViewById(R.id.ivHeaderIcon);      // 우측 상단 사람 아이콘
        LinearLayout btnEnvironment = findViewById(R.id.btn_environment);
        LinearLayout btnEquipment = findViewById(R.id.btn_camera);
        LinearLayout btnSchedule = findViewById(R.id.btn_schedule);
        LinearLayout btnGraph = findViewById(R.id.btn_graph);
        LinearLayout btnReport = findViewById(R.id.btn_report);
        LinearLayout btnPolicy = findViewById(R.id.btn_policy);

        // 2. 각 버튼 클릭 시 화면 전환 (이미지 속 파일명과 100% 일치시킴)

        // 프로필 클릭 -> mypage 클래스로 이동
        ivProfile.setOnClickListener(v -> {
            startActivity(new Intent(Menuactivity.this, mypage.class));
        });

        // 환경 클릭 -> enviroment 클래스로 이동
        btnEnvironment.setOnClickListener(v -> {
            startActivity(new Intent(Menuactivity.this, enviroment.class));
        });

        // 기기 제어 클릭 -> equipmentcontrol 클래스로 이동
        btnEquipment.setOnClickListener(v -> {
            startActivity(new Intent(Menuactivity.this, camera.class));
        });

        // 일정 클릭 -> Schedule 클래스로 이동 (대문자 S 확인됨)
        btnSchedule.setOnClickListener(v -> {
            startActivity(new Intent(Menuactivity.this, Schedule.class));
        });

        // 그래프 클릭 -> grape 클래스로 이동
        btnGraph.setOnClickListener(v -> {
            startActivity(new Intent(Menuactivity.this, grape.class));
        });

        // 리포트 클릭 -> report 클래스로 이동
        btnReport.setOnClickListener(v -> {
            startActivity(new Intent(Menuactivity.this, report.class));
        });

        // 정책 클릭 -> policy 클래스로 이동
        btnPolicy.setOnClickListener(v -> {
            startActivity(new Intent(Menuactivity.this, policy.class));
        });
    }
}