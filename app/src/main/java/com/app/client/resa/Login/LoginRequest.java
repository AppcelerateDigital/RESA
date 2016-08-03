package com.app.client.resa.Login;

import com.app.client.resa.UserInfo.UserLoginInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by wuyifan on 30/06/16.
 */
public class LoginRequest implements Runnable{

    UserLoginInfo userLoginInfo = new UserLoginInfo();
    String user_email;
    String user_password;
    String URL;

    public LoginRequest(String URL,String user_email, String user_password) {
        this.user_email = user_email;
        this.user_password = user_password;
        this.URL = URL;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            SendLoginRequestToServer sendLoginRequestToServer = new SendLoginRequestToServer();
            userLoginInfo = sendLoginRequestToServer.getLoginResponse(URL,user_email,user_password);
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
