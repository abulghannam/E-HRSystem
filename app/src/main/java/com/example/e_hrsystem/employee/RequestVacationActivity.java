package com.example.e_hrsystem.employee;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_hrsystem.R;
import com.example.e_hrsystem.model.RequestVacationData;
import com.example.e_hrsystem.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RequestVacationActivity extends AppCompatActivity {

    Spinner typeSpinner;
    EditText etStartDate, etEndDate, etMoreinfo;
    Button btnSendReqVac;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    DatabaseReference dbRefUser;
    FirebaseAuth auth;
    TextView tvUsername;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_vacation);

        user = new User();
//        databaseReference = db.getReference("Request Vacations Data");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Request vacations data");
        auth = FirebaseAuth.getInstance();
        dbRefUser = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());

        typeSpinner = findViewById(R.id.spinnerType);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        etMoreinfo = findViewById(R.id.etMoreInfoVac);
        btnSendReqVac = findViewById(R.id.btnSendRequestVac);
        tvUsername = findViewById(R.id.tv_username);
        btnSendReqVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }

        });

        dbRefUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);


                tvUsername.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sendData() {
        final String type = typeSpinner.getSelectedItem().toString();
        final String StartDateVac = etStartDate.getText().toString();
        final String EndDateVac = etEndDate.getText().toString();
        final String MoreInfo = etMoreinfo.getText().toString();
        final String id = auth.getCurrentUser().getUid();
        final String username = null;



        if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(StartDateVac) && !TextUtils.isEmpty(EndDateVac)) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    if (snapshot.hasChild(id)) {
                        Toast.makeText(RequestVacationActivity.this, "There is already a pending request",
                                Toast.LENGTH_SHORT).show();

                    } else {

                        DatabaseReference dbRefCurrentUserId = databaseReference.child(id);
                        RequestVacationData vacData = new RequestVacationData(type, StartDateVac, EndDateVac,
                                MoreInfo, id,tvUsername.getText().toString());
                        dbRefCurrentUserId.setValue(vacData);
                        Toast.makeText(RequestVacationActivity.this, "The Request has been sent",
                                Toast.LENGTH_SHORT).show();
                        etStartDate.getText().clear();
                        etEndDate.getText().clear();
                        etMoreinfo.getText().clear();
                        typeSpinner.setSelection(0);

//                databaseReference.push().child("Data").setValue(VacData);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        } else {
            Toast.makeText(RequestVacationActivity.this, "Please Fill all the data", Toast.LENGTH_SHORT).show();
        }

    }
}
