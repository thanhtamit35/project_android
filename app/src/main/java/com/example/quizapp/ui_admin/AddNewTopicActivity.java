package com.example.quizapp.ui_admin;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.quizapp.R;
import com.example.quizapp.Utils;
import com.example.quizapp.database.DBHelper;
import com.example.quizapp.model.Topic;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.io.InputStream;
import java.util.Objects;

public class AddNewTopicActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 100;
    private static final int RESULT_OK = -1;
    DBHelper dbHelper = new DBHelper(this);
    Uri selectImageUri;
    ImageView img;
    MaterialTextView twChooseImg;
    TextInputEditText edtNameTopic;
    MaterialButton btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_topic);

        mapping();
        addActions();
    }

    private void addActions() {
        twChooseImg.setOnClickListener(view -> {
            if (hasStoragePermission(AddNewTopicActivity.this)) {
                openImageChooser();
            } else {
                ActivityCompat.requestPermissions(((AppCompatActivity) AddNewTopicActivity.this),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);

            }
        });

        btnSave.setOnClickListener(view -> {
            String nameTopic = edtNameTopic.getText().toString();

            if (selectImageUri != null && !nameTopic.isEmpty()) {
                if (dbHelper.getTopic(nameTopic) != null) {
                    Toast.makeText(this, "Topic đã tồn tại!", Toast.LENGTH_SHORT).show();
                } else if (saveTopicInDB(selectImageUri)) {
                    Toast.makeText(this, "Thêm mới topic thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Thêm mới topic không thành công!", Toast.LENGTH_SHORT).show();
                }
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (loadImageFromDb()) {
//                            imageView.setVisibility(View.VISIBLE);
//                            showMessage("Image loaded from database successfully!");
//                        }
//                    }
//                }, 3000);
            } else {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(view -> startActivity(new Intent(this, ManageTopicActivity.class)));
    }

    private void mapping() {
        twChooseImg = findViewById(R.id.tw_choose_img);
        edtNameTopic = findViewById(R.id.edt_topic_name);
        img = findViewById(R.id.img_topic);
        btnSave = findViewById(R.id.btn_add);
        btnCancel = findViewById(R.id.btn_cancel);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                assert data != null;
                selectImageUri = data.getData();

                if (selectImageUri != null) {
                    img.setImageURI(selectImageUri);
                }
            }
        }
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    private boolean saveTopicInDB(Uri selectImageUri) {
        try {
            String topicName = Objects.requireNonNull(edtNameTopic.getText()).toString();
            InputStream inputStream = getContentResolver().openInputStream(selectImageUri);
            byte[] inputData = Utils.getBytes(inputStream);
            dbHelper.insertTopic(topicName, inputData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean hasStoragePermission(Context context) {
        int read = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        return read == PackageManager.PERMISSION_GRANTED;
    }
}