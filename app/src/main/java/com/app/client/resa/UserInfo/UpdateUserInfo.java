package com.app.client.resa.UserInfo;

import com.app.client.resa.Login.InitRegisterInfo;

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
public class UpdateUserInfo {

    public String updateInfoResponse(String urlString, UserProfileInfo userProfileInfo) throws Exception {

        HttpURLConnection conn = null;
        InputStream is = null;
        String resultData = "";
        StringBuilder tokenUri = new StringBuilder("&job_capacity=");
        tokenUri.append(URLEncoder.encode(userProfileInfo.getJob_capacity(), "UTF-8"));
        tokenUri.append("&pay_type=");
        tokenUri.append(URLEncoder.encode(userProfileInfo.getPay_type(), "UTF-8"));
        tokenUri.append("&is_indigenous=");
        tokenUri.append(URLEncoder.encode(userProfileInfo.getIs_indigenous(), "UTF-8"));
        tokenUri.append("&gender=");
        tokenUri.append(URLEncoder.encode(userProfileInfo.getGender(), "UTF-8"));
        tokenUri.append("&age_group=");
        tokenUri.append(URLEncoder.encode(userProfileInfo.getAge_group(), "UTF-8"));
        tokenUri.append("&job_classification=");
        tokenUri.append(URLEncoder.encode(userProfileInfo.getJob_classification(), "UTF-8"));
        tokenUri.append("&user_id=");
        tokenUri.append(URLEncoder.encode(userProfileInfo.getUser_id(), "UTF-8"));
        tokenUri.append("&user_login_token=");
        tokenUri.append(URLEncoder.encode(userProfileInfo.getUser_login_token(), "UTF-8"));
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
        return status;
    }

}
