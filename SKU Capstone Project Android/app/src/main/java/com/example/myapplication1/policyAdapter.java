package com.example.myapplication1;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_policy_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AuthModels.PolicyResponse policy = policyList.get(position);
        holder.tvTitle.setText(policy.title);
        holder.tvSummary.setText(policy.summary);
        holder.tvDept.setText(policy.department);
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