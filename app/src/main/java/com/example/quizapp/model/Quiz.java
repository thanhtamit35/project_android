package com.example.quizapp.model;

public class Quiz {
    private int idQuiz;
    private String nameQuiz;
    int score;

    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }

    public String getNameQuiz() {
        return nameQuiz;
    }

    public void setNameQuiz(String nameQuiz) {
        this.nameQuiz = nameQuiz;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Quiz(int idQuiz, String nameQuiz, int score) {
        this.idQuiz = idQuiz;
        this.nameQuiz = nameQuiz;
        this.score = score;
    }
}
