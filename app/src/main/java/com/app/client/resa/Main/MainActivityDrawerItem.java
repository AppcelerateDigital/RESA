package com.app.client.resa.Main;

/**
 * Created by wuyifan on 3/06/16.
 */
public class MainActivityDrawerItem {


    private String title_main_drawer;
    private int icon_main_drawer;

    // for later use, such as count num of e-mail
    private String count = "0";
    // boolean to set visiblity of the counter
    private boolean isCounterVisible = false;

    public MainActivityDrawerItem(){}

    public MainActivityDrawerItem(String title_main_drawer, int icon_main_drawer){
        this.title_main_drawer = title_main_drawer;
        this.icon_main_drawer = icon_main_drawer;
    }

    public MainActivityDrawerItem(String title_main_drawer, int icon_main_drawer, boolean isCounterVisible, String count){
        this.title_main_drawer = title_main_drawer;
        this.icon_main_drawer = icon_main_drawer;
        this.isCounterVisible = isCounterVisible;
        this.count = count;
    }

    public String getTitle_main_drawer() {
        return title_main_drawer;
    }

    public void setTitle_main_drawer(String title_main_drawer) {
        this.title_main_drawer = title_main_drawer;
    }

    public int getIcon_main_drawer() {
        return icon_main_drawer;
    }

    public void setIcon_main_drawer(int icon_main_drawer) {
        this.icon_main_drawer = icon_main_drawer;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public boolean isCounterVisible() {
        return isCounterVisible;
    }

    public void setCounterVisible(boolean counterVisible) {
        isCounterVisible = counterVisible;
    }

}
