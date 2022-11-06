package com.example.quizapp.model;

public class QuizQuestion {
    private  int idQuiz;
    private int idQuestion;

    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public QuizQuestion(int idQuiz, int idQuestion) {
        this.idQuiz = idQuiz;
        this.idQuestion = idQuestion;
    }
}
