package com.app.client.resa.Main;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.client.resa.R;

import java.util.ArrayList;

/**
 * Created by wuyifan on 3/06/16.
 */
public class MainActivityDrawerListAdapter extends BaseAdapter{


    private Context context;
    private ArrayList<MainActivityDrawerItem> mainActivityDrawerItems;


    public MainActivityDrawerListAdapter(Context context, ArrayList<MainActivityDrawerItem> mainActivityDrawerItems) {
        this.context = context;
        this.mainActivityDrawerItems = mainActivityDrawerItems;
    }

    @Override
    public int getCount() {
        return mainActivityDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mainActivityDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.img_main_drawer_icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.text_main_drawer_title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.text_drawer_list_item_counter);

        imgIcon.setImageResource(mainActivityDrawerItems.get(position).getIcon_main_drawer());
        txtTitle.setText(mainActivityDrawerItems.get(position).getTitle_main_drawer());
        // displaying count
        // check whether it set visible or not
        if(mainActivityDrawerItems.get(position).isCounterVisible()){
            txtCount.setText(mainActivityDrawerItems.get(position).getCount());
        }else{
            // hide the counter view
            txtCount.setVisibility(View.GONE);
        }

        return convertView;
    }

}
