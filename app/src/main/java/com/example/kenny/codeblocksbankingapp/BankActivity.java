package com.example.kenny.codeblocksbankingapp;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.kenny.codeblocksbankingapp.model.AccountHolder;
import com.example.kenny.codeblocksbankingapp.model.CustomerDatabase;

import java.lang.reflect.Array;

/*This activity is for the app's main user page. It
* 1) Has a Navigation Drawer from which the user can access other parts of the app
* 2) Handles the lanuching of fragments for each navigation item
* 3) Has buttons from which users can access their accounts*/
public class BankActivity extends AppCompatActivity {
    //Fields
    Toolbar mainPageToolbar; /*This will be used to
    1) Allow the user to access the navigation drawer
    */
    private ActionBarDrawerToggle  drawerToggle;/*
    1) This, when attached the toolbar, allows uses to access the navigation drawer*/

    private DrawerLayout mainUserPageDrawerLayout;/*
    This is the drawerlayout that contains the navigationdrawer*/

    
    //arrays that will store the dummy data
    String[] holderNames;
    int[] accessNo;
    String[] passwords;
    int[] checkAccNo;
    int [] savAccNo;
    String[] checkAccFunds;
    String[] savAccFunds;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //get the content of the arrays from strings.xml
        holderNames = getResources().getStringArray(R.array.holder_names);
        accessNo = getResources().getIntArray(R.array.dummy_access_card_no);
        passwords = getResources().getStringArray(R.array.dummy_password);
        checkAccNo = getResources().getIntArray(R.array.dummy_checking_account_no);
        savAccNo = getResources().getIntArray(R.array.dummy_savings_account_no);
        checkAccFunds = getResources().getStringArray(R.array.dummy_checking_account_funds);
        savAccFunds = getResources().getStringArray(R.array.dummy_savings_account_funds);

        saveHolder();


        super.onCreate(savedInstanceState);
        /*This if statement is to make the status bar the same color as the toolbar
        * for API 21 and up */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }



        /*Setting this Activity's Theme
        * To use a Naviagtion drawer the Theme has to be a AppCompat theme
        * So a custom theme from appcompat is used */
        setTheme(R.style.NewAppTheme);
        setContentView(R.layout.activity_main_user_page);

        //Getting a reference to the drawerlayout of the activity
        mainUserPageDrawerLayout = findViewById(R.id.drawer_layout);
        //Making the toolbar function as an actionbar
        setUpActionBar(mainPageToolbar);
    }


    /*This helper method sets up the toolbar as an actionbar with all right functionalities. It
    * 1) gets a reference for the toolbar
    * 2) Makes the toolbar the actionbar
    * 3) Makes the icon show on the actionBar
    * 5) Makes sure the Toolbar only shows the Icon
    * 6) Makes the hambunger button visiable on the actionbar and gives the user the ability tap
    * it to display the navigation drawer this is done by the using
    * an actionbarDraerToggle object*/
    void setUpActionBar(Toolbar toolbar ){
        toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        drawerToggle = new ActionBarDrawerToggle(this, mainUserPageDrawerLayout, toolbar, 0, 0) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setDrawerIndicatorEnabled(true);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mainUserPageDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
    }

    //Method for loading the account holder info from resource arrays
    //and savings them to the customer database
    public void saveHolder(){
        CustomerDatabase custDb = new CustomerDatabase(this);

        //loads and saves all 5 holders using the dummy data in arrays
        for(int i = 0; i < 5; i++){
            AccountHolder holder = createNewHolder(i);

            custDb.saveAccountHolder(holder);
        }
    }

    //Provide the accountHolder constructor with the parameters to create objects
    private AccountHolder createNewHolder(int index){

        return new AccountHolder(holderNames[index],accessNo[index],passwords[index], checkAccNo[index], savAccNo[index], Double.valueOf(checkAccFunds[index]), Double.valueOf(savAccFunds[index]));
    }
}
