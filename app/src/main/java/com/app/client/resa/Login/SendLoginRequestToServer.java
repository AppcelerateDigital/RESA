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
 * Created by wuyifan on 1/07/16.
 */
public class SendLoginRequestToServer {

    public UserLoginInfo getLoginResponse(String urlString, String email_address, String pass) throws Exception {
        System.out.println("URL is .....:"+urlString);
        HttpURLConnection conn = null;
        InputStream is = null;
        String resultData = "";
        StringBuilder tokenUri = new StringBuilder("user_email=");
        tokenUri.append(URLEncoder.encode(email_address, "UTF-8"));
        tokenUri.append("&user_pass=");
        tokenUri.append(URLEncoder.encode(pass, "UTF-8"));
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        String status = "";

        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(3000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept", "application/json");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());
            outputStreamWriter.write(tokenUri.toString());
            outputStreamWriter.flush();
            int statusCode = conn.getResponseCode();
        /* 200 represents HTTP OK */
            System.out.println("statusCode is :"+statusCode);
            if (statusCode == 200) {
                is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String str = null;
                StringBuffer buffer = new StringBuffer();
                while ((str = br.readLine()) != null) {
                    buffer.append(str);
                }
                is.close();
                br.close();
                resultData = buffer.toString();
                System.out.println("return data is "+resultData);
                JSONObject obj = new JSONObject(resultData);
                status= obj.getString("status");
                if(status.equals("success"))
                {
                    JSONArray userInfo = new JSONArray();
                    userInfo = obj.getJSONArray("user_data");
                    InitRegisterInfo inituserInfo = new InitRegisterInfo();
                    userLoginInfo = inituserInfo.initUserInfo(userInfo);

                }
                else
                {
                    userLoginInfo.setStatus("fail");
                    userLoginInfo.setFail_info(obj.getString("user_data"));
                }


            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return userLoginInfo;
    }
}
