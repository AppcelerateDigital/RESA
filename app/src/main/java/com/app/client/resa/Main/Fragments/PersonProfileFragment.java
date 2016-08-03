package com.app.client.resa.Main.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.client.resa.Config.ConfigFile;
import com.app.client.resa.Main.MainActivity;
import com.app.client.resa.R;
import com.app.client.resa.UserInfo.GetUserInfoRequest;
import com.app.client.resa.UserInfo.UpdateUserInfoRequest;
import com.app.client.resa.UserInfo.UserProfileInfo;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wuyifan on 3/06/16.
 */
public class PersonProfileFragment extends Fragment {

    Spinner spinner_job_capacity;
    Spinner spinner_pay_type;
    Spinner spinner_is_indigenous;
    Spinner spinner_gender;
    Spinner spinner_age_group;
    Spinner spinner_job_classification;
    TextView textView_job_capacity;
    TextView textView_gender;
    TextView textView_age_group;
    TextView textView_job_classification;
    TextView textView_pay_type;
    TextView textView_is_indigenous;
    Button btn_edt_info;
    View returnView;
    Button btn_back_to_edit;
    Button btn_update;
    private SharedPreferences sharedPreferences;
    private String user_id ="";
    private String user_login_token ="";

    UserProfileInfo userProfileInfo = new UserProfileInfo();

