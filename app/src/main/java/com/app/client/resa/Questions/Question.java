package com.app.client.resa.Questions;


import java.util.ArrayList;

/**
 * Created by wuyifan on 3/06/16.
 */
public class Question {

    private String question_id;
    private String question_category_id;
    private String question_detail;
    public Question() {
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_category() {
        return question_category_id;
    }

    public void setQuestion_category(String question_category) {
        this.question_category_id = question_category;
    }

    public String getQuestion_detail() {
        return question_detail;
    }

    public void setQuestion_detail(String question_detail) {
        this.question_detail = question_detail;
    }
}
