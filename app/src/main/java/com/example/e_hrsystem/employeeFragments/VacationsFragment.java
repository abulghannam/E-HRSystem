package com.example.e_hrsystem.employeeFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.e_hrsystem.R;
import com.example.e_hrsystem.ViewLevRequestActivity;
import com.example.e_hrsystem.ViewVacRequestActivity;
import com.example.e_hrsystem.employee.RequestLeaveActivity;
import com.example.e_hrsystem.employee.RequestVacationActivity;
import com.google.firebase.database.ValueEventListener;

import androidx.fragment.app.Fragment;

public class VacationsFragment extends Fragment {
    View view;
    Button firstButton,secondButton,btnReviewYourVac,btnReviewYourLev;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_vacations, container, false);
// get the reference of Button
        firstButton = (Button) view.findViewById(R.id.btnRequestVacation);
// perform setOnClickListener on first Button
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// display a message by using a Toast
                startActivity(new Intent(getActivity(), RequestVacationActivity.class));
            }
        });
        secondButton = (Button) view.findViewById(R.id.btnRequestLeave);
        secondButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view1) {
// display a message by using a Toast
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
