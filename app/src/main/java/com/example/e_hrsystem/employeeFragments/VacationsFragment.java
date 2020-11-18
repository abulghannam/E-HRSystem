package com.example.e_hrsystem.employeeFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.e_hrsystem.R;
import com.example.e_hrsystem.employee.RequestLeaveActivity;
import com.example.e_hrsystem.employee.RequestVacationActivity;

import androidx.fragment.app.Fragment;

public class VacationsFragment extends Fragment {
    View view;
    Button firstButton,secondButton;

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

        return view;
    }
}
