package com.example.quizapp.ui_admin;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.quizapp.R;
import com.example.quizapp.adapter.TopicAdapter;
import com.example.quizapp.database.DBHelper;
import com.example.quizapp.model.Topic;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManageTopicActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    TopicAdapter adapter;
    List<Topic> topics;
    List<Topic> topicFiltered;
    EditText edtSearch;
    ListView listView;
    DBHelper dbHelper = new DBHelper(this);
    MaterialButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_topic);
        createActionbar();
        mapping();

        topics = new ArrayList<>();
        topics = getAll();

        adapter = new TopicAdapter(this, R.layout.item, topics);
        listView.setAdapter(adapter);

        addActions();
    }

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
        btnAdd.setOnClickListener(view -> {
            startActivity(new Intent(this, AddNewTopicActivity.class));
        });
    }

    private void mapping() {
        listView = findViewById(R.id.lvw_list);
        edtSearch = findViewById(R.id.edt_search);
        btnAdd = findViewById(R.id.btn_add_new_topic);
    }

    private List<Topic> getAll() {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT * FROM tbl_topic";

        Cursor cursor = dbHelper.selectSQL(sql);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] img = cursor.getBlob(2);

            Topic topic = new Topic(id, name, img);
            topics.add(topic);
        }

        return topics;
    }
}