package com.example.e_hrsystem.adminFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_hrsystem.R;
import com.example.e_hrsystem.model.TimeLog;
import com.example.e_hrsystem.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileAdminFragment extends Fragment {

    TextView tvNameAdmin, tvEmailAdmin, tvWorkingIdAdmin;
    Button btnCheckinAdmin, btnCheckoutAdmin;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReferenceIn,databaseReferenceOut;
    DatabaseReference dbRefUser, dbRefGetData;
    TimeLog timelogAdmin;
    User userAdmin;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_profile, null);

        timelogAdmin = new TimeLog();

        btnCheckinAdmin = view.findViewById(R.id.btnCheckInAdmin);
        btnCheckoutAdmin = view.findViewById(R.id.btnCheckOutAdmin);

        tvNameAdmin = view.findViewById(R.id.tvUsernameAdminProfile);
        tvEmailAdmin = view.findViewById(R.id.tvEmailAdminProfile);
        tvWorkingIdAdmin = view.findViewById(R.id.tvWorkingIdAdminProfile);
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        userAdmin = new User();
//        databaseReferenceIn = FirebaseDatabase.getInstance().getReference().child("Check In Data").child(auth.getCurrentUser().getUid());
        databaseReferenceIn = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).child("checkIn");
        databaseReferenceOut = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).child("checkOut");
//        databaseReferenceOut = FirebaseDatabase.getInstance().getReference().child("Check out Data").child(auth.getCurrentUser().getUid());

//        databaseReferenceOut = FirebaseDatabase.getInstance().getReference().child("Check out Data").child(auth.getCurrentUser().getUid());

        dbRefUser = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());



        btnCheckinAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCheckinDataAdmin();
            }
        });

        btnCheckoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCheckoutDataAdmin();
            }
        });


        getData();
        return view;
    }

    private void sendCheckinDataAdmin() {
        final String currentDate = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault()).format(new Date());
        final String currentTime = new SimpleDateFormat("HH:mm:ss a", Locale.getDefault()).format(new Date());
        final String id = auth.getCurrentUser().getUid();

        databaseReferenceIn.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(id)){
                    Toast.makeText(getContext(), "You have already checked in!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Query query = FirebaseDatabase.getInstance().getReference("users").child(auth.getCurrentUser().getUid())
                            .child("checkIn").orderByChild("date").equalTo(currentDate);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                Toast.makeText(getContext(), "You have already checked In today!", Toast.LENGTH_SHORT).show();
                            }else{

                                TimeLog checkIn = new TimeLog(currentDate,currentTime);
                                DatabaseReference dbRefCurrentUserId = databaseReferenceIn.child(currentDate);
                                dbRefCurrentUserId.setValue(checkIn);
                                Toast.makeText(getContext(), "You have checked In at : " + currentTime, Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
//                            TimeLog checkin = new TimeLog(currentDate,currentTime,null);
//                    DatabaseReference dbRefCurrentUserId = databaseReferenceIn.child(currentDate);
//                    dbRefCurrentUserId.setValue(checkin);
//                    Toast.makeText(getContext(), "You checked in!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendCheckoutDataAdmin() {
        final String currentDate = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault()).format(new Date());
        final String currentTime = new SimpleDateFormat("HH:mm:ss a", Locale.getDefault()).format(new Date());
        final String id = auth.getCurrentUser().getUid();

        databaseReferenceOut.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(id)){
                    Toast.makeText(getContext(), "You have already checked Out today!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Query query = FirebaseDatabase.getInstance().getReference("users").child(auth.getCurrentUser().getUid())
                            .child("checkOut").orderByChild("date").equalTo(currentDate);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                Toast.makeText(getContext(), "You have already checked Out today!", Toast.LENGTH_SHORT).show();
                            }else{

                                TimeLog checkOut = new TimeLog(currentDate,currentTime);
                                DatabaseReference dbRefCurrentUserId = databaseReferenceOut.child(currentDate);
                                dbRefCurrentUserId.setValue(checkOut);
                                Toast.makeText(getContext(), "You have checked Out at : " + currentTime, Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
//                            TimeLog checkin = new TimeLog(currentDate,currentTime,null);
//                    DatabaseReference dbRefCurrentUserId = databaseReferenceIn.child(currentDate);
//                    dbRefCurrentUserId.setValue(checkin);
//                    Toast.makeText(getContext(), "You checked in!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getData(){
        dbRefUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userAdmin = snapshot.getValue(User.class);

                tvNameAdmin.setText("Employee's Username : " + userAdmin.getUsername());
                tvEmailAdmin.setText("Employee's Email : " +userAdmin.getEmail());
                tvWorkingIdAdmin.setText("Employee's Working ID : " +userAdmin.getWorkingID());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
