package com.example.quizapp.model;

public class History {
    private int idHistory;
    private int idAcc;
    private int idQuiz;

    public History(int idHistory, int idAcc, int idQuiz) {
        this.idHistory = idHistory;
        this.idAcc = idAcc;
        this.idQuiz = idQuiz;
    }

    public int getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(int idHistory) {
        this.idHistory = idHistory;
    }

    public int getIdAcc() {
        return idAcc;
    }

    public void setIdAcc(int idAcc) {
        this.idAcc = idAcc;
    }

    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }
}
