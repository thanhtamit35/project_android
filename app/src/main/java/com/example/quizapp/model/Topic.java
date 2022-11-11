package com.example.quizapp.model;

import java.io.Serializable;
import java.util.Arrays;

public class Topic implements Serializable {
    int idTopic;
    String nameTopic;
    byte[] imageTopic;

    public Topic() {
    }

    public Topic(int idTopic, String nameTopic, byte[] imageTopic) {
        this.idTopic = idTopic;
        this.nameTopic = nameTopic;
        this.imageTopic = imageTopic;
    }

    public int getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(int idTopic) {
        this.idTopic = idTopic;
    }

    public String getNameTopic() {
        return nameTopic;
    }

    public void setNameTopic(String nameTopic) {
        this.nameTopic = nameTopic;
    }

    public byte[] getImageTopic() {
        return imageTopic;
    }

    public void setImageTopic(byte[] imageTopic) {
        this.imageTopic = imageTopic;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "idTopic=" + idTopic +
                ", nameTopic='" + nameTopic + '\'' +
                ", imageTopic=" + Arrays.toString(imageTopic) +
                '}';
    }
}
