package com.example.e_hrsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.e_hrsystem.model.RequestLeaveData;
import com.example.e_hrsystem.model.RequestVacationData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewLevRequestActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference dbRef;
    RequestLeaveData vacationModel;
    TextView tvNoData,tvTimeLev,tvStatusLev;
    LinearLayout linearLayoutLev;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lev_request);

        init();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .4));

        checkLeave();
    }

    private void init(){

        tvNoData = findViewById(R.id.tvLev);
        linearLayoutLev = findViewById(R.id.testLev);
        vacationModel = new RequestLeaveData();
        dbRef = FirebaseDatabase.getInstance().getReference("Request leaves data");
        auth = FirebaseAuth.getInstance();

        tvTimeLev = findViewById(R.id.tv_timeLevEmp);
        tvStatusLev = findViewById(R.id.tv_LevStatusEmp);

    }

    private void checkLeave(){
        final String currentUserUID = auth.getCurrentUser().getUid();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    final String id = ds.getKey();
                    final DatabaseReference databaseReference = dbRef.child(id);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            vacationModel = snapshot.getValue(RequestLeaveData.class);
                            if (currentUserUID.equals(id)){
                                //Toast.makeText(ViewVacRequestActivity.this, "user exist", Toast.LENGTH_SHORT).show();
                                tvNoData.setVisibility(View.GONE);
                                linearLayoutLev.setVisibility(View.VISIBLE);
//                                Toast.makeText(ViewVacRequestActivity.this,
//                                        vacationModel.getType()+ "\n"
//                                        +vacationModel.getStartDateVac()+ "\n"
//                                        +vacationModel.getEndDateVac()+ "\n"
//                                        +vacationModel.isApproved()+ "\n"
//                                        , Toast.LENGTH_LONG).show();
                                tvTimeLev.setText(vacationModel.getTime());
                                tvStatusLev.setText(vacationModel.isApproved());

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}