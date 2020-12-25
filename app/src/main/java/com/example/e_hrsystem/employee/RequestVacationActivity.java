package com.example.e_hrsystem.employee;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RequestVacationActivity extends AppCompatActivity {

    Spinner typeSpinner;
    TextView etStartDate, etEndDate;
    EditText etMoreinfo;
    Button btnSendReqVac;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    DatabaseReference dbRefUser;
    FirebaseAuth auth;
    TextView tvUsername;
    User user;
    RequestVacationData requestVacationData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_vacation);

        user = new User();
        requestVacationData = new RequestVacationData();
//        databaseReference = db.getReference("Request Vacations Data");
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Request vacations data");
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

        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(true);
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(false);
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

    private boolean isValidRequest(){
        // To check if the current request does not overlap another request
        // Steps:
        // First, get the list of the request for the current user
        // Second, iterate on each request and check if its valid or not, note that all the current request should return => Valid
        // when using this method:  isValidStartDate()
        return true;
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
                        DatabaseReference dbRefCheckVacation = databaseReference.child(id);
                        dbRefCheckVacation.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                requestVacationData = snapshot.getValue(RequestVacationData.class);

                                String vacationStatus = requestVacationData.isApproved();
                                if (vacationStatus.equals("Approved")||vacationStatus.equals("Declined")){

                                    DatabaseReference dbRefCurrentUserId = databaseReference.child(id);
                                    RequestVacationData vacData = new RequestVacationData(type, StartDateVac, EndDateVac,
                                            MoreInfo, id, "In queue", tvUsername.getText().toString());
                                    dbRefCurrentUserId.setValue(vacData);
                                    Toast.makeText(RequestVacationActivity.this, "The Request has been sent",
                                            Toast.LENGTH_SHORT).show();
//                                    etStartDate.getText().clear();
//                                    etEndDate.getText().clear();
                                    etMoreinfo.getText().clear();
                                    typeSpinner.setSelection(0);

                                }else{
                                    Toast.makeText(RequestVacationActivity.this, "There is already a pending request",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }else{
                        DatabaseReference dbRefCurrentUserId = databaseReference.child(id);
                        RequestVacationData vacData = new RequestVacationData(type, StartDateVac, EndDateVac,
                                MoreInfo, id, "In queue", tvUsername.getText().toString());
                        dbRefCurrentUserId.setValue(vacData);
                        Toast.makeText(RequestVacationActivity.this, "The Request has been sent",
                                Toast.LENGTH_SHORT).show();
//                        etStartDate.getText().clear();
//                        etEndDate.getText().clear();
                        etMoreinfo.getText().clear();
                        typeSpinner.setSelection(0);

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

    private Date startDate = null;

    private void showDateDialog(final boolean isStartDate){
        final Calendar calendar = Calendar.getInstance();

        if (startDate == null && !isStartDate){
            Toast.makeText(this, "Please select the start date first", Toast.LENGTH_SHORT).show();
            return;
        }
        
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(year, month, dayOfMonth);
                Date date = calendar1.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

                if (isStartDate){
                    // get the start date
                    startDate = date;
                    etStartDate.setText(sdf.format(date));
                }else {
                    // get the end date
                    etEndDate.setText(sdf.format(date));
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        if (startDate != null){
            datePickerDialog.getDatePicker().setMinDate(startDate.getTime() + TimeUnit.DAYS.toMillis(1));
        }
        datePickerDialog.show();
    }
}
