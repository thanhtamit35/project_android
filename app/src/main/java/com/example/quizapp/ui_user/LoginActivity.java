package com.example.quizapp.ui_user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.quizapp.R;
import com.example.quizapp.database.DBHelper;
import com.example.quizapp.model.Account;
import com.example.quizapp.ui_admin.AdminActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private boolean passwordShow = false;
    EditText edtUsername;
    EditText edtPassword;
    AppCompatButton btnLogin;
    TextView twSignUp;
    ImageView imgPasswordIcon;
    SharedPreferences sharedPreferences;
    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(this.getSupportActionBar()).hide();

        mapping();

        addActions();
    }

    private void addActions() {
        imgPasswordIcon.setOnClickListener(view -> {
            if (passwordShow) {
                passwordShow = false;
                edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                imgPasswordIcon.setImageResource(R.drawable.password_show);
            } else {
                passwordShow = true;
                edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                imgPasswordIcon.setImageResource(R.drawable.password_hide);
            }

            edtPassword.setSelection(edtPassword.length());
        });

        btnLogin.setOnClickListener(view -> {
            String userName = edtUsername.getText().toString();
            String pass = edtPassword.getText().toString();
            sharedPreferences = getSharedPreferences("acc_user_name.xml", MODE_PRIVATE);

            if (userName.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "T??i kho???n m???t kh???u kh??ng ???????c ????? tr???ng!", Toast.LENGTH_LONG).show();
            } else {
                Account acc = dbHelper.getAccount(userName);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (acc == null) {
                    Toast.makeText(this, "T??i kho???n kh??ng t???n t???i!", Toast.LENGTH_SHORT).show();
                } else if (pass.equals(acc.getPassword()) && userName.equals(acc.getUserName())) {
                    boolean isAdmin;

                    if (acc.getIdRole() == 1) {
                        finish();
                        startActivity(new Intent(this, AdminActivity.class));
                        editor.putString("adminName", acc.getUserName());
                        editor.putString("fullNameAdmin", acc.getFullName());
                        isAdmin = true;
                    } else {
                        finish();
                        startActivity(new Intent(this, MainActivity.class));
                        editor.putString("userName", acc.getUserName());
                        editor.putString("fullName", acc.getFullName());

                        isAdmin = false;
                    }

                    editor.putBoolean("isAdmin", isAdmin);

                    Toast.makeText(this, "????ng nh???p th??nh c??ng!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "T??i kho???n ho???c m???t kh???u kh??ng ch??nh x??c!", Toast.LENGTH_SHORT).show();
                }

                editor.commit();
            }
        });

        twSignUp.setOnClickListener(view ->
        {
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void mapping() {
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_sign_in);
        twSignUp = findViewById(R.id.tw_signup);
        imgPasswordIcon = findViewById(R.id.img_password_icon);
    }
}