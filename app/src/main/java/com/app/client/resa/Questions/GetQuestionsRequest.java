package com.app.client.resa.Questions;

import com.app.client.resa.Config.ConfigFile;

import java.util.ArrayList;

/**
 * Created by wuyifan on 22/06/16.
 */
public class GetQuestionsRequest implements Runnable{
        private ArrayList<Question> questions= new ArrayList<Question>();
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                LoadquestionFromServer loadquestionFromServer = new LoadquestionFromServer();
                System.out.println("Loading from server");
                questions = loadquestionFromServer.getQuestionsURLResponse(ConfigFile.BASE_URL+ConfigFile.GET_QUESTIONS_URL);

            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
