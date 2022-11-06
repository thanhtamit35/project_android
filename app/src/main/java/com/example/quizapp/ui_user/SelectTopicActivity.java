package com.example.quizapp.ui_user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ListAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.R;
import com.example.quizapp.adapter.TopicUserAdapter;
import com.example.quizapp.database.DBHelper;
import com.example.quizapp.model.Topic;

import java.util.List;

public class SelectTopicActivity extends AppCompatActivity {
    GridView gridView;
    List<Topic> topics;
    Adapter adapter;
    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_topic);

        init();
        addActions();
    }

    private void addActions() {
        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            Topic topic = (Topic) view.getTag();
            int id = topic.getIdTopic();

            Intent intent = new Intent(this, StartQuizActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        });
    }

    private void init() {
        topics = dbHelper.getAllTopic();

        gridView = findViewById(R.id.id_grid_view);
        adapter = new TopicUserAdapter(this, R.layout.card_item, topics);
        gridView.setAdapter((ListAdapter) adapter);
    }
}