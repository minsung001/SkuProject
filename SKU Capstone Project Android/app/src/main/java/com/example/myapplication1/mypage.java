package com.example.myapplication1;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class mypage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        ImageView btnBack = findViewById(R.id.btn_back);
        EditText etBabyBirth = findViewById(R.id.et_edit_baby_birth);
        ImageButton btnAddGuardian = findViewById(R.id.btn_add_guardian);
        LinearLayout itemEmpty = findViewById(R.id.item_empty_guardian); // 안내 문구

        btnBack.setOnClickListener(v -> finish());

        // 💡 아기 생일 달력 (기존 로직 유지)
        etBabyBirth.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, y, m, d) -> {
                etBabyBirth.setText(y + "-" + (m + 1) + "-" + d);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        // 💡 보호자 초대 시 안내 문구 숨기기 예시
        btnAddGuardian.setOnClickListener(v -> {
            EditText et = new EditText(this);
            et.setHint("초대할 ID 입력");
            new AlertDialog.Builder(this)
                    .setTitle("보호자 초대")
                    .setView(et)
                    .setPositiveButton("초대", (dialog, which) -> {
                        // 보호자가 추가되면 안내 문구를 없앱니다.
                        itemEmpty.setVisibility(View.GONE);
                        Toast.makeText(this, "초대를 보냈습니다.", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("취소", null).show();
        });
    }
}