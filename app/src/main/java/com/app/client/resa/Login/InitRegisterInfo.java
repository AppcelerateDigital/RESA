package com.app.client.resa.Login;

import com.app.client.resa.UserInfo.UserLoginInfo;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by wuyifan on 1/07/16.
 */
public class InitRegisterInfo {

    public UserLoginInfo initUserInfo(JSONArray jsonArray)
    {
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        try
        {
                String current_question = jsonArray.getString(0);
                JSONObject ob = new JSONObject(current_question);
                userLoginInfo.setUser_id(ob.getString("user_id"));
                userLoginInfo.setUser_name(ob.getString("user_name"));
                userLoginInfo.setUser_email(ob.getString("user_email"));
                userLoginInfo.setUser_login_token(ob.getString("user_login_token"));
                userLoginInfo.setStatus("success");

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return userLoginInfo;
    }
}
