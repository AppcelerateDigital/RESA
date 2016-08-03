package com.app.client.resa.UserAnswers;

import com.app.client.resa.Questions.Question;
import com.app.client.resa.Questions.QuestionsInit;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by wuyifan on 24/06/16.
 */
public class UploadAnswersToServer {

    public String uploadAnswerToServerURLResponse(String urlString) throws Exception{
        System.out.println(urlString);
        HttpURLConnection conn = null; //连接对象
        InputStream is = null;
        ArrayList<Question> questions= new ArrayList<Question>();
        String status = "";
        String return_value = "";
        try {
            URL url = new URL(urlString); //URL对象
            conn = (HttpURLConnection)url.openConnection(); //使用URL打开一个链接
            conn.setDoInput(true); //允许输入流，即允许下载
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            int statusCode = conn.getResponseCode();
                 /* 200 represents HTTP OK */
            System.out.println(statusCode);
            if (statusCode == 200) {
                // obtain response data and transfer to string
                is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String str = null;
                StringBuffer buffer = new StringBuffer();
                while((str = br.readLine())!=null){
                    buffer.append(str+"\n");
                }
                is.close();
                br.close();
                // transfer to json object
                String returndata = buffer.toString();
//                System.out.println("return data is "+returndata);
                JSONObject obj = new JSONObject(returndata);
                status= obj.getString("status");
                if(status.equals("success"))
                {
                    return_value = status;
                }
                else
                {
                    return_value = "fail";
                }
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(conn != null){
                conn.disconnect();
            }
        }

        return return_value;
    }
}
