package com.app.client.resa.UserInfo;
/**
 * Created by wuyifan on 5/07/16.
 */
public class GetUserInfoRequest implements Runnable {

    UserProfileInfo userProfileInfo ;
    String URL;

    public GetUserInfoRequest(String URL,UserProfileInfo userLoginInfo) {
        this.userProfileInfo = userLoginInfo;
        this.URL =URL;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            System.out.println(URL);
            GetUserInfo getUserInfo = new GetUserInfo();
            userProfileInfo = getUserInfo.getInfoResponse(URL,userProfileInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserProfileInfo getUserProfileInfo() {
        return userProfileInfo;
    }

    public void setUserProfileInfo(UserProfileInfo userProfileInfo) {
        this.userProfileInfo = userProfileInfo;
    }
}
