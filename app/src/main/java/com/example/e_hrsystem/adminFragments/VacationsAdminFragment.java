package com.example.e_hrsystem.adminFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.e_hrsystem.R;
import com.example.e_hrsystem.employee.RequestLeaveActivity;
import com.example.e_hrsystem.employee.RequestVacationActivity;
import com.example.e_hrsystem.employee.ViewLevRequestActivity;
import com.example.e_hrsystem.employee.ViewVacRequestActivity;

import androidx.fragment.app.Fragment;

public class VacationsAdminFragment extends Fragment {

    View view;
    Button btnReqVacAdmin,btnLevVacAdmin,btnReviewYourVacAdmin,btnReviewYourLevAdmin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_admin_vacations, container, false);

        btnReqVacAdmin = view.findViewById(R.id.btnRequestVacationAdmin);

        btnReqVacAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), RequestVacationActivity.class));
            }
        });
        btnLevVacAdmin = view.findViewById(R.id.btnRequestLeaveAdmin);
        btnLevVacAdmin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view1) {

                startActivity(new Intent(getActivity(), RequestLeaveActivity.class));
            }
        });

        btnReviewYourVacAdmin = view.findViewById(R.id.btnReviewVacationReqAdmin);

        btnReviewYourVacAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ViewVacRequestActivity.class));

            }
        });

        btnReviewYourLevAdmin = view.findViewById(R.id.btnReviewLeaveReqAdmin);
        btnReviewYourLevAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ViewLevRequestActivity.class));

            }
        });

        return view;
    }
}
