package com.example.e_hrsystem.employee;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.e_hrsystem.R;
import com.example.e_hrsystem.employeeFragments.AttendanceFragment;
import com.example.e_hrsystem.employeeFragments.HomeFragment;
import com.example.e_hrsystem.employeeFragments.OffersFragment;
import com.example.e_hrsystem.employeeFragments.ProfileFragment;
import com.example.e_hrsystem.employeeFragments.VacationsFragment;
import com.example.e_hrsystem.utils.SharedPreferencesHelper;
import com.example.e_hrsystem.utils.SplashActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class EmployeeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment,new ProfileFragment());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_profile:

                    fragment = new ProfileFragment();


                break;

            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;

            case R.id.navigation_vacations:
                fragment = new VacationsFragment();
                break;

            case R.id.navigation_offers:
                fragment = new OffersFragment();
                break;

            case R.id.navigation_attendance:
                fragment = new AttendanceFragment();
                break;

            case R.id.navigation_logout:
                findViewById(R.id.navigation_logout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // clear the saved name that is saved in the SP, and redirect the user to the splash screen
                        SharedPreferencesHelper.logout(EmployeeActivity.this);
                        startActivity(new Intent(EmployeeActivity.this, SplashActivity.class));
                        finish();
                    }
                });

                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_fragment, fragment)
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