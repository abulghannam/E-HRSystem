package com.example.e_hrsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e_hrsystem.model.RequestVacationData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapterVac extends FirebaseRecyclerAdapter<RequestVacationData, MyAdapterVac.myviewholder> {


    public MyAdapterVac(@NonNull FirebaseRecyclerOptions<RequestVacationData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int i, @NonNull RequestVacationData requestVacationData) {
        holder.tvType.setText(requestVacationData.getType());
        holder.tvStart.setText(requestVacationData.getStartDateVac());
        holder.tvEnd.setText(requestVacationData.getEndDateVac());
        holder.tvInfo.setText(requestVacationData.getMoreInfo());
        holder.tvId.setText(requestVacationData.getId());
        holder.tvName.setText(requestVacationData.getUsername());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_vacation,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView tvType,tvStart,tvEnd,tvInfo,tvId,tvName;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            tvType = itemView.findViewById(R.id.tv_type);
            tvStart= itemView.findViewById(R.id.tv_start);
            tvEnd =  itemView.findViewById(R.id.tv_end);
            tvInfo = itemView.findViewById(R.id.tv_moreInfo);
            tvId =   itemView.findViewById(R.id.tv_ID);
            tvName = itemView.findViewById(R.id.tv_name);

        }
    }

}
