package com.app.client.resa.Login;

import com.app.client.resa.UserInfo.UserLoginInfo;

/**
 * Created by wuyifan on 30/06/16.
 */
public class RegisterRequest implements Runnable{

        UserLoginInfo userLoginInfo = new UserLoginInfo();
        String user_full_name;
        String user_email;
        String user_password;
        String URL;

    public RegisterRequest(String URL,String user_full_name, String user_email, String user_password) {
        this.user_full_name = user_full_name;
        this.user_email = user_email;
        this.user_password = user_password;
        this.URL = URL;
    }

    @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                InsertUserDataToServer insertUserDataToServer = new InsertUserDataToServer();
                userLoginInfo = insertUserDataToServer.getInsertUserURLResponse(URL,user_full_name,user_email,user_password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public UserLoginInfo getUserLoginInfo() {
        return userLoginInfo;
    }

    public void setUserLoginInfo(UserLoginInfo userLoginInfo) {
        this.userLoginInfo = userLoginInfo;
    }
}
