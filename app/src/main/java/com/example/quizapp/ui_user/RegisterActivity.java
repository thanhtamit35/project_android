package com.example.quizapp.ui_user;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {
    EditText edtFullName, edtUserName, edtPass, edtConfirmPass;
    ImageView iconShowPass, iconShowConfirm;
    AppCompatButton btnSignUp;
    TextView twSignIn;
    private boolean passwordShow = false, passConfirmShow = false;
    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.getSupportActionBar().hide();

        mapping();
        showHidePass();
        addActions();
    }

    private void addActions() {
        twSignIn.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        btnSignUp.setOnClickListener(view -> {
            String fullName = edtFullName.getText().toString();
            String userName = edtUserName.getText().toString();
            String pass = edtPass.getText().toString();
            String confirmPass = edtConfirmPass.getText().toString();

            if (!fullName.isEmpty() && !userName.isEmpty() && !pass.isEmpty() && pass.equals(confirmPass)) {
                Account acc = new Account(0, userName, pass, 2, fullName);

                if (dbHelper.getAccount(userName) != null) {
                    Toast.makeText(this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.insertAccount(acc);
                    startActivity(new Intent(this, LoginActivity.class));
                }
            } else if (!pass.equals(confirmPass)) {
                Toast.makeText(this, "Mật khẩu không khớp.Vui lòng kiểm tra lại!", Toast.LENGTH_SHORT).show();
            }
            {
                Toast.makeText(this, "Vui lòng điền đầy đủ các thông tin!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showHidePass() {
        iconShowPass.setOnClickListener(view -> {
            if (passwordShow) {
                passwordShow = false;
                edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                iconShowPass.setImageResource(R.drawable.password_show);
            } else {
                passwordShow = true;
                edtPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                iconShowPass.setImageResource(R.drawable.password_hide);
            }

            edtPass.setSelection(edtPass.length());
        });

        iconShowConfirm.setOnClickListener(view -> {
            if (passConfirmShow) {
                passConfirmShow = false;
                edtConfirmPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                iconShowConfirm.setImageResource(R.drawable.password_show);
            } else {
                passConfirmShow = true;
                edtConfirmPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                iconShowConfirm.setImageResource(R.drawable.password_hide);
            }

            edtConfirmPass.setSelection(edtConfirmPass.length());
        });
    }

    private void mapping() {
        edtFullName = findViewById(R.id.edt_full_name);
        edtUserName = findViewById(R.id.edt_user_name);
        edtPass = findViewById(R.id.edt_password);
        edtConfirmPass = findViewById(R.id.edt_confirm_password);
        iconShowPass = findViewById(R.id.img_password_icon);
        iconShowConfirm = findViewById(R.id.img_confirm_password_icon);
        btnSignUp = findViewById(R.id.btn_sign_up);
        twSignIn = findViewById(R.id.tw_sign_in);
    }

}