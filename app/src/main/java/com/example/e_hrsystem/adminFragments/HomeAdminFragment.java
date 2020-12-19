package com.example.e_hrsystem.adminFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_hrsystem.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeAdminFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_home, null);
    }
}
