package com.example.e_hrsystem.adminFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.e_hrsystem.R;
import com.example.e_hrsystem.offerselements.Hotels;
import com.example.e_hrsystem.offerselements.OthersOffer;
import com.example.e_hrsystem.offerselements.RestAndCafe;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OffersAdminFragment extends Fragment {
    Button btn_Hotels_Admin, btn_RestCafe_Admin, btn_Others_Admin;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_offers, null);

        btn_Hotels_Admin = view.findViewById(R.id.btnHotelsAdmin);
        btn_RestCafe_Admin = view.findViewById(R.id.btnRestCafeAdmin);
        btn_Others_Admin = view.findViewById(R.id.btnOthersAdmin);

        btn_Hotels_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Hotels.class));
            }
        });

        btn_RestCafe_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RestAndCafe.class));
            }
        });

        btn_Others_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OthersOffer.class));
            }
        });

        return view;

}}
