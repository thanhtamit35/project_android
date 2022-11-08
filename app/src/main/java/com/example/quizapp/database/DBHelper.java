package com.example.quizapp.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.quizapp.model.Account;
import com.example.quizapp.model.History;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.QuizQuestion;
import com.example.quizapp.model.Topic;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_app.db";
    private static final int VERSION = 1;
    private static final String CREATE_TABLE_ROLE = "CREATE TABLE IF NOT EXISTS tbl_role("
            + "idRole INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "nameRole TEXT NOT NULL UNIQUE)";
    private static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE IF NOT EXISTS tbl_account("
            + "idAcc INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "userName TEXT NOT NULL UNIQUE,"
            + "password TEXT NOT NULL, "
            + "idRole INTEGER NOT NULL, "
            + "fullName TEXT NOT NULL, "
            + "FOREIGN KEY(\"idRole\") REFERENCES tbl_role(\"idRole\"))";
    private static final String CREATE_TABLE_TOPIC = "CREATE TABLE IF NOT EXISTS tbl_topic ("
            + "idTopic INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "nameTopic TEXT NOT NULL UNIQUE, "
            + "imageTopic BLOB NOT NULL )";
    private static final String CREATE_TABLE_QUESTION = "CREATE TABLE IF NOT EXISTS tbl_question (\n" +
            "\t\"idQuestion\"\tINTEGER PRIMARY KEY AUTOINCREMENT," +
            "\t\"idTopic\"\tINTEGER NOT NULL,\n" +
            "\t\"content\"\tTEXT NOT NULL UNIQUE,\n" +
            "\t\"option1\"\tTEXT NOT NULL,\n" +
            "\t\"option2\"\tTEXT NOT NULL,\n" +
            "\t\"option3\"\tTEXT NOT NULL,\n" +
            "\t\"option4\"\tTEXT NOT NULL,\n" +
            "\t\"answer\"\tTEXT NOT NULL,\n" +
            "\tCONSTRAINT \"fk_id_topic\" FOREIGN KEY(\"idTopic\") REFERENCES tbl_topic(\"idTopic\")\n" +
            ")";
    private static final String CREATE_TABLE_QUIZ = "CREATE TABLE IF NOT EXISTS tbl_quiz (\n" +
            "\t\"idQuiz\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t\"nameQuiz\"\tTEXT NOT NULL UNIQUE,\n" +
            "\t\"score\"\tINTEGER NOT NULL\n" +
            ")";
    private static final String CREATE_TABLE_QUIZ_QUESTION = "CREATE TABLE IF NOT EXISTS tbl_quiz_question (\n" +
            "\t\"idQuiz\"\tINTEGER NOT NULL,\n" +
            "\t\"idQuestion\"\tINTEGER NOT NULL,\n" +
            "\tFOREIGN KEY(\"idQuestion\") REFERENCES tbl_question(\"idQuestion\"),\n" +
            "\tFOREIGN KEY(\"idQuiz\") REFERENCES tbl_quiz(\"idQuiz\")\n" +
            ")";
    private static final String CREATE_TABLE_HISTORY = "CREATE TABLE IF NOT EXISTS tbl_history (\n" +
            "\t\"idHistory\"\tINTEGER NOT NULL,\n" +
            "\t\"idAcc\"\tINTEGER NOT NULL,\n" +
            "\t\"idQuiz\"\tINTEGER NOT NULL,\n" +
            "\tPRIMARY KEY(\"idHistory\"),\n" +
            "\tFOREIGN KEY(\"idQuiz\") REFERENCES tbl_quiz(\"idQuiz\"),\n" +
            "\tFOREIGN KEY(\"idAcc\") REFERENCES tbl_account(\"idAcc\")\n" +
            ")";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public void execsSQL(String sql) {
        getWritableDatabase().execSQL(sql);
    }

    public Cursor selectSQL(String sql) {
        return getReadableDatabase().rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ROLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNT);
        sqLiteDatabase.execSQL(CREATE_TABLE_TOPIC);
        sqLiteDatabase.execSQL(CREATE_TABLE_QUESTION);
        sqLiteDatabase.execSQL(CREATE_TABLE_QUIZ);
        sqLiteDatabase.execSQL(CREATE_TABLE_QUIZ_QUESTION);
        sqLiteDatabase.execSQL(CREATE_TABLE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_history");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_account");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_role");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_quiz_question");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_quiz");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_question");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_topic");
        onCreate(sqLiteDatabase);
    }

    /**
     * Method get account from database
     *
     * @param userName - user name
     * @return account
     */
    public Account getAccount(String userName) {
        String sql = "SELECT * FROM tbl_account WHERE userName = '" + userName + "';";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        Account acc = null;

        while (cursor.moveToNext()) {
            int idAcc = cursor.getInt(0);
            String user = cursor.getString(1);
            String pass = cursor.getString(2);
            int idRole = cursor.getInt(3);
            String fullName = cursor.getString(4);

            acc = new Account(idAcc, user, pass, idRole, fullName);
        }

        return acc;
    }

    /**
     * Method insert account to database
     *
     * @param acc account
     */
    public void insertAccount(Account acc) {
        try {
            String insert = "INSERT INTO tbl_account VALUES(NULL, '" + acc.getUserName() + "', '" + acc.getPassword() + "', 2, '" + acc.getFullName() + "')";

            execsSQL(insert);
        } catch (Exception e) {
            Log.e("Insert fail!", e.getMessage());
        }
    }

    public boolean updateAcc(Account acc) {
        try {
            String insert = "UPDATE tbl_account\n" +
                    "SET fullName = '" + acc.getFullName() + "',\n" +
                    "password = '" + acc.getPassword() + "'\n" +
                    "WHERE idAcc = " + acc.getIdAcc() + ";";

            execsSQL(insert);

            return true;
        } catch (Exception e) {
            Log.e("Insert fail!", e.getMessage());
            return false;
        }
    }


    public Topic getTopic(String topicName) {
        String sql = "SELECT * FROM tbl_topic WHERE nameTopic = '" + topicName + "'";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        Topic topic = null;

        while (cursor.moveToNext()) {
            int idTopic = cursor.getInt(0);
            String nameTopic = cursor.getString(1);
            byte[] imageTopic = cursor.getBlob(2);

            topic = new Topic(idTopic, nameTopic, imageTopic);
        }

        return topic;
    }

    public int getIdTopic(String nameTopic) {
        int id = 0;

        String sql = "SELECT * FROM tbl_topic WHERE nameTopic = '" + nameTopic + "'";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        Topic topic = null;

        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }

        return id;
    }

    public List<Topic> getAllTopic() {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT * FROM tbl_topic";

        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        while (cursor.moveToNext()) {
            int idTopic = cursor.getInt(0);
            String nameTopic = cursor.getString(1);
            byte[] imageTopic = cursor.getBlob(2);

            Topic topic = new Topic(idTopic, nameTopic, imageTopic);
            topics.add(topic);
        }

        return topics;
    }

    /**
     * Get topic by id
     *
     * @param id
     * @return
     */
    public Topic getTopicById(int id) {
        String sql = "SELECT * FROM tbl_topic WHERE idTopic = " + id;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        Topic topic = null;

        while (cursor.moveToNext()) {
            int idTopic = cursor.getInt(0);
            String nameTopic = cursor.getString(1);
            byte[] imageTopic = cursor.getBlob(2);

            topic = new Topic(idTopic, nameTopic, imageTopic);
        }

        return topic;
    }

    /**
     * Get all question by id topic
     *
     * @param id id topic
     * @return List question by id topic
     */
    public List<Question> getQuestionByIdTopic(int id) {
        // ORDER BY random() LIMIT 5;
        String sql = "SELECT * FROM tbl_question WHERE idTopic = " + id;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        List<Question> questions = new ArrayList<>();

        while (cursor.moveToNext()) {
            int idQuestion = cursor.getInt(0);
            String contentQues = cursor.getString(1);
            int idTopic = cursor.getInt(2);
            String option1 = cursor.getString(3);
            String option2 = cursor.getString(4);
            String option3 = cursor.getString(5);
            String option4 = cursor.getString(6);
            String ans = cursor.getString(7);

            Question question = new Question(idQuestion,  idTopic,contentQues, option1, option2, option3, option4, ans);
            questions.add(question);
        }

        return questions;
    }

    /**
     * Get all name of topic in tbl_topic
     *
     * @return list name topic
     */
    public List<String> getNameTopic() {
        String sql = "SELECT nameTopic FROM tbl_topic";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        List<String> topics = new ArrayList<>();

        while (cursor.moveToNext()) {
            String nameTopic = cursor.getString(0);
            topics.add(nameTopic);
        }

        return topics;
    }

    /**
     * Insert data to tbl_topic
     *
     * @param nameTopic  name of topic
     * @param imageBytes byte array of image topic
     */
    public void insertTopic(String nameTopic, byte[] imageBytes) {
        ContentValues cv = new ContentValues();
        cv.put("nameTopic", nameTopic);
        cv.put("imageTopic", imageBytes);
        getWritableDatabase().insert("tbl_topic", null, cv);
    }

    public byte[] retrieveImageFromDB() {
        Cursor cursor = getReadableDatabase().query(true, "tbl_topic",
                new String[]{"imageTopic",}, null,
                null, null,
                null, "id" + "DESC", "1");

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") byte[] blob = cursor.getBlob(cursor.getColumnIndex("imageTopic"));
            cursor.close();
            return blob;
        }
        return null;
    }

    public Question getQuestion(String content) {
        String sql = "SELECT * FROM tbl_question WHERE content = '" + content + "'";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        Question question = null;

        while (cursor.moveToNext()) {
            int idQuestion = cursor.getInt(0);
            String contentQues = cursor.getString(1);
            int idTopic = cursor.getInt(2);
            String option1 = cursor.getString(3);
            String option2 = cursor.getString(4);
            String option3 = cursor.getString(5);
            String option4 = cursor.getString(6);
            String ans = cursor.getString(7);

            question = new Question(idQuestion,  idTopic,contentQues, option1, option2, option3, option4, ans);
        }

        return question;
    }

    /**
     * Method insert new question to tbl_question
     *
     * @param question - object Question
     * @return true if insert success, false if insert fail
     */
    public boolean addNewQuestion(Question question) {
        try {
            String insert = "INSERT INTO tbl_question VALUES(NULL, " +
                    "" + question.getIdTopic() + ", " +
                    "'" + question.getContent() + "', " +
                    "'" + question.getOption1() + "', " +
                    "'" + question.getOption2() + "', " +
                    "'" + question.getOption3() + "', " +
                    "'" + question.getOption4() + "', " +
                    "'" + question.getAnswer() + "')";

            execsSQL(insert);
            return true;
        } catch (Exception e) {
            Log.e("Insert fail!", e.getMessage());
            return false;
        }
    }

    /**
     * Method get id quiz by nameQuiz
     *
     * @param nameQuiz - name of quiz
     * @return id of quiz
     */
    public int getIdQuiz(String nameQuiz) {
        int id = 0;

        String sql = "SELECT idQuiz FROM tbl_quiz WHERE nameQuiz = '" + nameQuiz + "'";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }

        return id;
    }

    public String getNameQuiz(int id) {
        String nameQuiz = "";

        String sql = "SELECT namQuiz FROM tbl_quiz WHERE idQuiz =" + id;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        while (cursor.moveToNext()) {
            nameQuiz = cursor.getString(0);
        }

        return nameQuiz;
    }

    public void insertQuiz(Quiz quiz) {
        try {
            String insert = "INSERT INTO tbl_quiz VALUES(NULL, '" + quiz.getNameQuiz() + "', " + quiz.getScore() + ")";

            execsSQL(insert);
        } catch (Exception e) {
            Log.e("Insert fail!", e.getMessage());
        }
    }

    /**
     * Method get all record from table tbl_question
     *
     * @return list question
     */
    public List<Question> getAllQuestion() {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM tbl_question";

        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int idTopic = cursor.getInt(1);
            String content = cursor.getString(2);
            String option1 = cursor.getString(3);
            String option2 = cursor.getString(4);
            String option3 = cursor.getString(5);
            String option4 = cursor.getString(6);
            String answer = cursor.getString(7);

            Question question = new Question(id,  idTopic, content,option1, option2, option3, option4, answer);
            questions.add(question);
        }

        return questions;
    }

    /**
     * Method insert data to tbl_quiz_question
     *
     * @param quizQuestion - Object QuizQuestion
     */
    public void insertQuizQuestion(QuizQuestion quizQuestion) {
        ContentValues cv = new ContentValues();
        cv.put("idQuiz", quizQuestion.getIdQuiz());
        cv.put("idQuestion", quizQuestion.getIdQuestion());
        getWritableDatabase().insert("tbl_quiz_question", null, cv);
    }

    /**
     * Method insert data to tbl_history
     *
     * @param history - object History
     */
    public void insertHistory(History history) {
        try {
            String insert = "INSERT INTO tbl_history VALUES(NULL, " + history.getIdAcc() + ", " + history.getIdQuiz() + ")";

            execsSQL(insert);
        } catch (Exception e) {
            Log.e("Insert fail!", e.getMessage());
        }
    }

    /**
     * Method get all result test by userName
     *
     * @param userName - userName account of user
     * @return list result
     */
    public List<Quiz> getResultByUser(String userName) {
        String sql = "SELECT tbl_history.idQuiz, tbl_quiz.nameQuiz, tbl_quiz.score\n" +
                "FROM tbl_history\n" +
                "INNER JOIN tbl_account ON tbl_account.userName = '" + userName + "'\n" +
                "INNER JOIN tbl_quiz ON tbl_quiz.idQuiz = tbl_history.idQuiz\n" +
                "ORDER BY tbl_quiz.score DESC";

        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        List<Quiz> quizzes = new ArrayList<>();

        while (cursor.moveToNext()) {
            int idQuiz = cursor.getInt(0);
            String nameQuiz = cursor.getString(1);
            int score = cursor.getInt(2);

            Quiz quiz = new Quiz(idQuiz, nameQuiz, score);
            quizzes.add(quiz);
        }

        return quizzes;
    }

    public boolean deleteHistory(int idAcc) {
        try {
            String sql = "DELETE FROM tbl_history WHERE tbl_history.idAcc = " + idAcc + ";\n";

            execsSQL(sql);

            return true;
        } catch (Exception e) {
            Log.e("Delete fail!", e.getMessage());
            return false;
        }
    }

    public boolean deleteQuizQuestion(int idQuiz) {
        try {
            String sql = "DELETE FROM tbl_quiz_question WHERE tbl_quiz_question.idQuiz = " + idQuiz + ";\n";

            execsSQL(sql);

            return true;
        } catch (Exception e) {
            Log.e("Delete fail!", e.getMessage());
            return false;
        }
    }

    public boolean deleteQuiz(int idQuiz) {
        try {
            String sql = "DELETE FROM tbl_quiz WHERE tbl_quiz.idQuiz = " + idQuiz + ";\n";

            execsSQL(sql);

            return true;
        } catch (Exception e) {
            Log.e("Delete fail!", e.getMessage());
            return false;
        }
    }
}
