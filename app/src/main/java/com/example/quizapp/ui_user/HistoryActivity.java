package com.example.quizapp.ui_user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.quizapp.R;
import com.example.quizapp.adapter.HistoryAdapter;
import com.example.quizapp.database.DBHelper;
import com.example.quizapp.model.Account;
import com.example.quizapp.model.Quiz;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoryActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    HistoryAdapter adapter;
    List<Quiz> quizzes;
    List<Quiz> quizFiltered;
    EditText edtSearch;
    ListView listView;
    DBHelper dbHelper = new DBHelper(this);

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        sharedPreferences = getSharedPreferences("acc_user_name.xml", MODE_PRIVATE);
        createActionBar();
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.tw_full_name);
        navUsername.setText("Hello, " + sharedPreferences.getString("fullName", null));
        init();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        quizzes = new ArrayList<>();
        String userName = sharedPreferences.getString("userName", null);
        quizzes = dbHelper.getResultByUser(userName);

//        adapter = new SimAdapter(this, R.layout.item, partitions.get(0));
        adapter = new HistoryAdapter(this, R.layout.item_history, quizzes);
        listView = findViewById(R.id.lvw_history);
        listView.setAdapter(adapter);

        MaterialButton btnReset = findViewById(R.id.btn_reset_result);
        btnReset.setOnClickListener(view -> showDialog());

//        edtSearch = findViewById(R.id.edt_search);

//        int index = getIntent().getIntExtra("pos", 0);
//        listView.setSelection(index);
//
//        edtSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                List<Sim> simFiltered = new ArrayList<>();
//
//                String s = charSequence.toString().toLowerCase();
//
//                if (simFiltered.size() > 0)
//                    simFiltered.clear();
//                if (s.length() == 0) {
//                    simFiltered.addAll(simList);
//                } else {
//                    for (int listNo = 0; listNo < simList.size(); listNo++) {
//                        if (simList.get(listNo).getNumber().toLowerCase().contains(s)) {
//                            Sim sim = new Sim();
//                            sim.setId(simList.get(listNo).getId()); // hear write your data to add filter define in pojo class.
//                            sim.setNumber(simList.get(listNo).getNumber());
//                            sim.setPrice(simList.get(listNo).getPrice());
//
//                            simFiltered.add(sim);
//
//                        }
//                    }
//                }
//
//                adapter = new SimAdapter(getApplicationContext(), R.layout.item, simFiltered);
//                listView.setAdapter(adapter);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
    }

    private void showDialog() {
        String userName = sharedPreferences.getString("userName", null);
        Account acc = dbHelper.getAccount(userName);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xoá lịch sử");
        builder.setMessage("Bạn có muốn xoá lịch sử?");
        builder.setPositiveButton("Có", (dialogInterface, i1) -> {
            quizzes.clear();
            adapter = new HistoryAdapter(this, R.layout.item_history, quizzes);
            listView = findViewById(R.id.lvw_history);
            listView.setAdapter(adapter);

            dbHelper.deleteHistory(acc.getIdAcc());
            for (int i = 0; i < quizzes.size(); i++) {
                dbHelper.deleteQuizQuestion(quizzes.get(i).getIdQuiz());
                dbHelper.deleteQuiz(quizzes.get(i).getIdQuiz());
            }
        });

        builder.setNegativeButton("Không", (dialogInterface, i1) -> {

        });

        builder.show();
    }

}