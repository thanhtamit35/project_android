package com.example.quizapp.model;

public class Account {
    private int idAcc;
    private String userName;
    private String password;
    private int idRole;
    private String fullName;

    public int getIdAcc() {
        return idAcc;
    }

    public void setIdAcc(int idAcc) {
        this.idAcc = idAcc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Account() {
    }

    public Account(int idAcc, String userName, String password, int idRole, String fullName) {
        this.idAcc = idAcc;
        this.userName = userName;
        this.password = password;
        this.idRole = idRole;
        this.fullName = fullName;
    }
}