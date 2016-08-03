package com.app.client.resa.Login;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.client.resa.Config.ConfigFile;
import com.app.client.resa.Main.MainActivity;
import com.app.client.resa.R;
import com.app.client.resa.UserInfo.UserLoginInfo;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private TextView signup;
    private EditText email;
    private EditText password;
    private Button btn_login;
    private CheckBox remember_password;
//    private CheckBox auto_login;
    private SharedPreferences sharedPreferences;
    private UserLoginInfo userLoginInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup = (TextView)findViewById(R.id.btn_signup);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        btn_login = (Button) findViewById(R.id.btn_login);
        remember_password = (CheckBox) findViewById(R.id.checkBox_remember_pass);
//        auto_login = (CheckBox) findViewById(R.id.checkBox_auto_login);
        sharedPreferences = LoginActivity.this.getSharedPreferences("user",Context.MODE_PRIVATE);
//        auto_login.setChecked(true);
        remember_password.setChecked(true);
        final String default_user = sharedPreferences.getString("user_email","");
//        System.out.println("user is :"+default_user);
        email.setText(default_user);
        if(sharedPreferences.getBoolean(default_user,false))
        {
            password.setText(sharedPreferences.getString("user_password",""));
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_email = email.getText().toString();
                String user_password = password.getText().toString();
                String URL = ConfigFile.BASE_URL+ConfigFile.LOGIN_URL;
                System.out.println("user_password : "+user_password);
                System.out.println("user_email : "+user_email);
                userLoginInfo = login_user(URL,user_email,user_password);
                // to generate a list of saved users, need to be implement later  ------------------
                if(userLoginInfo.getStatus()!=null)
                {
                    if(userLoginInfo.getStatus().equals("success"))
                    {
                        SaveLoginTokenToLocal saveLoginTokenToLocal = new SaveLoginTokenToLocal();
                        saveLoginTokenToLocal.saveToSharedPreferences(LoginActivity.this,userLoginInfo);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putBoolean("AUTO_LOGIN_ISCHECK", auto_login.isChecked());
                        editor.putBoolean(default_user,remember_password.isChecked());
                        if(remember_password.isChecked())
                        {
                            editor.putString("user_password",password.getText().toString());
                        }
                        editor.commit();
                        Intent it = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(it);
                    }
                    else
                    {
                        String msg = userLoginInfo.getFail_info();
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.Failed_Login_Info, Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this, Register.class);
                startActivity(it);
            }
        });
    }

    public UserLoginInfo login_user(String URL, String user_email, String user_password)
    {
        LoginRequest loginRequest = new LoginRequest(URL,user_email,user_password);
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        Thread thread = new Thread(loginRequest);
        thread.start();
        try {
            thread.join();
            userLoginInfo = loginRequest.getUserLoginInfo();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return userLoginInfo;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}