    public PersonProfileFragment() {


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        container.removeAllViews();
        View rootView = inflater.inflate(R.layout.fragment_personal_profile_show, container, false);
        sharedPreferences= getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        user_id= sharedPreferences.getString("user_id","");
        user_login_token = sharedPreferences.getString("user_login_token","");
        userProfileInfo.setUser_id(user_id);
        userProfileInfo.setUser_login_token(user_login_token);
        initProfileInfo(rootView,inflater,container);
//        initAllspinners(rootView);
        return rootView;
    }
    public void initProfileInfo(final View rootView,final  LayoutInflater inflater,final ViewGroup container)
    {
            UserProfileInfo userProfileInfo = getUserInfo(user_id,user_login_token);
            textView_job_capacity = (TextView) rootView.findViewById(R.id.textview_show_job_capacity);
            textView_age_group = (TextView) rootView.findViewById(R.id.textview_show_age_group);
            textView_gender = (TextView) rootView.findViewById(R.id.textview_show_gender);
            textView_is_indigenous = (TextView) rootView.findViewById(R.id.textview_show_indigenous);
            textView_pay_type = (TextView) rootView.findViewById(R.id.textview_show_pay_type);

            textView_job_classification = (TextView) rootView.findViewById(R.id.textview_show_job_classification);
            btn_edt_info = (Button) rootView.findViewById(R.id.btn_edt_info);
            textView_age_group.setText(userProfileInfo.getAge_group());
            textView_gender.setText(userProfileInfo.getGender());
            textView_is_indigenous.setText(userProfileInfo.getIs_indigenous());
            textView_pay_type.setText(userProfileInfo.getPay_type());
            textView_job_capacity.setText(userProfileInfo.getJob_capacity());
            textView_job_classification.setText(userProfileInfo.getJob_classification());
            returnView = new View(getActivity().getApplicationContext());

            btn_edt_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    View showview = rootView.findViewById(R.id.layout_show_profile);
                    ViewGroup parent = (ViewGroup) showview.getParent();
                    View editView =inflater.inflate(R.layout.fragment_personal_profile, container, false);
                    initAllspinners(editView,rootView);
                    parent.removeView(showview);
                    parent.addView(editView);
                }
            });
    }
    public UserProfileInfo getUserInfo(String user_id, String user_login_token)
    {
        String URL = ConfigFile.BASE_URL+ConfigFile.GET_USER_PROFILE_URL;
        GetUserInfoRequest getUserInfoRequest = new GetUserInfoRequest(URL,userProfileInfo);
        Thread thread = new Thread(getUserInfoRequest);
        thread.start();
        try{
            thread.join();
            userProfileInfo =getUserInfoRequest.getUserProfileInfo();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return userProfileInfo;
    }

    public void initAllspinners(final View editView, final View showView )
    {
        initJob_capacity(editView);
        initAge_group(editView);
        initGender(editView);
        initJob_classification(editView);
        initPay_type(editView);
        initIs_indigenous(editView);
        btn_back_to_edit =(Button) editView.findViewById(R.id.btn_back_to_edit);
        btn_update = (Button) editView.findViewById(R.id.btn_update_info);
        btn_back_to_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup parent = (ViewGroup) editView.getParent();
                parent.removeView(editView);
                parent.addView(showView);
//              initProfileInfo(showView,inflater,container);
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            String status = "";
            @Override
            public void onClick(View v) {

                String URL = ConfigFile.BASE_URL+ConfigFile.UPDATE_USER_PROFILE_URL;
                UpdateUserInfoRequest updateUserInfoRequest = new UpdateUserInfoRequest(URL,userProfileInfo);
                Thread thread = new Thread(updateUserInfoRequest);
                thread.start();
                try {
                    thread.join();
                    status = updateUserInfoRequest.getStatus();
                    if(status.equals("success"))
                    {
                        Intent intent = new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);
                    }

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

    }
    public void initJob_capacity(View rootView)
    {
        final  String[] jobCapacity = getResources().getStringArray(R.array.job_capacity);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(rootView.getContext(),R.layout.profile_spinner_text,jobCapacity);
        arrayAdapter.setDropDownViewResource(R.layout.profile_spinner_text_show);
        spinner_job_capacity = (Spinner) rootView.findViewById(R.id.spinner_job_capacity);
        spinner_job_capacity.setAdapter(arrayAdapter);
        //  ...........
        int selectionPosition= arrayAdapter.getPosition(userProfileInfo.getJob_capacity());
        spinner_job_capacity.setSelection(selectionPosition);

        //   spinner_job_capacity.setSelection(0);
        spinner_job_capacity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                spinner_job_capacity.setSelection(position,true);
                userProfileInfo.setJob_capacity(spinner_job_capacity.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_job_capacity.setVisibility(View.VISIBLE);

    }

    public  void  initPay_type(View rootView)
    {
        final String[] pay_type = getResources().getStringArray(R.array.pay_type);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(rootView.getContext(),R.layout.profile_spinner_text,pay_type);
        arrayAdapter.setDropDownViewResource(R.layout.profile_spinner_text_show);
        spinner_pay_type = (Spinner) rootView.findViewById(R.id.spinner_pay_type);
        spinner_pay_type.setAdapter(arrayAdapter);
        int selectionPosition= arrayAdapter.getPosition(userProfileInfo.getPay_type());
        spinner_pay_type.setSelection(selectionPosition);

        spinner_pay_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                spinner_pay_type.setSelection(position,true);
                userProfileInfo.setPay_type(spinner_pay_type.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_pay_type.setVisibility(View.VISIBLE);

    }
    public  void  initIs_indigenous(View rootView)
    {
        final String[] is_indigenous = getResources().getStringArray(R.array.indigenous);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(rootView.getContext(),R.layout.profile_spinner_text,is_indigenous);
        arrayAdapter.setDropDownViewResource(R.layout.profile_spinner_text_show);
        spinner_is_indigenous = (Spinner) rootView.findViewById(R.id.spinner_is_indigenous);
        spinner_is_indigenous.setAdapter(arrayAdapter);
        int selectionPosition= arrayAdapter.getPosition(userProfileInfo.getIs_indigenous());
        spinner_is_indigenous.setSelection(selectionPosition);

        spinner_is_indigenous.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                spinner_is_indigenous.setSelection(position,true);
                userProfileInfo.setIs_indigenous(spinner_is_indigenous.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_is_indigenous.setVisibility(View.VISIBLE);
    }
    public  void  initGender(View rootView)
    {
        final String[] gender = getResources().getStringArray(R.array.gender);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(rootView.getContext(),R.layout.profile_spinner_text,gender);
        arrayAdapter.setDropDownViewResource(R.layout.profile_spinner_text_show);
        spinner_gender = (Spinner) rootView.findViewById(R.id.spinner_gender);
        spinner_gender.setAdapter(arrayAdapter);
        int selectionPosition= arrayAdapter.getPosition(userProfileInfo.getGender());
        spinner_gender.setSelection(selectionPosition);

        spinner_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                spinner_gender.setSelection(position,true);
                userProfileInfo.setGender(spinner_gender.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_gender.setVisibility(View.VISIBLE);
    }
    public  void  initAge_group(View rootView)
    {
        final String[] age_group = getResources().getStringArray(R.array.age_group);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(rootView.getContext(),R.layout.profile_spinner_text,age_group);
        arrayAdapter.setDropDownViewResource(R.layout.profile_spinner_text_show);
        spinner_age_group = (Spinner) rootView.findViewById(R.id.spinner_age_group);
        spinner_age_group.setAdapter(arrayAdapter);
        int selectionPosition= arrayAdapter.getPosition(userProfileInfo.getAge_group());
        spinner_age_group.setSelection(selectionPosition);
        spinner_age_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                spinner_age_group.setSelection(position,true);
//                System.out.println(spinner_age_group.getSelectedItem().toString());
                userProfileInfo.setAge_group(spinner_age_group.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_age_group.setVisibility(View.VISIBLE);
    }
    public  void  initJob_classification(View rootView)
    {
        final String[] job_classification = getResources().getStringArray(R.array.job_classification);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(rootView.getContext(),R.layout.profile_spinner_text,job_classification);
        arrayAdapter.setDropDownViewResource(R.layout.profile_spinner_text_show);
        spinner_job_classification = (Spinner) rootView.findViewById(R.id.spinner_job_classification);
        spinner_job_classification.setAdapter(arrayAdapter);
        int selectionPosition= arrayAdapter.getPosition(userProfileInfo.getJob_classification());
        spinner_job_classification.setSelection(selectionPosition);

        spinner_job_classification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                spinner_job_classification.setSelection(position,true);
                userProfileInfo.setJob_classification(spinner_job_classification.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_job_classification.setVisibility(View.VISIBLE);
    }
}
