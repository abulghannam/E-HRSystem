package com.example.e_hrsystem.admin;


import android.os.Bundle;

import com.example.e_hrsystem.R;
import com.example.e_hrsystem.model.RequestVacationData;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VacRecyclerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapterVac adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vac_recycler);

//        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        try {
//            Date date = fmt.parse("2020-12-20");
//            Log.e("xxxxxxxxxx " , date.getTime() + "");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        recyclerView =findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<RequestVacationData> options =
                new FirebaseRecyclerOptions.Builder<RequestVacationData>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Request vacations data"), RequestVacationData.class)
                .build();

        adapter =  new MyAdapterVac(options);
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
