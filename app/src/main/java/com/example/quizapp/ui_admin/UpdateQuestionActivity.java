package com.example.quizapp.ui_admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.R;
import com.example.quizapp.database.DBHelper;
import com.example.quizapp.model.Question;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

public class UpdateQuestionActivity extends AppCompatActivity {
    TextInputEditText txtContent, txtOption1, txtOption2, txtOption3, txtOption4;
    MaterialAutoCompleteTextView atwTopic, atwAnswer;
    MaterialButton btnSave, btnCancel;
    private final String[] answers = {"option1", "option2", "option3", "option4"};
    Question question;
    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_question);
        Objects.requireNonNull(this.getSupportActionBar()).hide();
        Bundle bundleQues = getIntent().getBundleExtra("bundle");
        question = (Question) bundleQues.getSerializable("data");

        mapping();
        addActions();
    }

    private void addActions() {
//        TODO: event btnSave click
        btnSave.setOnClickListener(view -> {
            try {
                String newContent = Objects.requireNonNull(txtContent.getText()).toString();
                int idTopic = dbHelper.getIdTopic(atwTopic.getText().toString());
                String option1 = Objects.requireNonNull(txtOption1.getText()).toString();
                String option2 = Objects.requireNonNull(txtOption2.getText()).toString();
                String option3 = Objects.requireNonNull(txtOption3.getText()).toString();
                String option4 = Objects.requireNonNull(txtOption4.getText()).toString();
                String answer = atwAnswer.getText().toString();

                Question question1 = new Question(question.getIdQuestion(), idTopic, newContent, option1, option2, option3, option4, answer);

                if (newContent.equals(question.getContent())) {
                    dbHelper.updateInfoQuestion(question1);
                } else {
                    dbHelper.updateQuestion(question1);
                }

                Toast.makeText(this, "Update success!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("Update question fail!", e.getMessage());

                Toast.makeText(this, "Update question fail!", Toast.LENGTH_SHORT).show();
            }
        });

//        TODO: event btnCancel click
        btnCancel.setOnClickListener(view -> {
            startActivity(new Intent(this, ManageQuestionActivity.class));
            finish();
        });
    }

    private void mapping() {
        txtContent = findViewById(R.id.edt_content_update);
        txtOption1 = findViewById(R.id.edt_option_1_update);
        txtOption2 = findViewById(R.id.edt_option_2_update);
        txtOption3 = findViewById(R.id.edt_option_3_update);
        txtOption4 = findViewById(R.id.edt_option_4_update);
        atwTopic = findViewById(R.id.auto_topic_update);
        atwAnswer = findViewById(R.id.auto_answer_update);
        btnSave = findViewById(R.id.btn_update_ques);
        btnCancel = findViewById(R.id.btn_cancel_update_ques);

        List<String> listTopic = dbHelper.getNameTopic();
        ArrayAdapter<String> adapterTopic = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listTopic);
        ArrayAdapter<String> adapterAns = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, answers);
        int pos = 0, position = 0;

//        TODO: get position answer old in answer adapter
        for (int i = 0; i < adapterAns.getCount(); i++) {
            if (adapterAns.getItem(i).equals(question.getAnswer())) {
                pos = i;
                break;
            }
        }

//        TODO: get position of topic old in adapter topic
        for (int i = 0; i < adapterTopic.getCount(); i++) {
            if (adapterTopic.getItem(i).equals(dbHelper.getTopicById(question.getIdTopic()).getNameTopic())) {
                position = i;
                break;
            }
        }

        txtContent.setText(question.getContent());
        txtOption1.setText(question.getOption1());
        txtOption2.setText(question.getOption2());
        txtOption3.setText(question.getOption3());
        txtOption4.setText(question.getOption4());
        atwAnswer.setAdapter(adapterAns);
        atwAnswer.setText(adapterAns.getItem(pos), false);
        atwTopic.setAdapter(adapterTopic);
        atwTopic.setText(adapterTopic.getItem(position), false);
    }
}