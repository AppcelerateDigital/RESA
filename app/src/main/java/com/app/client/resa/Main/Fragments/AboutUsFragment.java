package com.app.client.resa.Main.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.client.resa.R;

/**
 * Created by wuyifan on 3/06/16.
 */
public class AboutUsFragment extends Fragment {
    TextView textView_web1;
    TextView textView_web2;
    public AboutUsFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);



        textView_web1 = (TextView) rootView.findViewById(R.id.weblink1);
        textView_web2 = (TextView) rootView.findViewById(R.id.weblink2);
//        String webLinkText = "<a href='https://www.lifeline.org.au/'>"+getResources().getString(R.string.web1_content)+"</a>" ;
//        textView.setText(Html.fromHtml(webLinkText));
//        textView.setTextColor(getResources().getColor(R.color.white_color));

        textView_web1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = textView_web1.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent .setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        textView_web2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = textView_web2.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent .setData(Uri.parse(url));
                startActivity(intent);
            }
        });


        return rootView;
    }
}
