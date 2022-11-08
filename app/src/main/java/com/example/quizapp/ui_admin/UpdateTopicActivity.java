package com.example.quizapp.ui_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.quizapp.R;
import com.example.quizapp.Utils;
import com.example.quizapp.database.DBHelper;
import com.example.quizapp.model.Topic;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
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

        findViewById(R.id.btn_update_topic).setOnClickListener(view -> {

        });

        mapping();
        addActions();
    }

    private void addActions() {
//        TODO: event btnSave click
        btnSave.setOnClickListener(view -> {

        });

//        TODO: event btnCancel click
        btnCancel.setOnClickListener(view -> {
            startActivity(new Intent(this, ManageTopicActivity.class));
            finish();
        });
    }

    private void mapping() {
        TextInputEditText edtNameTopic = findViewById(R.id.edt_update_topic_name);
        ImageView img = findViewById(R.id.update_img_topic);

        Bundle bundleTopic = getIntent().getBundleExtra("bundle");
        topic = (Topic) bundleTopic.getSerializable("data");
        edtNameTopic.setText(topic.getNameTopic());
        img.setImageBitmap(Utils.getImage(topic.getImageTopic()));

        btnSave = findViewById(R.id.btn_update_topic);
        btnCancel = findViewById(R.id.btn_cancel_edit_topic);
    }
}