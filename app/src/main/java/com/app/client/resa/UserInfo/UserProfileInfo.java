package com.app.client.resa.UserInfo;

/**
 * Created by wuyifan on 1/07/16.
 */
public class UserProfileInfo {
    private String user_id;
    private String user_login_token;
    private String user_profile_id;
    private String job_capacity;
    private String pay_type;
    private String is_indigenous;
    private String gender;
    private String age_group;
    private String job_classification;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_profile_id() {
        return user_profile_id;
    }

    public void setUser_profile_id(String user_profile_id) {
        this.user_profile_id = user_profile_id;
    }

    public String getJob_capacity() {
        return job_capacity;
    }

    public void setJob_capacity(String job_capacity) {
        this.job_capacity = job_capacity;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getIs_indigenous() {
        return is_indigenous;
    }

    public void setIs_indigenous(String is_indigenous) {
        this.is_indigenous = is_indigenous;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge_group() {
        return age_group;
    }

    public void setAge_group(String age_group) {
        this.age_group = age_group;
    }

    public String getJob_classification() {
        return job_classification;
    }

    public void setJob_classification(String job_classification) {
        this.job_classification = job_classification;
    }

    public String getUser_login_token() {
        return user_login_token;
    }

    public void setUser_login_token(String user_login_token) {
        this.user_login_token = user_login_token;
    }
}
