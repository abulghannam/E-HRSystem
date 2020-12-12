package com.example.e_hrsystem.employeeFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.e_hrsystem.R;
import com.example.e_hrsystem.offerselements.Hotels;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OffersFragment extends Fragment {
    Button btn_Hotels, btn_RestCafe, btn_Others;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, null);

        btn_Hotels = view.findViewById(R.id.btnHotels);
        btn_RestCafe = view.findViewById(R.id.btnRestCafe);
        btn_Others = view.findViewById(R.id.btnOthers);

        btn_Hotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Hotels.class));
            }
        });

        return view;


    }}
