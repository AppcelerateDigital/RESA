package com.app.client.resa.Config;

/**
 * Created by wuyifan on 11/07/16.
 */
public class ConfigFile {


//    public static String LOCAL_TESTING = "http://10.1.1.9:8888/RESA/APIs";
////    public static String LOCAL_TESTING = "http://192.168.178.130:8888/RESA/APIs";
//    public static String BASE_URL = LOCAL_TESTING;
    public static String BASE_URL ="http://www.burgerlocator.com.au/resa";
    public static String LOGIN_URL ="/Users/UserLogin.php?";
    public static String REGISTER_URL ="/Users/UserRegister.php?";
    public static String UPDATE_USER_PROFILE_URL ="/Users/UpdateUserProfile.php?";
    public static String GET_USER_PROFILE_URL ="/Users/GetUserProfileInfo.php?";
    public static String GET_LAST_ANSWET_TIME ="/Answers/GetLastAnswerTime.php?";
    public static String GET_QUESTIONS_URL ="/Questions/GetQuestions.php?";
    public static String GENERATE_INSERT_RESULT_URL ="/Answers/GetInsertID.php?";
    public static String UPLOAD_USER_ANSWER_URL ="/Answers/UploadAnswers.php?";



    public static long TIME_DELAY = 500;
}
