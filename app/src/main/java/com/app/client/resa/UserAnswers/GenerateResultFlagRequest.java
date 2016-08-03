package com.app.client.resa.UserAnswers;

import com.app.client.resa.Config.ConfigFile;

/**
 * Created by wuyifan on 27/06/16.
 */
public class GenerateResultFlagRequest implements Runnable{

    private String insert_id;
    private String user_id;

    public GenerateResultFlagRequest(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            GenerateResultFlag generateResultFlag = new GenerateResultFlag();

            insert_id = generateResultFlag.generateResultFlagURLResponse(ConfigFile.BASE_URL+ConfigFile.GENERATE_INSERT_RESULT_URL+"user_id="+user_id);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getInsert_id() {
        return insert_id;
    }

    public void setInsert_id(String insert_id) {
        this.insert_id = insert_id;
    }
}
