package com.example.quizapp.ui_user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.quizapp.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;

public class ResultActivity extends AppCompatActivity {
    MaterialTextView twScore, twCorrect, twIncorrect;
    AppCompatButton btnContinue;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Objects.requireNonNull(this.getSupportActionBar()).hide();

        mapping();
        addActions();
    }

    private void addActions() {
        btnContinue.setOnClickListener(view -> {
            startActivity(new Intent(this, SelectTopicActivity.class));
            finish();
        });
    }

    @SuppressLint("SetTextI18n")
    private void mapping() {
        twScore = findViewById(R.id.tw_score);
        twCorrect = findViewById(R.id.tw_correct);
        twIncorrect = findViewById(R.id.tw_incorrect);
        btnContinue = findViewById(R.id.btn_continue);
        img = findViewById(R.id.img_res);

        Bundle bundle = getIntent().getExtras();
        int score = bundle.getInt("score");
        int correct = bundle.getInt("correct");
        int incorrect = bundle.getInt("incorrect");

        twScore.setText("Score: " + score);
        twCorrect.setText("Correct: " + correct);
        twIncorrect.setText("Incorrect: " + incorrect);

        if (correct < incorrect) {
            img.setImageResource(R.drawable.sad);
        } else if (correct == incorrect) {
            img.setImageResource(R.drawable.smile);
        } else {
            img.setImageResource(R.drawable.star);
        }
    }
}