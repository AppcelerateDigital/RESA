package com.app.client.resa.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.client.resa.Config.ConfigFile;
import com.app.client.resa.Main.MainActivity;
import com.app.client.resa.R;
import com.app.client.resa.UserInfo.UserLoginInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private TextView signin;
    private EditText email;
    private EditText password;
    private EditText fullname;
    private Button createbtn;
    private UserLoginInfo userLoginInfo;
    private EditText confirm_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signin = (TextView) findViewById(R.id.btn_back_to_sign_in);
        createbtn = (Button) findViewById(R.id.btn_register);
        fullname = (EditText) findViewById(R.id.edt_full_name);
        email = (EditText) findViewById(R.id.edt_register_email);
        password = (EditText) findViewById(R.id.edt_register_password);
        confirm_pass = (EditText) findViewById(R.id.edt_register_password_check);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(Register.this, LoginActivity.class);
                startActivity(it);
            }
        });
        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterInfo registerInfo = getRegisterInfo();
                if(registerInfo.getUser_name()!=null&&registerInfo.getUser_email()!=null&&registerInfo.getUser_password()!=null&&registerInfo.getUser_confirm_password()!=null)
                {

                    if(isEmail(registerInfo.getUser_email()))
                    {

                        if(registerInfo.getUser_password().equals(registerInfo.getUser_confirm_password()))
                        {
                            String URL = ConfigFile.BASE_URL+ConfigFile.REGISTER_URL;
                            userLoginInfo = insert_user(URL,registerInfo.getUser_name(),registerInfo.getUser_email(),registerInfo.getUser_password());

                            if(userLoginInfo!=null&&userLoginInfo.getStatus()!=null)
                            {
                                if(userLoginInfo.getStatus().equals("fail"))
                                {
                                    String msg = userLoginInfo.getFail_info();
                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    SaveLoginTokenToLocal saveLoginTokenToLocal = new SaveLoginTokenToLocal();
                                    saveLoginTokenToLocal.saveToSharedPreferences(Register.this,userLoginInfo);
                                    String user_name = userLoginInfo.getUser_name();
                                    String user_email = userLoginInfo.getUser_email();
                                    String user_id = userLoginInfo.getUser_id();
                                    String user_login_token = userLoginInfo.getUser_login_token();
                                    Intent intent = new Intent(Register.this, MainActivity.class);
                                    intent.putExtra("user_name",user_name);
//                    intent.putExtra("user_email",user_email);
                                    intent.putExtra("user_id",user_id);
                                    intent.putExtra("user_login_token",user_login_token);
                                    startActivity(intent);
                                }
                            }
                            else {
                                Toast toast = Toast.makeText(getApplicationContext(), R.string.Failed_Login_Info, Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                        else
                        {
                            Toast toast = Toast.makeText(getApplicationContext(), R.string.Failed_Login_password_not_match, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                    else
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.Failed_Login_wrong_email, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.Failed_Login_empty_input, Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
    }
    public UserLoginInfo insert_user(String URL, String user_name,String user_email,String user_password)
    {
        RegisterRequest registerRequest = new RegisterRequest(URL,user_name,user_email,user_password);
        Thread thread = new Thread(registerRequest);
        thread.start();
        RegisterInfo registerInfo = getRegisterInfo();
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        try {
            thread.join();
            userLoginInfo = registerRequest.getUserLoginInfo();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return userLoginInfo;
    }
    public RegisterInfo getRegisterInfo()
    {
        RegisterInfo registerInfo = new RegisterInfo();
        String name = fullname.getText().toString();
        String user_email = email.getText().toString();
        String user_pass = password.getText().toString();
        String confirm_user_pass = confirm_pass.getText().toString();
        if(user_pass.equals(""))
        {
            user_pass = null;
        }
        if(confirm_user_pass.equals(""))
        {
            confirm_user_pass = null;
        }
        if (name.equals(""))
        {
            name=null;
        }
        registerInfo.setUser_name(name);
        registerInfo.setUser_email(user_email);
        registerInfo.setUser_password(user_pass);
        registerInfo.setUser_confirm_password(confirm_user_pass);
        return  registerInfo;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
