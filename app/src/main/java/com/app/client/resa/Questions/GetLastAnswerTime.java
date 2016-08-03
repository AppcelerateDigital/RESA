package com.app.client.resa.Questions;

import com.app.client.resa.Config.ConfigFile;

/**
 * Created by wuyifan on 27/06/16.
 */
public class GetLastAnswerTime implements Runnable{

    String last_answer_time ;
    //hardcode ...
    String user_id="";

    public GetLastAnswerTime(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            System.out.println("user_id is : " +user_id);
            GetLastAnswerTimeRequest lastAnswerTimeRequest = new GetLastAnswerTimeRequest();
            System.out.println("Loading from server :"+ConfigFile.BASE_URL+ConfigFile.GET_LAST_ANSWET_TIME+"user_id="+user_id);
            last_answer_time = lastAnswerTimeRequest.getLastAnswerTimeURLResponse(ConfigFile.BASE_URL+ConfigFile.GET_LAST_ANSWET_TIME+"user_id="+user_id);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getLast_answer_time() {
        return last_answer_time;
    }

    public void setLast_answer_time(String last_answer_time) {
        this.last_answer_time = last_answer_time;
    }
}
