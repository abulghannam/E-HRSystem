package com.example.e_hrsystem.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_hrsystem.R;
import com.example.e_hrsystem.model.RequestLeaveData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapterLev extends FirebaseRecyclerAdapter<RequestLeaveData, MyAdapterLev.myviewholder> {

    FirebaseAuth auth;
    DatabaseReference dbRef;

    public MyAdapterLev(@NonNull FirebaseRecyclerOptions<RequestLeaveData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyAdapterLev.myviewholder holder, int i, @NonNull final RequestLeaveData requestLeaveData) {

        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("Request leaves data");

        holder.tvTime.setText(requestLeaveData.getTime());
        holder.tvInfo.setText(requestLeaveData.getMoreInfo());
        holder.tvId.setText(requestLeaveData.getId());
        holder.tvName.setText(requestLeaveData.getUsername());
        holder.btn_Approve_Lev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                dialog.setTitle("Are you sure you want to approve this request?");
                dialog.setMessage("If you approve this , it will give the employee the ability to " +
                        "take his leave on the requested time interval.");
                dialog.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                approveLevRequest(requestLeaveData,"Approved");
                                Toast.makeText(view.getContext(), "You have been approved this request",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                dialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = dialog.create();
                alertDialog.show();

            }
        });

        holder.btn_Decline_Lev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                dialog.setTitle("Are you sure you want to decline this request?");
                dialog.setMessage("If you decline this , it will not give the employee the ability to " +
                        "take his vacation on the requested time interval.");
                dialog.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                declineLevRequest(requestLeaveData,"Declined");
                                Toast.makeText(view.getContext(), "You have been declined this request",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                dialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });
    }

    private void approveLevRequest(@NonNull RequestLeaveData requestLeaveData, String isApproved) {
        dbRef.child(requestLeaveData.getId()).child("approved").setValue(isApproved);
//        System.currentTimeMillis();
//        new Date(120365)
    }


    private void declineLevRequest(RequestLeaveData requestLeaveData, String isApproved) {
        dbRef.child(requestLeaveData.getId()).child("approved").setValue(isApproved);
    }

    @NonNull
    @Override
    public MyAdapterLev.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_leave,parent,false);
        return new MyAdapterLev.myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView tvTime,tvInfo,tvId,tvName;
        Button btn_Approve_Lev,btn_Decline_Lev;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            tvTime = itemView.findViewById(R.id.tv_timeLev);
            tvInfo = itemView.findViewById(R.id.tv_moreInfoLev);
            tvId =   itemView.findViewById(R.id.tv_IDLev);
            tvName = itemView.findViewById(R.id.tv_nameLev);
            btn_Approve_Lev = itemView.findViewById(R.id.btnApproveLev);
            btn_Decline_Lev = itemView.findViewById(R.id.btnDeclineLev);

        }
    }

}
