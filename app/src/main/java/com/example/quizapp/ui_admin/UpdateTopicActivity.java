package com.example.quizapp.ui_admin;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

public class UpdateTopicActivity extends AppCompatActivity {
    Topic topic;
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
        setContentView(R.layout.activity_update_topic);
        Objects.requireNonNull(this.getSupportActionBar()).hide();

        Bundle bundleTopic = getIntent().getBundleExtra("bundle");
        topic = (Topic) bundleTopic.getSerializable("data");

        mapping();
        addActions();
    }

    private void addActions() {

        twChooseImg.setOnClickListener(view -> {
            if (hasStoragePermission(UpdateTopicActivity.this)) {
                openImageChooser();
            } else {
                ActivityCompat.requestPermissions(((AppCompatActivity) UpdateTopicActivity.this),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
            }
        });

//        TODO: event btnSave click
        btnSave.setOnClickListener(view -> {
            saveUpdateInDB(selectImageUri);
        });

//        TODO: event btnCancel click
        btnCancel.setOnClickListener(view -> {
            startActivity(new Intent(this, ManageTopicActivity.class));
            finish();
        });
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

    private boolean saveUpdateInDB(Uri selectImageUri) {
        try {
            String topicName = Objects.requireNonNull(edtNameTopic.getText()).toString();
            InputStream inputStream = getContentResolver().openInputStream(selectImageUri);
            byte[] inputData = Utils.getBytes(inputStream);
            Topic topic1 = new Topic(topic.getIdTopic(), topicName, inputData);

            if (topicName.equals(topic.getNameTopic())) {
                dbHelper.updateImg(topic1);
            } else {
                dbHelper.updateTopic(topic1);
            }
            Toast.makeText(this, "Update success!", Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception e) {
            Log.e("Update fail!", e.getMessage());
            Toast.makeText(this, "Topic đã tồn tại!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean hasStoragePermission(Context context) {
        int read = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        return read == PackageManager.PERMISSION_GRANTED;
    }

    private void mapping() {
        edtNameTopic = findViewById(R.id.edt_update_topic_name);
        img = findViewById(R.id.img_topic_update);
        twChooseImg = findViewById(R.id.tw_choose_img);

        edtNameTopic.setText(topic.getNameTopic());
        img.setImageBitmap(Utils.getImage(topic.getImageTopic()));

        btnSave = findViewById(R.id.btn_update_topic);
        btnCancel = findViewById(R.id.btn_cancel_edit_topic);
    }
}