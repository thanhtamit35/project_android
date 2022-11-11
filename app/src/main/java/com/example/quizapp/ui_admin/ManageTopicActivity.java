package com.example.quizapp.ui_admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.quizapp.EditInfoActivity;
import com.example.quizapp.R;
import com.example.quizapp.adapter.TopicAdapter;
import com.example.quizapp.database.DBHelper;
import com.example.quizapp.model.Topic;
import com.example.quizapp.ui_user.LoginActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManageTopicActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    TopicAdapter adapter;
    List<Topic> topics;
    List<Topic> topicFiltered;
    TextInputEditText edtSearch;
    MaterialButton btnEditAcc;
    ListView listView;
    DBHelper dbHelper = new DBHelper(this);
    MaterialButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_topic);
        createActionbar();
        mapping();
        addActions();
    }

    @SuppressLint("NonConstantResourceId")
    private void createActionbar() {
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
                case R.id.mnu_topic:
                    startActivity(new Intent(this, ManageTopicActivity.class));
                    break;

                case R.id.mnu_question:
                    startActivity(new Intent(this, ManageQuestionActivity.class));
                    break;
                case R.id.mnu_logout:
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                    break;
            }

            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addActions() {
        btnAdd.setOnClickListener(view -> startActivity(new Intent(this, AddNewTopicActivity.class)));

        btnEditAcc.setOnClickListener(view -> {
            startActivity(new Intent(this, EditInfoActivity.class));
            finish();
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                topicFiltered = new ArrayList<>();

                String s = charSequence.toString().toLowerCase();

                if (topicFiltered.size() > 0)
                    topicFiltered.clear();
                if (s.length() == 0) {
                    topicFiltered.addAll(topics);
                } else {
                    for (Topic t : topics) {
                        if (t.getNameTopic().toLowerCase().contains(s)) {
                            topicFiltered.add(t);
                        }
                    }
                }

                adapter = new TopicAdapter(ManageTopicActivity.this, R.layout.item, topicFiltered);
                listView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void mapping() {
        listView = findViewById(R.id.lvw_list);
        edtSearch = findViewById(R.id.edt_search);
        btnAdd = findViewById(R.id.btn_add_new_topic);

        sharedPreferences = getSharedPreferences("acc_user_name.xml", MODE_PRIVATE);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.tw_full_name);
        navUsername.setText("Hi, " + sharedPreferences.getString("fullNameAdmin", null));

        topics = new ArrayList<>();
        topics = dbHelper.getAllTopic();

        adapter = new TopicAdapter(this, R.layout.item, topics);
        listView.setAdapter(adapter);

        btnEditAcc = headerView.findViewById(R.id.btn_edit_account);
    }
}