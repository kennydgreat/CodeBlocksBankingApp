package com.example.kenny.codeblocksbankingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.media.RatingCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.kenny.codeblocksbankingapp.model.CustomerDatabase;



/*This activity is for the app's main user page. It
* 1) Has a Navigation Drawer from which the user can access other parts of the app
* 2) Handles the lanuching of fragments for each navigation item
* 3) Has buttons from which users can access their accounts*/
public class BankActivity extends AppCompatActivity implements BankAccountsSummaryFragment.OnAccountsImageViewButtonClickListener ,
        NavigationView.OnNavigationItemSelectedListener{
    //Fields
    Toolbar mainPageToolbar; /*This will be used to
    1) Allow the user to access the navigation drawer
    */
    private ActionBarDrawerToggle  drawerToggle;/*
    1) This, when attached the toolbar, allows uses to access the navigation drawer*/

    private DrawerLayout mainUserPageDrawerLayout;/*
    This is the drawerlayout that contains the navigationdrawer*/

    //The NavigationDraw View
    private NavigationView navigationView;

    //arrays that will store the dummy data
    String[] holderNames;
    int[] accessNo;
    String[] passwords;
    int[] checkAccNo;
    int [] savAccNo;
    String[] checkAccFunds;
    String[] savAccFunds;

    //An intent will be passed by login activity to bank
    //with the current users card number which will be used by
    //the bank to update views with user info
    private String currentCustomerAccessCardNo;

    private String[] currentCustomerInfoArray;


    private FragmentManager fragmentManager = getSupportFragmentManager();
    //This the FragmentManager that will manage all fragments that will
    //attach to this activity (Navigation drawer menu items, account pages,
    // account summary page)
    private BankAccountsSummaryFragment bankAccountsSummaryFragment;

    //view displaying the customer's name in navigation drawer header
    public TextView txtUserNameDisplay;


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


        //Set up NavigationDrawer
        setUpNavigationDrawer();

        //get the incoming intent
        Intent incomingIntent = getIntent();
        currentCustomerAccessCardNo = incomingIntent.getStringExtra("customerAccountNumber");

        currentCustomerInfoArray = currentCustomerInfo(currentCustomerAccessCardNo);
        launchBankAccountSummaryFragment();

        //display customer name on navigation header with welcome message
        txtUserNameDisplay = findViewById(R.id.txt_userNameDisplay);
        View headerView = navigationView.getHeaderView(0);
        txtUserNameDisplay = headerView.findViewById(R.id.txt_userNameDisplay);
        txtUserNameDisplay.setText(getResources().getString(R.string.welcomeString, currentCustomerInfoArray[0]));


    }

    private void launchBankAccountSummaryFragment() {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        bankAccountsSummaryFragment = new BankAccountsSummaryFragment();

        //pass the info array in a bundle
        Bundle args = new Bundle();
        args.putStringArray("CURRENT CUSTOMER INFO ARRAY", currentCustomerInfoArray);

        bankAccountsSummaryFragment.setArguments(args);
        fragmentTransaction.replace(R.id.main_user_page_container
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
    //This method sets up the NavigationDrawer
    public void setUpNavigationDrawer(){
        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //using the customers access card number, get their name from the database
    public String[] currentCustomerInfo(String id){

        String[] custInfo;

        CustomerDatabase custDb = new CustomerDatabase(getApplicationContext());

        custInfo = new String[]{
                //0
                custDb.getCustomerNameByAccessNo(id),
                //1
                custDb.getCustomerCheckAccNoByAccessNo(id),
                //2
                custDb.getCustomerCheckAccFundsByAccessNo(id),
                //3
                custDb.getCustomerSavingsAccNoByAccessNo(id),
                //4
                custDb.getCustomerSavingsAccFundsByAccessNo(id)
        };

        return custInfo;
    }

    //This called by the MakeTransactionFragment when cancel button is clicked
    //it removes the fragment
    public void onMakeTransactionCancelButtonClick(){
        fragmentManager.popBackStack();
    }

    //logging out dialog to be called when user taps backout or clicks log out button
    public void createLoggingOutDialog(Context context){
        final AlertDialog.Builder loggingOutDialog = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AlertDialogCustom));
        loggingOutDialog.setIcon(android.R.drawable.ic_dialog_alert);
        loggingOutDialog.setTitle("Log Out?");
        loggingOutDialog.setMessage("Are you sure you want to log out?");

        loggingOutDialog.setCancelable(true);

        loggingOutDialog.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                //killing BackActivity
                finish();
            }
        });

        loggingOutDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                dialogInterface.cancel();
            }
        });


        AlertDialog alert = loggingOutDialog.create();
        alert.show();
    }
/*This implement of on onAccountsImageViewButton
    * 1) is for laucnhing the correspondsing fragment depending
    * on what Accounts imageView was clicked on the BankAccountsSummaryFragment*/

    @Override
    public void onAccountsImageViewButton(int imageViewButtonID) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AccountPageFragment accountPageFragment = new AccountPageFragment();
        Bundle args = new Bundle();
        args.putStringArray("CURRENT CUSTOMER INFO ARRAY", currentCustomerInfoArray);
        switch (imageViewButtonID){
            case R.id.btn_savingsImage:
                args.putInt("IMAGEVIEW BUTTON ID",R.id.btn_savingsImage);
                accountPageFragment.setArguments(args);
                fragmentTransaction.replace(R.id.main_user_page_container
                        , accountPageFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.btn_checkingImage:
                args.putInt("IMAGEVIEW BUTTON ID",R.id.btn_checkingImage);
                accountPageFragment.setArguments(args);
                fragmentTransaction.replace(R.id.main_user_page_container
                        , accountPageFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.btn_investmentsImage:
                args.putInt("IMAGEVIEW BUTTON ID",R.id.btn_investmentsImage);
                accountPageFragment.setArguments(args);
                fragmentTransaction.replace(R.id.main_user_page_container
                        , accountPageFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            default:
        }
    }

    //This method is the callback method for Navigation Listener
    //It's called when Item on the the Navigation Drawer is clicked
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle args = new Bundle();
        args.putStringArray("CURRENT CUSTOMER INFO ARRAY", currentCustomerInfoArray);
        switch (item.getItemId()) {
            //Start up the  MakeTransactionFragment fragment
            case R.id.make_transactions_menu_item: {
                MakeTransactionFragment makeTransactionFragment = new MakeTransactionFragment();
                makeTransactionFragment.setArguments(args);
                fragmentTransaction.replace(R.id.main_user_page_container
                        , makeTransactionFragment);

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            }
            //Log out the user
            case R.id.logout_menu_item:{
                createLoggingOutDialog(this);
            }
        }
        //close navigation drawer
        mainUserPageDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //override backPressed function to ensure the user logs out first
    @Override
    public void onBackPressed(){
        if(bankAccountsSummaryFragment != null && bankAccountsSummaryFragment.isVisible()){
            createLoggingOutDialog(this);
        }else{
            super.onBackPressed();
        }
    }

}
