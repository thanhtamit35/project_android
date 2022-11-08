package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.quizapp.ui_user.AboutActivity;
import com.example.quizapp.ui_user.HistoryActivity;
import com.example.quizapp.ui_user.LoginActivity;
import com.example.quizapp.ui_user.MainActivity;
import com.example.quizapp.ui_user.SelectTopicActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class EditInfoActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        createActionBar();

        sharedPreferences = getSharedPreferences("acc_user_name.xml", MODE_PRIVATE);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.tw_full_name);
        navUsername.setText("Hello, " + sharedPreferences.getString("fullName", null));

        headerView.findViewById(R.id.btn_edit_account).setOnClickListener(view -> {
            startActivity(new Intent(this, EditInfoActivity.class));
            finish();
        });
    }

    /**
     * Method create actionbar
     */
    @SuppressLint("NonConstantResourceId")
    private void createActionBar() {
        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Todo: change color for actionbar
        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#00c853"));

        // Set BackgroundDrawable
        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        // Todo: Change color for toggle button menu
        actionBarDrawerToggle.getDrawerArrowDrawable()
                .setColor(getResources().getColor(R.color.white));

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawer(GravityCompat.START);

            switch (item.getItemId()) {
                case R.id.mnu_home:
                    finish();
                    startActivity(new Intent(this, MainActivity.class));
                    break;
                case R.id.mnu_start_quiz:
                    finish();
                    startActivity(new Intent(this, SelectTopicActivity.class));
                    break;
                case R.id.mnu_history:
                    finish();
                    startActivity(new Intent(this, HistoryActivity.class));
                    break;
                case R.id.mnu_about_us:
                    finish();
                    startActivity(new Intent(this, AboutActivity.class));
                    break;
                case R.id.mnu_logout:
                    finish();
                    startActivity(new Intent(this, LoginActivity.class));
                    break;
            }

            return true;
        });
    }

    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}