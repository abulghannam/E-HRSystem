package com.example.e_hrsystem.employeeFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.e_hrsystem.R;
import com.example.e_hrsystem.employee.ViewLevRequestActivity;
import com.example.e_hrsystem.employee.ViewVacRequestActivity;
import com.example.e_hrsystem.employee.RequestLeaveActivity;
import com.example.e_hrsystem.employee.RequestVacationActivity;

import androidx.fragment.app.Fragment;

public class VacationsFragment extends Fragment {
    View view;
    Button btnReqVac,btnLevVac,btnReviewYourVac,btnReviewYourLev;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_vacations, container, false);

        btnReqVac = view.findViewById(R.id.btnRequestVacation);

        btnReqVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), RequestVacationActivity.class));
            }
        });
        btnLevVac = view.findViewById(R.id.btnRequestLeave);
        btnLevVac.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view1) {

                startActivity(new Intent(getActivity(), RequestLeaveActivity.class));
            }
        });

        btnReviewYourVac = view.findViewById(R.id.btnReviewVacationReq);

        btnReviewYourVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ViewVacRequestActivity.class));

            }
        });

        btnReviewYourLev = view.findViewById(R.id.btnReviewLeaveReq);
        btnReviewYourLev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ViewLevRequestActivity.class));

            }
        });

        return view;
    }
}
