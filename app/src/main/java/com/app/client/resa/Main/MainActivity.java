package com.app.client.resa.Main;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.app.client.resa.Main.Fragments.AboutUsFragment;
import com.app.client.resa.Main.Fragments.HomeFragment;
import com.app.client.resa.Main.Fragments.PersonProfileFragment;
import com.app.client.resa.Main.Fragments.QuestionFragment;
import com.app.client.resa.Main.Fragments.RewardsFragment;
import com.app.client.resa.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mainDrawerLayout;
    private ListView mainDrawerList;
    private ActionBarDrawerToggle mainDrawerToggle;
    // nav drawer title
    private CharSequence mainDrawerTitle;
    // used to store app title
    private CharSequence mTitle;
    // slide menu items
    private String[] MenuTitles;
    private TypedArray MenuIcons;
    private ArrayList<MainActivityDrawerItem> mainActivityDrawerItems;
    private MainActivityDrawerListAdapter adapter;

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        toolbar = (Toolbar) findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);

        mTitle = mainDrawerTitle = getTitle();

        // load slide menu items
        MenuTitles = getResources().getStringArray(R.array.main_drawer_items);


        // nav drawer icons from resources
        MenuIcons = getResources()
                .obtainTypedArray(R.array.main_drawer_icons);


        mainDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mainDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        mainActivityDrawerItems = new ArrayList<MainActivityDrawerItem>();

        // adding nav drawer items to array
        // Home
        for(int i =0;i<MenuIcons.length();i++)
        {
            mainActivityDrawerItems.add(new MainActivityDrawerItem(MenuTitles[i], MenuIcons.getResourceId(i, -1)));
        }
        // Recycle the typed array
        MenuIcons.recycle();

        mainDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new MainActivityDrawerListAdapter(getApplicationContext(),
                mainActivityDrawerItems);
        mainDrawerList.setAdapter(adapter);

        // Set up your ActionBar
//        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // enabling action bar app icon and behaving it as toggle button

        mainDrawerToggle = new ActionBarDrawerToggle(this,mainDrawerLayout,R.string.app_name,R.string.app_name)
        {

            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mainDrawerTitle);
                // calling onPrepareOptionsMen    invalidateOptionsMenu();u() to hide action bar icons
            }
        };
        mainDrawerLayout.addDrawerListener(mainDrawerToggle);
        if (savedInstanceState == null) {
            // on first time display view for first nav item
           displayView(0);
        }
    }

    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mainDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
//                System.runFinalizersOnExit(true);
//                this.finishAffinity();
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }

    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mainDrawerLayout.isDrawerOpen(mainDrawerList);
        menu.findItem(R.id.logout).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new QuestionFragment();
                break;
//                fragment = new HomeFragment();
//                break;
            case 1:
                fragment = new PersonProfileFragment();
                break;
            case 2:
                fragment = new RewardsFragment();
                break;
            case 3:
                fragment = new AboutUsFragment();
                break;
//            case 4:

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mainDrawerList.setItemChecked(position, true);
            mainDrawerList.setSelection(position);
            setTitle(MenuTitles[position]);
            mainDrawerLayout.closeDrawer(mainDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
//        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mainDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mainDrawerToggle.onConfigurationChanged(newConfig);
    }

}
