package com.example.quizapp.ui_user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.quizapp.R;
import com.example.quizapp.database.DBHelper;
import com.example.quizapp.model.Account;
import com.example.quizapp.model.History;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.QuizQuestion;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class StartQuizActivity extends AppCompatActivity {
    DBHelper dbHelper = new DBHelper(this);
    List<Question> questionList;
    SharedPreferences sharedPreferences;
    boolean isBack = false;

    private int currentQuestionPosition = 0;

    private int score = 0;
    private int idQuiz = 0;
    private final List<String> userAns = new ArrayList<>();

    // Options
    private AppCompatButton option1, option2, option3, option4;

    // next button
    private AppCompatButton btnNext;

    // back button
    private AppCompatButton btnBack;

    // Total questions and main question TextView
    private TextView twQuestion, twQuestions, twTimer;

    // selectedOption's Value. if user not selected any option yet then it is empty by default
    private String selectedOptionByUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);
        sharedPreferences = getSharedPreferences("acc_user_name.xml", MODE_PRIVATE);

        mapping();

        startTimer();

        addActions();
    }

    private void addActions() {
        option1.setOnClickListener(v -> {

            // check if user has not attempted this question yet
            if (selectedOptionByUser.isEmpty()) {

                selectedOptionByUser = option1.getText().toString();
                userAns.add("option1");

                // change selected AppCompatButton background color and text color
                option1.setBackgroundResource(R.drawable.round_back_red10);
                option1.setTextColor(Color.WHITE);

                // reveal answer
                revealAnswer();
            }
        });

        option2.setOnClickListener(v -> {

            // check if user has not attempted this question yet
            if (selectedOptionByUser.isEmpty()) {

                selectedOptionByUser = option2.getText().toString();

                userAns.add("option2");

                // change selected AppCompatButton background color and text color
                option2.setBackgroundResource(R.drawable.round_back_red10);
                option2.setTextColor(Color.WHITE);

                // reveal answer
                revealAnswer();
            }
        });

        option3.setOnClickListener(v -> {

            // check if user has not attempted this question yet
            if (selectedOptionByUser.isEmpty()) {

                selectedOptionByUser = option3.getText().toString();
                userAns.add("option3");

                // change selected AppCompatButton background color and text color
                option3.setBackgroundResource(R.drawable.round_back_red10);
                option3.setTextColor(Color.WHITE);

                // reveal answer
                revealAnswer();
            }
        });

        option4.setOnClickListener(v -> {

            // check if user has not attempted this question yet
            if (selectedOptionByUser.isEmpty()) {

                selectedOptionByUser = option4.getText().toString();
                userAns.add("option4");

                // change selected AppCompatButton background color and text color
                option4.setBackgroundResource(R.drawable.round_back_red10);
                option4.setTextColor(Color.WHITE);

                // reveal answer
                revealAnswer();
            }
        });

        btnBack.setOnClickListener(v -> {
            // open StartActivity.java
            startActivity(new Intent(getApplicationContext(), SelectTopicActivity.class));
            this.finish(); // finish(destroy) this activity
        });

        btnNext.setOnClickListener(v -> {

            // check if user has not selected any option yet
            if (selectedOptionByUser.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please select an option", Toast.LENGTH_SHORT).show();
            } else {
                changeNextQuestion();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void mapping() {
        twQuestions = findViewById(R.id.tw_questions);
        twQuestion = findViewById(R.id.tw_question);
        twTimer = findViewById(R.id.tw_timer);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        btnNext = findViewById(R.id.btn_next);
        btnBack = findViewById(R.id.btn_back);

        int id = getIntent().getIntExtra("id", 0);
        questionList = dbHelper.getQuestionByIdTopic(id);

        // set current question to TextView along with options from questionsLists ArrayList
        twQuestions.setText((currentQuestionPosition + 1) + "/" + questionList.size());
        twQuestion.setText(questionList.get(currentQuestionPosition).getContent());
        option1.setText(questionList.get(currentQuestionPosition).getOption1());
        option2.setText(questionList.get(currentQuestionPosition).getOption2());
        option3.setText(questionList.get(currentQuestionPosition).getOption3());
        option4.setText(questionList.get(currentQuestionPosition).getOption4());
    }

    private void startTimer() {
        new CountDownTimer(60000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {

                // Used for formatting digit to be in 2 digits only

                if (currentQuestionPosition >= questionList.size()) {
                    cancel();
                }

                NumberFormat f = new DecimalFormat("00");

                long min = (millisUntilFinished / 60000) % 60;

                long sec = (millisUntilFinished / 1000) % 60;

                twTimer.setText(f.format(min) + ":" + f.format(sec));

            }

            // When the task is over it will print 00:00 there

            public void onFinish() {
                AlertDialog.Builder builder = new AlertDialog.Builder(StartQuizActivity.this);
                builder.setTitle("Hết giờ!!!!");
                builder.setMessage("Bài test của bạn đã được ghi nhận. Xem kết quả ngay?");
                builder.setPositiveButton("Có" + score, (dialogInterface, i1) -> {

//                    TODO: insert data to tbl_quiz when over time
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
                    score = getCorrectAnswers() * 10;
                    String currentDateTime = "Test" + sdf.format(new Date());

                    Quiz quiz = new Quiz(0, currentDateTime, score);
                    dbHelper.insertQuiz(quiz);
                    idQuiz = dbHelper.getIdQuiz(currentDateTime);

//                    TODO: insert data to tbl_quiz_question
                    insertToQuizQuestion(questionList, idQuiz);

//                  TODO:  Open result activity when over time
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("correct", getCorrectAnswers());
                    bundle.putSerializable("incorrect", getIncorrectAnswers());
                    bundle.putSerializable("score", score);
                    Intent intent = new Intent(StartQuizActivity.this, ResultActivity.class);
                    intent.putExtra("bundle", bundle);
                    startActivity(intent);
                    cancel();
                    finish();
                });

                builder.setNegativeButton("Không", (dialogInterface, i1) -> {
                    startActivity(new Intent(StartQuizActivity.this, SelectTopicActivity.class));
                    finish();
                });

                builder.show();
            }

        }.start();
    }

    private void revealAnswer() {
        // get answer of current question
        final String getAnswer = questionList.get(currentQuestionPosition).getAnswer();

        // change background color and text color of option which match with answer
        switch (getAnswer) {
            case "option1":
                option1.setBackgroundResource(R.drawable.round_back_green10);
                option1.setTextColor(Color.WHITE);
                break;
            case "option2":
                option2.setBackgroundResource(R.drawable.round_back_green10);
                option2.setTextColor(Color.WHITE);
                break;
            case "option3":
                option3.setBackgroundResource(R.drawable.round_back_green10);
                option3.setTextColor(Color.WHITE);
                break;
            case "option4":
                option4.setBackgroundResource(R.drawable.round_back_green10);
                option4.setTextColor(Color.WHITE);
                break;
        }
    }

    /**
     * Method change question when click button next
     */
    @SuppressLint("SetTextI18n")
    private void changeNextQuestion() {

        // increment currentQuestionPosition by 1 for next question
        currentQuestionPosition++;

        // change next button text to submit if it is last question
        if ((currentQuestionPosition + 1) == questionList.size()) {
            btnNext.setText("Submit Quiz");
        }

        // check if next question is available. else quiz completed
        if (currentQuestionPosition < questionList.size()) {

            // make selectedOptionByUser empty to hold next question's answer
            selectedOptionByUser = "";

            // set normal background color and text color to options
            option1.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option1.setTextColor(Color.parseColor("#1F6BB8"));
            option2.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option2.setTextColor(Color.parseColor("#1F6BB8"));
            option3.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option3.setTextColor(Color.parseColor("#1F6BB8"));
            option4.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option4.setTextColor(Color.parseColor("#1F6BB8"));

            // set current question to TextView along with options from questionsLists ArrayList
            twQuestions.setText((currentQuestionPosition + 1) + "/" + questionList.size());
            twQuestion.setText(questionList.get(currentQuestionPosition).getContent());
            option1.setText(questionList.get(currentQuestionPosition).getOption1());
            option2.setText(questionList.get(currentQuestionPosition).getOption2());
            option3.setText(questionList.get(currentQuestionPosition).getOption3());
            option4.setText(questionList.get(currentQuestionPosition).getOption4());
        } else {
           openResult();
        }
    }

    private void openResult(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMMyyyyHH:mm:ss Z");

        // on below line we are creating a variable
        // for current date and time and calling a simple date format in it.
        score = getCorrectAnswers() * 10;
        String currentDateTime = "Test" + sdf.format(new Date());

//            TODO: insert quiz to tbl_quiz
        Quiz quiz = new Quiz(0, currentDateTime, score);
        dbHelper.insertQuiz(quiz);
        idQuiz = dbHelper.getIdQuiz(currentDateTime);
        String userName = sharedPreferences.getString("userName", null);
        Account acc = dbHelper.getAccount(userName);
        int idUser = acc.getIdAcc();
        History history = new History(0, idUser, idQuiz);
        dbHelper.insertHistory(history);

//            TODO: insert data to tbl_quiz_question
        insertToQuizQuestion(questionList, idQuiz);

        // Open result activity along with correct and incorrect answers
        Bundle bundle = new Bundle();
        bundle.putInt("correct", getCorrectAnswers());
        bundle.putInt("incorrect", getIncorrectAnswers());
        bundle.putInt("score", score);
        Intent intent = new Intent(StartQuizActivity.this, ResultActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

        // finish(destroy) this activity
        finish();
    }


    /**
     * Method get number of correct answer
     *
     * @return number of correct answer
     */
    private int getCorrectAnswers() {
        int correctAnswers = 0;

        for (int i = 0; i < questionList.size(); i++) {
            final String getAnswer = questionList.get(i).getAnswer();

            // compare user selected option with original answer
            if (userAns.get(i).equals(getAnswer)) {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    /**
     * Method get number incorrect answer
     *
     * @return number incorrect answer
     */
    private int getIncorrectAnswers() {

        int incorrectAnswers = 0;

        for (int i = 0; i < questionList.size(); i++) {
            final String getAnswer = questionList.get(i).getAnswer();
//             compare user selected option with original answer
            if (!selectedOptionByUser.equals(getAnswer)) {
                incorrectAnswers++;
            }
        }
        return incorrectAnswers;
    }

    /**
     * Method insert data to tbl_quiz_question
     *
     * @param questions list question
     * @param idQuiz    id quiz
     */
    private void insertToQuizQuestion(List<Question> questions, int idQuiz) {
        for (int i = 0; i < questions.size(); i++) {
            int idQuestion = questions.get(i).getIdQuestion();
            QuizQuestion quizQuestion = new QuizQuestion(idQuiz, idQuestion);
            dbHelper.insertQuizQuestion(quizQuestion);
        }
    }
}