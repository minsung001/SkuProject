package com.example.myapplication1;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Schedule extends AppCompatActivity {

    private ImageView btnBack;
    private CalendarView calendarView;
    private TextView tvVaccineRule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        // 1. 뷰 바인딩
        btnBack = findViewById(R.id.btnBack);
        calendarView = findViewById(R.id.calendarView);
        tvVaccineRule = findViewById(R.id.tvVaccineRule);

        // 2. 뒤로가기 버튼: finish()를 사용하여 Menuactivity로 복귀
        btnBack.setOnClickListener(v -> {
            finish();
        });

        // 3. 날짜 선택 시 규칙 텍스트 출력
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = year + "년 " + (month + 1) + "월 " + dayOfMonth + "일";
            // 💡 추후 JSON 데이터를 이곳에 연결합니다.
            tvVaccineRule.setText("📅 " + selectedDate + " 기준:\n이 날짜의 접종 주의사항 및 백신 예외 규칙을 확인하세요.");
        });
    }
}