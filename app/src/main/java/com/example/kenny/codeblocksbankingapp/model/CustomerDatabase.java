package com.example.kenny.codeblocksbankingapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kenny.codeblocksbankingapp.R;

import java.util.ArrayList;

/**
 * Created by Diego on 1/4/2018.
 */

public class CustomerDatabase{



    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;

    //Database constants
    public static final String DB_Name = "customers.db";
    public static final int DB_Version = 1;

    public static final String CUSTOMER_TABLE = "Customers";

    public static final String CARD_ACCESS_NO = "accessNo";
    public static final int CARD_ACCESS_NO_COLUMN = 0;

    public static final String CUSTOMER_PASSWORD = "password";
    public static final int CUSTOMER_PASSWORD_COLUMN = 2;

    public static final String CUSTOMER_NAME = "custName";
    public static final int CUSTOMER_NAME_COLUMN = 1;

    public static final String CHECKING_ACCOUNT_FUNDS = "checkAccFunds";
    public static final int CHECKING_ACCOUNT_FUNDS_COLUMN = 3;

    public static final String SAVINGS_ACCOUNT_FUNDS = "saveAccFunds";
    public static final int SAVINGS_ACCOUNT_FUNDS_COLUMN = 4;

    public static final String CHECKING_ACCOUNT_NO = "checkAccNo";
    public static final int CHECKING_ACCOUNT_NO_COLUMN = 5;

    public static final String SAVINGS_ACCOUNT_NO = "saveAccNo";
    public static final int SAVINGS_ACCOUNT_NO_COLUMN = 6;

    //DDL for creating the db
    public static final String CREATE_CUSTOMER_TABLE =
            "CREATE TABLE " + CUSTOMER_TABLE + " (" +
                    CARD_ACCESS_NO + " INTEGER PRIMARY KEY, " +
                    CUSTOMER_NAME + " TEXT, " +
                    CHECKING_ACCOUNT_NO + " TEXT, " +
                    CUSTOMER_PASSWORD + " TEXT, " +
                    CHECKING_ACCOUNT_FUNDS + " TEXT, " +
                    SAVINGS_ACCOUNT_NO + " TEXT, " +
                    SAVINGS_ACCOUNT_FUNDS + " TEXT)";


    public CustomerDatabase(Context context){
        openHelper = new DBHelper(context, DB_Name, DB_Version);
    }

    //method to store accountHolder objects into the database
    public AccountHolder saveAccHolder(AccountHolder holder){
        database = openHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CARD_ACCESS_NO, holder.getAccessCardNo());
        values.put(CUSTOMER_NAME, holder.getName());
        values.put(CUSTOMER_PASSWORD, holder.getPassword());
        values.put(CHECKING_ACCOUNT_FUNDS, holder.getCheckingAccountFunds());
        values.put(CHECKING_ACCOUNT_NO, holder.getCheckingAccountNo());
        values.put(SAVINGS_ACCOUNT_NO, holder.getSavingsAccountNo());
        values.put(SAVINGS_ACCOUNT_FUNDS, holder.getSavingsAccountFunds());

        long id = database.insert(CUSTOMER_TABLE, null, values);

        holder.setDbId(id);

        database.close();

        return holder;
    }

    private static class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context, String name, int version){
            super(context, name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TABLE);
            db.execSQL(CREATE_CUSTOMER_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

            db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TABLE);
            onCreate(db);

        }
    }

    /**Method will be used by the login activity to check if user is authorized */
    public int login(String accessNo, String password){
        database = openHelper.getReadableDatabase();
        String[] selectArgs = new String[]{accessNo,password};

        try{
            int i = 0;
            Cursor cursor = null;
            cursor = database.rawQuery("SELECT * FROM Customers WHERE accessNo =? AND password =?", selectArgs);
            cursor.moveToFirst();
            i = cursor.getCount();
            cursor.close();
            database.close();
            return i;
        }
        catch (Exception e){ e.printStackTrace();}

        return 0;
    }

    //get the customer's name
    public String getCustomerNameByAccessNo(String accessNo){

        String currentCustName;

        database = openHelper.getReadableDatabase();

        String[] selectArgs = new String[] {accessNo};

        Cursor cursor = null;
        cursor = database.rawQuery("SELECT custName FROM Customers WHERE accessNo =?", selectArgs);

        cursor.moveToNext();

        currentCustName = cursor.getString(cursor.getColumnIndex(CUSTOMER_NAME));
        cursor.close();
        database.close();

        return currentCustName;

    }

    //get the customer's checkAccNo
    public String getCustomerCheckAccNoByAccessNo(String accessNo){

        String currentCustCheckAccNo;

        database = openHelper.getReadableDatabase();

        String[] selectArgs = new String[] {accessNo};

        Cursor cursor = null;
        cursor = database.rawQuery("SELECT checkAccNo FROM Customers WHERE accessNo =?",selectArgs);

        cursor.moveToNext();

        currentCustCheckAccNo = cursor.getString(cursor.getColumnIndex(CHECKING_ACCOUNT_NO));
        cursor.close();
        database.close();

        return currentCustCheckAccNo;
    }

    //get checking funds
    public String getCustomerCheckAccFundsByAccessNo(String accessNo){

        String currentCustCheckAccFunds;

        database = openHelper.getReadableDatabase();

        String[] selectArgs = new String[] {accessNo};

        Cursor cursor = null;
        cursor = database.rawQuery("SELECT checkAccFunds FROM Customers WHERE accessNo =?",selectArgs);

        cursor.moveToNext();

        currentCustCheckAccFunds = cursor.getString(cursor.getColumnIndex(CHECKING_ACCOUNT_FUNDS));
        cursor.close();
        database.close();

        return currentCustCheckAccFunds;
    }

    //get savingsAccNo
    public String getCustomerSavingsAccNoByAccessNo(String accessNo){

        String currentCustSaveAccNo;

        database = openHelper.getReadableDatabase();

        String[] selectArgs = new String[] {accessNo};

        Cursor cursor = null;
        cursor = database.rawQuery("SELECT saveAccNo FROM Customers WHERE accessNo =?",selectArgs);

        cursor.moveToNext();

        currentCustSaveAccNo = cursor.getString(cursor.getColumnIndex(SAVINGS_ACCOUNT_NO));
        cursor.close();
        database.close();

        return currentCustSaveAccNo;

    }

    //get Savings accFunds
    public String getCustomerSavingsAccFundsByAccessNo(String accessNo){

        String currentCustSaveAccFunds;

        database = openHelper.getReadableDatabase();

        String[] selectArgs = new String[] {accessNo};

        Cursor cursor = null;
        cursor = database.rawQuery("SELECT saveAccFunds FROM Customers WHERE accessNo =?",selectArgs);

        cursor.moveToNext();

        currentCustSaveAccFunds = cursor.getString(cursor.getColumnIndex(SAVINGS_ACCOUNT_FUNDS));
        cursor.close();
        database.close();

        return currentCustSaveAccFunds;

    }

}
