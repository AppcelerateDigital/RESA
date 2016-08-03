package com.app.client.resa.UserInfo;

import com.app.client.resa.Questions.Question;
import com.app.client.resa.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wuyifan on 5/07/16.
 */
public class UserInfoInit {

    public UserProfileInfo getUserInfo(JSONArray jsonArray)
    {
        UserProfileInfo userProfileInfo = new UserProfileInfo();
        try
        {
                String string_info = jsonArray.getString(0);
                JSONObject ob = new JSONObject(string_info);
                userProfileInfo.setJob_capacity(ob.getString("job_capacity"));
                userProfileInfo.setPay_type(ob.getString("pay_type"));
                userProfileInfo.setIs_indigenous(ob.getString("is_indigenous"));
                userProfileInfo.setGender(ob.getString("gender"));
                userProfileInfo.setAge_group(ob.getString("age_group"));
                userProfileInfo.setJob_classification(ob.getString("job_classification"));

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return userProfileInfo;
    }
}
