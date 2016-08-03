package com.app.client.resa.Main.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.client.resa.R;

/**
 * Created by wuyifan on 3/06/16.
 */
public class RewardsFragment extends Fragment {
    public RewardsFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        View rootView = inflater.inflate(R.layout.fragment_rewards, container, false);


        return rootView;
    }
}
