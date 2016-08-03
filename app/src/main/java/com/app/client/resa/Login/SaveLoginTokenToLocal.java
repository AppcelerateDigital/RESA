package com.app.client.resa.Login;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.app.client.resa.UserInfo.UserLoginInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by wuyifan on 1/07/16.
 */
public class SaveLoginTokenToLocal {

    public void saveToSharedPreferences(Activity activity, UserLoginInfo userInfo)
    {
        SharedPreferences sharedPref = activity.getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user_login_token", userInfo.getUser_login_token());
        editor.putString("user_id", userInfo.getUser_id());
        editor.putString("user_email",userInfo.getUser_email());
        Date currentDate = GregorianCalendar.getInstance().getTime();
        String formattedDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(currentDate);

        editor.putString("last_login_date",formattedDate);
        System.out.println("Date is :"+formattedDate+" | Login Token is :"+userInfo.getUser_login_token());
        editor.commit();
    }
}
