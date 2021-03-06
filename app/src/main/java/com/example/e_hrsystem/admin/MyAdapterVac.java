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
import com.example.e_hrsystem.model.RequestVacationData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapterVac extends FirebaseRecyclerAdapter<RequestVacationData, MyAdapterVac.myviewholder> {


    FirebaseAuth auth;
    DatabaseReference dbRef;
    //RequestVacationData approveReq = new RequestVacationData();

    public MyAdapterVac(@NonNull FirebaseRecyclerOptions<RequestVacationData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int i, @NonNull final RequestVacationData requestVacationData) {

        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("Request vacations data");

        FirebaseUser userVerify = FirebaseAuth.getInstance().getCurrentUser();

        if (userVerify != null) {
            boolean isSameUser = userVerify.getUid().equals(requestVacationData.getId());
            holder.btn_Approve.setEnabled(!isSameUser);
            holder.btn_Decline.setEnabled(!isSameUser);
        }
        boolean isPending = requestVacationData.isApproved().equalsIgnoreCase("In Queue");


        holder.btn_Approve.setVisibility(isPending ? View.VISIBLE : View.GONE);
        holder.btn_Decline.setVisibility(isPending ? View.VISIBLE : View.GONE);



        holder.tvType.setText(requestVacationData.getType());
        holder.tvStart.setText(requestVacationData.getStartDateVac());
        holder.tvEnd.setText(requestVacationData.getEndDateVac());
        holder.tvInfo.setText(requestVacationData.getMoreInfo());
        holder.tvId.setText(requestVacationData.getId());
        holder.tvName.setText(requestVacationData.getUsername());
        holder.tvStatus.setText(requestVacationData.isApproved());
        holder.btn_Approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                dialog.setTitle("Are you sure you want to approve this request?");
                dialog.setMessage("If you approve this , it will give the employee the ability to " +
                        "take his vacation on the requested time interval.");
                        dialog.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        approveVacRequest(requestVacationData,"Approved");

                                        Toast.makeText(view.getContext(), "You have approved this request",
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



        holder.btn_Decline.setOnClickListener(new View.OnClickListener() {
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
                                declineVacRequest(requestVacationData,"Declined");
                                Toast.makeText(view.getContext(), "You have declined this request",
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

    private void approveVacRequest(@NonNull RequestVacationData requestVacationData,String isApproved) {
        dbRef.child(requestVacationData.getId()).child("approved").setValue(isApproved);
//        System.currentTimeMillis();
//        new Date(120365)
    }


    private void declineVacRequest(RequestVacationData requestVacationData, String isApproved) {
        dbRef.child(requestVacationData.getId()).child("approved").setValue(isApproved);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_vacation, parent, false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView tvType, tvStart, tvEnd, tvInfo, tvId, tvName,tvStatus;
        Button btn_Approve, btn_Decline;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            tvType = itemView.findViewById(R.id.tv_type);
            tvStart= itemView.findViewById(R.id.tv_start);
            tvEnd =  itemView.findViewById(R.id.tv_end);
            tvInfo = itemView.findViewById(R.id.tv_moreInfo);
            tvId =   itemView.findViewById(R.id.tv_ID);
            tvName = itemView.findViewById(R.id.tv_name);
            tvStatus = itemView.findViewById(R.id.tv_statusVac);
            btn_Approve = itemView.findViewById(R.id.btnApproveVac);
            btn_Decline = itemView.findViewById(R.id.btnDeclineVac);

        }
    }

}
