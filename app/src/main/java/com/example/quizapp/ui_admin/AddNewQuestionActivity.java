package com.example.quizapp.ui_admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.R;
import com.example.quizapp.database.DBHelper;
import com.example.quizapp.model.Question;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

public class AddNewQuestionActivity extends AppCompatActivity {

    TextInputEditText txtContent, txtOption1, txtOption2, txtOption3, txtOption4;
    AutoCompleteTextView atwTopic, atwAnswer;
    MaterialButton btnSave, btnCancel, btnBack;
    private final String[] answers = {"option1", "option2", "option3", "option4"};
    DBHelper dbHelper = new DBHelper(this);
    private String tpName, ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_question);
        Objects.requireNonNull(this.getSupportActionBar()).hide();

        mapping();
        addActions();
    }

    private void addActions() {
        btnSave.setOnClickListener(view -> {
            String content = Objects.requireNonNull(txtContent.getText()).toString();
            String op1 = Objects.requireNonNull(txtOption1.getText()).toString();
            String op2 = Objects.requireNonNull(txtOption2.getText()).toString();
            String op3 = Objects.requireNonNull(txtOption3.getText()).toString();
            String op4 = Objects.requireNonNull(txtOption4.getText()).toString();
            int idTopic = dbHelper.getIdTopic(tpName);

            if (!content.isEmpty() && !op1.isEmpty() && !op2.isEmpty() && !op3.isEmpty() && !op4.isEmpty()) {
                if (dbHelper.getQuestion(content) == null) {
                    Question question = new Question(0, idTopic, content, op1, op2, op3, op4, ans);

                    boolean res = dbHelper.addNewQuestion(question);

                    String msg = (res) ? "Thêm mới thành công!" : "Thêm mới thất bại!";

                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Câu hỏi đã tồn tại!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            }
        });

        atwTopic.setOnItemClickListener((adapterView, view, i, l) -> tpName = adapterView.getItemAtPosition(i).toString());

        atwAnswer.setOnItemClickListener((adapterView, view, i, l) -> ans = adapterView.getItemAtPosition(i).toString());

        btnCancel.setOnClickListener(view -> {
            startActivity(new Intent(AddNewQuestionActivity.this, ManageQuestionActivity.class));
            finish();
        });

        btnBack.setOnClickListener(view -> startActivity(new Intent(this, ManageQuestionActivity.class)));
    }

    private void mapping() {
        txtContent = findViewById(R.id.edt_content);
        txtOption1 = findViewById(R.id.edt_option_1);
        txtOption2 = findViewById(R.id.edt_option_2);
        txtOption3 = findViewById(R.id.edt_option_3);
        txtOption4 = findViewById(R.id.edt_option_4);
        atwTopic = findViewById(R.id.auto_topic);
        atwAnswer = findViewById(R.id.auto_answer);
        btnBack = findViewById(R.id.btn_back);
        btnSave = findViewById(R.id.btn_add_ques);
        btnCancel = findViewById(R.id.btn_cancel);


//        TODO: create data for AutoCompleteTextView
        List<String> listTopic;
        listTopic = dbHelper.getNameTopic();
        ArrayAdapter<String> adapterTopic = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listTopic);
        ArrayAdapter<String> adapterAns = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, answers);

        atwTopic.setThreshold(1);
        atwTopic.setAdapter(adapterTopic);

        atwAnswer.setThreshold(1);//will start working from first character
        atwAnswer.setAdapter(adapterAns);//setting the adapter data into the AutoCompleteTextView
    }
}