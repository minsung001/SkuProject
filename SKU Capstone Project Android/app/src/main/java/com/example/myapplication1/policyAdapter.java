package com.example.myapplication1;

import android.content.Intent; // 💡 1. Intent 임포트 추가
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class policyAdapter extends RecyclerView.Adapter<policyAdapter.ViewHolder> {

    private List<AuthModels.PolicyResponse> policyList;

    public policyAdapter(List<AuthModels.PolicyResponse> list) {
        this.policyList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 💡 layout 파일명이 activity_policy_adapter가 맞는지 다시 한번 확인하세요!
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_policy_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AuthModels.PolicyResponse policy = policyList.get(position);
        holder.tvTitle.setText(policy.title);
        holder.tvSummary.setText(policy.summary);
        holder.tvDept.setText(policy.department);

        // 💡 2. 클릭 리스너 추가: 항목을 클릭하면 상세 페이지(policyDetail)로 이동
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), policyDetail.class);
            // 💡 데이터를 'policy_data'라는 이름으로 담아서 보냅니다.
            intent.putExtra("policy_data", policy);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSummary, tvDept;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvPolicyTitle);
            tvSummary = itemView.findViewById(R.id.tvPolicySummary);
            tvDept = itemView.findViewById(R.id.tvDept);
        }
    }
}