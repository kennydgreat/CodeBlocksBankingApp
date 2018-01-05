package com.example.kenny.codeblocksbankingapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.kenny.codeblocksbankingapp.model.AccountHolder;
import com.example.kenny.codeblocksbankingapp.model.CustomerDatabase;


/*This activity is for the app's main user page. It
* 1) Has a Navigation Drawer from which the user can access other parts of the app
* 2) Handles the lanuching of fragments for each navigation item
* 3) Has buttons from which users can access their accounts*/
public class BankActivity extends AppCompatActivity implements BankAccountsSummaryFragment.OnAccountsImageViewButtonClickListener {
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

    //If the user is successfully logged in their access number is
    //stored in this variable
    int currentUserAccessNumber;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    //This the FragmentManager that will manage all fragments that will
    //attach to this activity (Navigation drawer menu items, account pages,
    // account summary page)
    private Bundle args = new Bundle();
    private BankAccountsSummaryFragment bankAccountsSummaryFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        launchBankAccountSummaryFragment();


    }

    private void launchBankAccountSummaryFragment() {
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        bankAccountsSummaryFragment = new BankAccountsSummaryFragment();
        fragmentTransaction.add(R.id.main_user_page_container
                , bankAccountsSummaryFragment);
        fragmentTransaction.commit();
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


    /*This implement of on onAccountsImageViewButton
    * 1) is for laucnhing the correspondsing fragment depending
    * on what Accounts imageView was clicked on the BankAccountsSummaryFragment*/
    @Override
    public void onAccountsImageViewButton(int imageViewButtonID) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AccountPageFragment accountPageFragment = new AccountPageFragment();
        currentUserAccessNumber = 345;
        args.putInt("CURRENT USER ACCESS NUMBER",currentUserAccessNumber);
        switch (imageViewButtonID){
            case R.id.savings_imageview_button:
                args.putInt("IMAGEVIEW BUTTON ID",R.id.savings_imageview_button);
                accountPageFragment.setArguments(args);
                fragmentTransaction.replace(R.id.main_user_page_container
                        , accountPageFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.checkings_imageview_button:
                args.putInt("IMAGEVIEW BUTTON ID",R.id.checkings_imageview_button);
                accountPageFragment.setArguments(args);
                fragmentTransaction.replace(R.id.main_user_page_container
                        , accountPageFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.investments_imageview_button:
                args.putInt("IMAGEVIEW BUTTON ID",R.id.investments_imageview_button);
                accountPageFragment.setArguments(args);
                fragmentTransaction.replace(R.id.main_user_page_container
                        , accountPageFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            default:
        }
    }

    //verify the user using method in customer database
    public boolean isVerifiedUser(String userAccessCard, String userPassword){
        CustomerDatabase custDb = new CustomerDatabase(this);

        int check = custDb.login(userAccessCard, userPassword);

        if(check == 1){
            return true;
        }else{
            return false;
        }
    }

}
