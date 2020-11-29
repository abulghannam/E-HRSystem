package com.example.e_hrsystem.employee;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.e_hrsystem.R;
import com.example.e_hrsystem.model.RequestLeaveData;
import com.example.e_hrsystem.model.RequestVacationData;
import com.example.e_hrsystem.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RequestLeaveActivity extends AppCompatActivity {

    TimePicker timePicker;
    EditText etmoreInfo;
    Button btnSendReqLev;
    TextView tvPickedTime,tvUsernameLev;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    DatabaseReference dbRefUser;
    FirebaseAuth auth;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_leave);

        user = new User();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Request leaves data");
        auth = FirebaseAuth.getInstance();
        dbRefUser = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());

        timePicker = findViewById(R.id.timePicker);
        tvPickedTime = findViewById(R.id.pickedTime);
        etmoreInfo = findViewById(R.id.etMoreInfoLev);
        tvUsernameLev = findViewById(R.id.tv_lev_username);
        btnSendReqLev = findViewById(R.id.btnSendRequestLev);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hour, int min) {
                String minAndSec = hour + ":" + min;

                tvPickedTime.setText(Html.fromHtml("Time Selected :"+minAndSec));
            }
        });

        btnSendReqLev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });

        dbRefUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);


                tvUsernameLev.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void sendData(){
        final String id = auth.getCurrentUser().getUid();
        final String time = tvPickedTime.getText().toString();
        final String MoreInfo = etmoreInfo.getText().toString();
        final String username = null;



        if (!TextUtils.isEmpty(time)) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    if (snapshot.hasChild(id)) {
                        Toast.makeText(RequestLeaveActivity.this, "There is already a pending request", Toast.LENGTH_SHORT).show();

                    } else {

                        DatabaseReference dbRefCurrentUserId = databaseReference.child(id);
                        RequestLeaveData levData = new RequestLeaveData(id,time,MoreInfo,tvUsernameLev.getText().toString());
                        dbRefCurrentUserId.setValue(levData);
                        Toast.makeText(RequestLeaveActivity.this, "The Request has been sent", Toast.LENGTH_SHORT).show();
                        tvPickedTime.setText("");
                        etmoreInfo.getText().clear();
//                databaseReference.push().child("Data").setValue(VacData);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        } else {
            Toast.makeText(RequestLeaveActivity.this, "Please Select the time", Toast.LENGTH_SHORT).show();
        }
    }
}
