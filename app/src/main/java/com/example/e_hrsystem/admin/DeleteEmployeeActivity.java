package com.example.e_hrsystem.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_hrsystem.R;
import com.example.e_hrsystem.authentication.LoginActivity;
import com.example.e_hrsystem.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DeleteEmployeeActivity extends AppCompatActivity {
    User userModel = new User();

    EditText etDeleteUser;
    FirebaseAuth auth;
  //  FirebaseUser user;

    DatabaseReference dbRef ;
    Button btnDelete;
    String systemEmail,userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_employee);

        btnDelete = findViewById(R.id.btnDelete);
        etDeleteUser = findViewById(R.id.etDeleteEmail);
        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("users");
    //    user = auth.getCurrentUser();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(DeleteEmployeeActivity.this);
                dialog1.setTitle("Are you sure?");
                dialog1.setMessage("Deleting this account will result in completely removing this " +
                        "account from the system and you won't be able to return it back.");
                dialog1.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteUser();
                                if(!userEmail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())
                                Toast.makeText(getApplicationContext(), "You have been deleted this account",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                dialog1.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = dialog1.create();
                alertDialog.show();

            }
        });

    }

    private void deleteUser(){
        userEmail = etDeleteUser.getText().toString();
        if (TextUtils.isEmpty(userEmail)) {
            etDeleteUser.setError("Email is Required!");
            etDeleteUser.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            etDeleteUser.setError("Please provide a valid email!");
            etDeleteUser.requestFocus();
            return;
        }

        if(!TextUtils.isEmpty(userEmail)){

            Toast.makeText(this, userEmail, Toast.LENGTH_SHORT).show();
            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()){
                        final String id = ds.getKey();
//                        Toast.makeText(DeleteEmployeeActivity.this, id, Toast.LENGTH_SHORT).show();
                        final DatabaseReference databaseReference = dbRef.child(id);
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                userModel = snapshot.getValue(User.class);
//                                boolean pass = userModel.isDeleted();
                                systemEmail = userModel.getEmail();
//                                Toast.makeText(DeleteEmployeeActivity.this, String.valueOf(pass), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(DeleteEmployeeActivity.this, String.valueOf(systemEmail), Toast.LENGTH_SHORT).show();
                                if (userEmail.equals(systemEmail)){

                                    Map<String, Object> updates = new HashMap<>();
                                    updates.put("deleted", true);
                                    databaseReference.updateChildren(updates);

//                                    Toast.makeText(DeleteEmployeeActivity.this, "equals", Toast.LENGTH_SHORT).show();
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
//        else{
//            Toast.makeText(this, "Please insert Email", Toast.LENGTH_SHORT).show();
//        }
    }

//    private void initListeners() {
//        findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder dialog = new AlertDialog.Builder(DeleteEmployeeActivity.this);
//                dialog.setTitle("Are you sure?");
//                dialog.setMessage("Deleting this account will result in completely removing this " +
//                        "account from the system and you won't be able to return it back.");
//                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    Toast.makeText(DeleteEmployeeActivity.this, "The user has been deleted"
//                                            , Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(DeleteEmployeeActivity.this, task.getException().getMessage()
//                                            , Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    }
//                });
//
//                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                AlertDialog alertDialog = dialog.create();
//                alertDialog.show();
//                // TODO: handle the registration process
//            }
//        });
//    }
}