package com.example.e_hrsystem.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_hrsystem.R;
import com.example.e_hrsystem.adminFragments.AdminConsoleFragment;
import com.example.e_hrsystem.adminFragments.AttendanceAdminFragment;
import com.example.e_hrsystem.adminFragments.HomeAdminFragment;
import com.example.e_hrsystem.adminFragments.OffersAdminFragment;
import com.example.e_hrsystem.adminFragments.ProfileAdminFragment;
import com.example.e_hrsystem.adminFragments.VacationsAdminFragment;


import com.example.e_hrsystem.model.User;
import com.example.e_hrsystem.utils.SplashActivityJava;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainAdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FirebaseAuth auth;

    TextView nameAdmin,emailAdmin;

    User userAdmin;

    DatabaseReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_drawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userAdmin = new User();
        auth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawerAdmin);
        navigationView = findViewById(R.id.navigationView);

        View headerView = navigationView.getHeaderView(0);

        nameAdmin = headerView.findViewById(R.id.tvemployeeName);
        emailAdmin = headerView.findViewById(R.id.tvemployeeEmail);

        dbRef = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment,new ProfileAdminFragment());
        fragmentTransaction.commit();

        getUSerData();
    }

    private void getUSerData(){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userAdmin = snapshot.getValue(User.class);

                nameAdmin.setText(userAdmin.getUsername());
                emailAdmin.setText(userAdmin.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        Fragment fragmentAdmin = null;

        switch (item.getItemId()) {
            case R.id.navigation_admin_profile:
                fragmentAdmin = new ProfileAdminFragment();
                break;

            case R.id.navigation_admin_home:
                fragmentAdmin = new HomeAdminFragment();
                break;

            case R.id.navigation_admin_vacations:
                fragmentAdmin = new VacationsAdminFragment();
                break;

            case R.id.navigation_admin_offers:
                fragmentAdmin = new OffersAdminFragment();
                break;

            case R.id.navigation_admin_attendance:
                fragmentAdmin = new AttendanceAdminFragment();
                break;

            case R.id.navigation_admin_console:
                fragmentAdmin = new AdminConsoleFragment();
                break;

            case R.id.navigation_admin_logout:
                auth.signOut();
                startActivity(new Intent(MainAdminActivity.this, SplashActivityJava.class));
                finish();
                break;
        }

        return loadFragment(fragmentAdmin);
    }

    private boolean loadFragment(Fragment fragmentAdmin) {
        //switching fragment
        if (fragmentAdmin != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_fragment, fragmentAdmin)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }
    boolean doubleBackToExitPressedOnce = false;
    public void onBackPressed(){
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }


        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}








//    FirebaseAuth auth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_admin_console);
//        // init the listeners
//        auth = FirebaseAuth.getInstance();
//        initListeners();
//
//        // get the saved name and display it
////        String name = SharedPreferencesHelper.getSavedName(this);
////        ((TextView)findViewById(R.id.textView)).setText(name);
//    }
//
//    private void initListeners() {
//        findViewById(R.id.btnDeleteEmployee).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // when delete employee button clicked, open the desired activity
//                startActivity(new Intent(MainAdminActivity.this, DeleteEmployeeActivity.class));
//            }
//        });
//        findViewById(R.id.btnAddEmployee).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // when add employee button clicked, open the desired activity
//                startActivity(new Intent(MainAdminActivity.this, AddEmployeeActivity.class));
//            }
//        });
//
//        findViewById(R.id.btnReviewVacationRequestsData).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainAdminActivity.this, VacRecyclerActivity.class));
//            }
//        });
//
//        findViewById(R.id.btnReviewLeaveRequestsData).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainAdminActivity.this, LevRecyclerActivity.class));
//            }
//        });
//
//        findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // clear the saved name that is saved in the SP, and redirect the user to the splash screen
//                //SharedPreferencesHelper.logout(MainAdminActivity.this);
//                auth.signOut();
//                startActivity(new Intent(MainAdminActivity.this, SplashActivityJava.class));
//                finish();
//            }
//        });
//    }
