package com.example.e_hrsystem.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_hrsystem.R;
import com.example.e_hrsystem.employee.RequestVacationActivity;
import com.example.e_hrsystem.model.RequestVacationData;
import com.example.e_hrsystem.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ViewVacRequestActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference dbRef;
    RequestVacationData vacationModel;
    TextView tv,tvTypeVac,tvStartDate,tvEndDate,tvVacStatus;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vac_request);
        init();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .4));

        checkVacation();

    }
    private void init(){
        tv = findViewById(R.id.tv);
        linearLayout = findViewById(R.id.test);
        vacationModel = new RequestVacationData();
        dbRef = FirebaseDatabase.getInstance().getReference("Request vacations data");
        auth = FirebaseAuth.getInstance();

        tvTypeVac = findViewById(R.id.tv_typeVac);
        tvStartDate = findViewById(R.id.tv_startVac);
        tvEndDate = findViewById(R.id.tv_endVac);
        tvVacStatus = findViewById(R.id.tv_statusVac);

    }

    private void checkVacation(){
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
                            vacationModel = snapshot.getValue(RequestVacationData.class);
                            if (currentUserUID.equals(id)){
                                //Toast.makeText(ViewVacRequestActivity.this, "user exist", Toast.LENGTH_SHORT).show();
                                tv.setVisibility(View.GONE);
                                linearLayout.setVisibility(View.VISIBLE);
//                                Toast.makeText(ViewVacRequestActivity.this,
//                                        vacationModel.getType()+ "\n"
//                                        +vacationModel.getStartDateVac()+ "\n"
//                                        +vacationModel.getEndDateVac()+ "\n"
//                                        +vacationModel.isApproved()+ "\n"
//                                        , Toast.LENGTH_LONG).show();
                                tvTypeVac.setText(vacationModel.getType());
                                tvStartDate.setText(vacationModel.getStartDateVac());
                                tvEndDate.setText(vacationModel.getEndDateVac());
                                tvVacStatus.setText(vacationModel.isApproved());

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