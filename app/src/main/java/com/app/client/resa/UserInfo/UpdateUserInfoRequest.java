package com.app.client.resa.UserInfo;

/**
 * Created by wuyifan on 1/07/16.
 */
public class UpdateUserInfoRequest implements Runnable {


    UserProfileInfo userProfileInfo ;
    String URL;
    String status;

    public UpdateUserInfoRequest(String URL,UserProfileInfo userLoginInfo) {
        this.userProfileInfo = userLoginInfo;
        this.URL =URL;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            UpdateUserInfo updateUserInfo = new UpdateUserInfo();
            status = updateUserInfo.updateInfoResponse(URL,userProfileInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
