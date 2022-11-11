package com.example.quizapp.ui_user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.quizapp.R;
import com.example.quizapp.adapter.TopicUserAdapter;
import com.example.quizapp.database.DBHelper;
import com.example.quizapp.model.Topic;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Objects;

public class SelectTopicActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    SharedPreferences sharedPreferences;
    NavigationView navigationView;
    GridView gridView;
    List<Topic> topics;
    Adapter adapter;
    DBHelper dbHelper = new DBHelper(this);

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_topic);
        createActionBar();
        sharedPreferences = getSharedPreferences("acc_user_name.xml", MODE_PRIVATE);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.tw_full_name);
        navUsername.setText("Hello, " + sharedPreferences.getString("fullName", null));
        init();
        addActions();
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

    /**
     * Method add action click on item in gridview
     */
    private void addActions() {
        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            Topic topic = (Topic) view.getTag();
            int id = topic.getIdTopic();

            if (dbHelper.getQuestionByIdTopic(id).size() == 0) {
                Toast.makeText(this, "Câu hỏi sẽ được cập nhật sớm. Vui lòng chọn topic khác!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, StartQuizActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    /**
     * Method display topics to screen
     */
    private void init() {
        topics = dbHelper.getAllTopic();

        gridView = findViewById(R.id.id_grid_view);
        adapter = new TopicUserAdapter(this, R.layout.card_item, topics);
        gridView.setAdapter((ListAdapter) adapter);
    }
}