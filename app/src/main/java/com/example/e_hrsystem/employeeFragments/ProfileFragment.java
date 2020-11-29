package com.example.e_hrsystem.employeeFragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e_hrsystem.R;
import com.example.e_hrsystem.model.User;
import com.example.e_hrsystem.utils.SharedPreferencesHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    TextView tvName, tvEmail,tvGender;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    DatabaseReference ref;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        View view = inflater.inflate(R.layout.fragment_profile, null);

        tvName = view.findViewById(R.id.tvUsernameProfile);
        tvEmail = view.findViewById(R.id.tvEmailProfile);
        tvGender = view.findViewById(R.id.tvGenderProfile);


        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        tvName.setText(firebaseUser.getUid());
        tvEmail.setText(firebaseUser.getEmail());

        return view;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        User user = SharedPreferencesHelper.getUser(getContext());
//
//        TextView tvDetails = (TextView) view.findViewById(R.id.tvDetails);
//        tvDetails.setText(user.getUsername());
//    }
}
