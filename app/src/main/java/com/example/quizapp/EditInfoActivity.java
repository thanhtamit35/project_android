package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.quizapp.database.DBHelper;
import com.example.quizapp.model.Account;
import com.example.quizapp.ui_admin.AdminActivity;
import com.example.quizapp.ui_user.AboutActivity;
import com.example.quizapp.ui_user.HistoryActivity;
import com.example.quizapp.ui_user.LoginActivity;
import com.example.quizapp.ui_user.MainActivity;
import com.example.quizapp.ui_user.SelectTopicActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class EditInfoActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    TextInputEditText txtFullName, txtOldPass, txtNewPass, txtConfirmPass;
    MaterialButton btnAllowEdit, btnSave, btnCancel;
    DBHelper dbHelper = new DBHelper(this);
    String fullName, userName, pass;
    int id = 0;
    private RadioGroup radioGroup;
    boolean isChangeName = false, isChangePass = false, isAdmin;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        createActionBar();

        mapping();
        addActions();
    }

    @SuppressLint({"NonConstantResourceId", "CommitPrefEdits", "ApplySharedPref"})
    private void addActions() {
        btnAllowEdit.setOnClickListener(view -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();

            switch (selectedId) {
                case R.id.rdo_change_name:
                    isChangeName = true;
                    txtFullName.setEnabled(true);
                    txtOldPass.setEnabled(false);
                    txtNewPass.setEnabled(false);
                    txtConfirmPass.setEnabled(false);
                    btnSave.setEnabled(true);
                    break;
                case R.id.rdo_change_pass:
                    isChangePass = true;
                    txtOldPass.setEnabled(true);
                    txtNewPass.setEnabled(true);
                    txtConfirmPass.setEnabled(true);
                    txtFullName.setEnabled(false);
                    btnSave.setEnabled(true);
                    break;
                default:
                    Toast.makeText(this, "Vui lòng chọn danh mục cần chỉnh sửa!", Toast.LENGTH_SHORT).show();
                    btnSave.setEnabled(false);
                    break;
            }
        });

        btnSave.setOnClickListener(view -> {
            int idRole = isAdmin ? 1 : 2;

            if (isChangeName) {
                String name = Objects.requireNonNull(txtFullName.getText()).toString();

                if (isAdmin) {
                    sharedPreferences.edit().putString("fullNameAdmin", name);
                } else {
                    sharedPreferences.edit().putString("fullName", name);
                }
                sharedPreferences.edit().apply();
                Account account = new Account(id, userName, pass, idRole, txtFullName.getText().toString());
                dbHelper.updateAcc(account);

                Toast.makeText(this, "Cập nhật tên thành công! Vui lòng đăng nhập lại để cập nhật dữ liệu!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                btnSave.setEnabled(false);
                finish();
            } else if (isChangePass) {
                String oldPass = Objects.requireNonNull(txtOldPass.getText()).toString();
                String newPass = Objects.requireNonNull(txtNewPass.getText()).toString();
                String confirmPass = Objects.requireNonNull(txtConfirmPass.getText()).toString();

                if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty() || oldPass.equals(newPass) || !newPass.equals(confirmPass)) {
                    Toast.makeText(this, "Đổi mật khẩu không thành công. Vui lòng kiểm tra lại!", Toast.LENGTH_SHORT).show();
                } else {
                    Account account = new Account(id, userName, newPass, idRole, Objects.requireNonNull(txtFullName.getText()).toString());
                    dbHelper.updateAcc(account);

                    btnSave.setEnabled(false);
                    Toast.makeText(this, "Đổi mật khẩu thành công! Vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(view -> {
            if (isAdmin ) {
                startActivity(new Intent(this, AdminActivity.class));
            } else {
                startActivity(new Intent(this, MainActivity.class));
            }

            finish();
        });
    }

    @SuppressLint("SetTextI18n")
    private void mapping() {

        sharedPreferences = getSharedPreferences("acc_user_name.xml", MODE_PRIVATE);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.tw_full_name);
        navUsername.setText("Hello, " + sharedPreferences.getString("fullName", null));

        headerView.findViewById(R.id.btn_edit_account).setOnClickListener(view -> {
            startActivity(new Intent(this, EditInfoActivity.class));
            finish();
        });

        txtFullName = findViewById(R.id.edt_full_name_edit);
        txtOldPass = findViewById(R.id.edt_old_pass_edit);
        txtNewPass = findViewById(R.id.edt_new_pass_edit);
        txtConfirmPass = findViewById(R.id.edt_confirm_pass_edit);
        btnAllowEdit = findViewById(R.id.btn_allow_edt);
        btnSave = findViewById(R.id.btn_save_edit);
        btnCancel = findViewById(R.id.btn_cancel_edit);
        radioGroup = findViewById(R.id.rdo_group);

        isAdmin = sharedPreferences.getBoolean("isAdmin", false);

        if (isAdmin) {
            fullName = sharedPreferences.getString("fullNameAdmin", null);
            userName = sharedPreferences.getString("adminName", null);
        } else {
            fullName = sharedPreferences.getString("fullName", null);
            userName = sharedPreferences.getString("userName", null);
        }

        Account account = dbHelper.getAccount(userName);
        pass = account.getPassword();
        id = account.getIdAcc();

        txtFullName.setText(fullName);
        txtOldPass.setText(pass);
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