package com.app.client.resa.UserInfo;

/**
 * Created by wuyifan on 1/07/16.
 */
public class UserLoginInfo {

    private String user_id;
    private String user_name;
    private String user_email;
    private String user_login_token;
    private String status;
    private String fail_info;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_login_token() {
        return user_login_token;
    }

    public void setUser_login_token(String user_login_token) {
        this.user_login_token = user_login_token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFail_info() {
        return fail_info;
    }

    public void setFail_info(String fail_info) {
        this.fail_info = fail_info;
    }
}
