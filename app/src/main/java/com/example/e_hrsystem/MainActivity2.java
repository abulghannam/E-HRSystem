package com.example.e_hrsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


import com.example.e_hrsystem.utils.SharedPreferencesHelper;
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

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
                        SharedPreferencesHelper.logout(MainActivity2.this);
                        startActivity(new Intent(MainActivity2.this, SplashActivity.class));
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
}