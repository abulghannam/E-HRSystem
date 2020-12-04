package com.example.e_hrsystem;


import android.os.Bundle;

import com.example.e_hrsystem.model.RequestVacationData;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VacRecyclerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vac_recycler);

        recyclerView =(RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<RequestVacationData> options =
                new FirebaseRecyclerOptions.Builder<RequestVacationData>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Request vacations data"), RequestVacationData.class)
                .build();

        adapter =  new MyAdapter(options);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }
}
