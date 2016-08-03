package com.app.client.resa.Questions;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wuyifan on 21/06/16.
 */
public class QuestionsInit {

    public ArrayList<Question> getQuestionsList(JSONArray jsonArray)
    {
        ArrayList<Question> questions = new ArrayList<Question>();
        try
        {
            for(int i=0;i<jsonArray.length();i++)
            {
                Question question = new Question();
                String current_question = jsonArray.getString(i);
                JSONObject ob = new JSONObject(current_question);
                question.setQuestion_id(ob.getString("question_id"));
                question.setQuestion_category(ob.getString("question_category_id"));
                question.setQuestion_detail(ob.getString("question_detail"));
                questions.add(question);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return questions;
    }

}
