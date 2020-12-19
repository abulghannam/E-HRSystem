package com.example.e_hrsystem.adminFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.e_hrsystem.R;
import com.example.e_hrsystem.admin.AddEmployeeActivity;
import com.example.e_hrsystem.admin.DeleteEmployeeActivity;
import com.example.e_hrsystem.admin.LevRecyclerActivity;
import com.example.e_hrsystem.admin.VacRecyclerActivity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AdminConsoleFragment extends Fragment {

    View view;
    Button btnAddNewUser,btnDeleteUser,btnReviewReqVac,btnReviewReqLev;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_admin_console, container, false);

        btnAddNewUser = view.findViewById(R.id.btnAddEmployee);
        btnDeleteUser = view.findViewById(R.id.btnDeleteEmployee);
        btnReviewReqVac = view.findViewById(R.id.btnReviewVacationRequestsDataAdminConsole);
        btnReviewReqLev = view.findViewById(R.id.btnReviewLeaveRequestsDataAdminConsole);

        btnAddNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddEmployeeActivity.class));
            }
        });

        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DeleteEmployeeActivity.class));
            }
        });

        btnReviewReqVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), VacRecyclerActivity.class));
            }
        });

        btnReviewReqLev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LevRecyclerActivity.class));
            }
        });

        return view;
    }
}
