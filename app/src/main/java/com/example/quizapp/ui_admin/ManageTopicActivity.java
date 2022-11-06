package com.example.quizapp.ui_admin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.R;
import com.example.quizapp.adapter.TopicAdapter;
import com.example.quizapp.database.DBHelper;
import com.example.quizapp.model.Topic;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ManageTopicActivity extends AppCompatActivity {
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

        mapping();

        topics = new ArrayList<>();
        topics = getAll();

        adapter = new TopicAdapter(this, R.layout.item, topics);
        listView.setAdapter(adapter);

        addActions();
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