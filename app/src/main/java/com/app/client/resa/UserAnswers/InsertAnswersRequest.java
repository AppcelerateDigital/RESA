package com.app.client.resa.UserAnswers;

import com.app.client.resa.Config.ConfigFile;
import com.app.client.resa.Main.Fragments.QuestionFragment;
import com.app.client.resa.Questions.LoadquestionFromServer;

/**
 * Created by wuyifan on 24/06/16.
 */
public class InsertAnswersRequest implements Runnable {
    String is_insert;
    String question_id;
    String question_category_id;
    String user_id;
    String answer_id;
    String result_id;

    public InsertAnswersRequest(String question_id, String question_category_id, String user_id, String answer_id,String result_id) {
        this.question_id = question_id;
        this.question_category_id = question_category_id;
        this.user_id = user_id;
        this.answer_id = answer_id;
        this.result_id= result_id;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            UploadAnswersToServer uploadAnswersToServer = new UploadAnswersToServer();
            System.out.println("Sending Request . . . . . .");
            System.out.println("question_id is :"+question_id);
            System.out.println("answer_id is:"+answer_id);
            System.out.println("user_id is:"+user_id);
            System.out.println("question_category_id is:"+question_category_id);
            System.out.println("result_id is:"+result_id);
            is_insert = uploadAnswersToServer.uploadAnswerToServerURLResponse(ConfigFile.BASE_URL+ConfigFile.UPLOAD_USER_ANSWER_URL+"question_id="+question_id+"&question_category_id="+question_category_id+"&user_id="+user_id+"&answer_id="+answer_id+"&result_id="+result_id);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getIs_insert() {
        return is_insert;
    }

    public void setIs_insert(String is_insert) {
        this.is_insert = is_insert;
    }

    public String getResult_id() {
        return result_id;
    }

    public void setResult_id(String result_id) {
        this.result_id = result_id;
    }
}
