package com.example.e_hrsystem;

import android.os.Bundle;

import com.example.e_hrsystem.model.RequestLeaveData;
import com.example.e_hrsystem.model.RequestVacationData;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LevRecyclerActivity extends AppCompatActivity {

    RecyclerView recyclerViewLev;
    MyAdapterLev adapterLev;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lev_recycler);

        recyclerViewLev =findViewById(R.id.recyclerview1);
        recyclerViewLev.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<RequestLeaveData> options =
                new FirebaseRecyclerOptions.Builder<RequestLeaveData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Request leaves data"), RequestLeaveData.class)
                        .build();

        adapterLev =  new MyAdapterLev(options);
        recyclerViewLev.setAdapter(adapterLev);

    }

    @Override
    protected void onStart(){
        super.onStart();
        adapterLev.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        adapterLev.stopListening();
    }
}
