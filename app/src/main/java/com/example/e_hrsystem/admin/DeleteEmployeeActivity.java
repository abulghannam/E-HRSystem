package com.example.e_hrsystem.admin;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_hrsystem.R;

public class DeleteEmployeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_employee);
        initListeners();
    }

    private void initListeners() {
        findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = ((EditText) findViewById(R.id.etDeleteEmail)).getText().toString().trim();

                Toast.makeText(DeleteEmployeeActivity.this,"The user has been deleted"
                        , Toast.LENGTH_SHORT).show();

                // TODO: handle the registration process
            }
        });
    }
}