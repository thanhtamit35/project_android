package com.example.quizapp.ui_admin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.R;
import com.example.quizapp.adapter.QuestionAdapter;
import com.example.quizapp.adapter.TopicAdapter;
import com.example.quizapp.database.DBHelper;
import com.example.quizapp.model.Question;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ManageQuestionActivity extends AppCompatActivity {

    QuestionAdapter adapter;
    List<Question> questions;
    List<Question> questionFiltered;
    EditText edtSearch;
    ListView listView;
    DBHelper dbHelper = new DBHelper(this);
    MaterialButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_question);

        mapping();

        questions = new ArrayList<>();
        questions = getAll();

        adapter = new QuestionAdapter(this, R.layout.item_question, questions);
        listView.setAdapter(adapter);
        addActions();
    }

    private void addActions() {
        btnAdd.setOnClickListener(view -> {
            startActivity(new Intent(this, AddNewQuestionActivity.class));
        });
    }

    private void mapping() {
        listView = findViewById(R.id.lvw_list_question);
        edtSearch = findViewById(R.id.edt_search);
        btnAdd = findViewById(R.id.btn_add_new_question);
    }

    /**
     * Method get all record from table tbl_question
     *
     * @return list question
     */
    private List<Question> getAll() {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM tbl_question";

        Cursor cursor = dbHelper.selectSQL(sql);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String content = cursor.getString(1);
            int idTopic = cursor.getInt(2);
            String option1 = cursor.getString(3);
            String option2 = cursor.getString(4);
            String option3 = cursor.getString(5);
            String option4 = cursor.getString(6);
            String answer = cursor.getString(7);

            Question question = new Question(id, content, idTopic, option1, option2, option3, option4, answer);
            questions.add(question);
        }

        return questions;
    }
}