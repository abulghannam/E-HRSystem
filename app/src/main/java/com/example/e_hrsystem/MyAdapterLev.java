package com.example.e_hrsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e_hrsystem.model.RequestLeaveData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapterLev extends FirebaseRecyclerAdapter<RequestLeaveData, MyAdapterLev.myviewholder> {

    public MyAdapterLev(@NonNull FirebaseRecyclerOptions<RequestLeaveData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyAdapterLev.myviewholder holder, int i, @NonNull RequestLeaveData requestLeaveData) {
        holder.tvTime.setText(requestLeaveData.getTime());
        holder.tvInfo.setText(requestLeaveData.getMoreInfo());
        holder.tvId.setText(requestLeaveData.getId());
        holder.tvName.setText(requestLeaveData.getUsername());
    }

    @NonNull
    @Override
    public MyAdapterLev.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_leave,parent,false);
        return new MyAdapterLev.myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView tvTime,tvInfo,tvId,tvName;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            tvTime = itemView.findViewById(R.id.tv_timeLev);
            tvInfo = itemView.findViewById(R.id.tv_moreInfoLev);
            tvId =   itemView.findViewById(R.id.tv_IDLev);
            tvName = itemView.findViewById(R.id.tv_nameLev);

        }
    }

}
