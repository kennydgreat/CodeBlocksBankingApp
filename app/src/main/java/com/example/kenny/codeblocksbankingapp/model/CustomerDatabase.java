package com.example.kenny.codeblocksbankingapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by diego on 2018-01-02.
 */

public class CustomerDatabase{

    //instance members for accessing the db
    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;

    //db constants
    public static final String DB_NAME = "customers.db";
    public static final int DB_VERSION = 1;

    //table constants for customer entity
    public static final String CUSTOMERS_TABLE = "Customer";

    public static final String ACCESS_NO = "accessNo";
    public static final int ACCESS_NO_COLUMN = 0;

    public static final String NAME = "name";
    public static final int NAME_COLUMN = 1;

    public static final String PASSWORD = "password";
    public static final int PASSWORD_COLUMN = 2;

    public static final String CHECKING_ACCOUNT_NUMBER = "CheckAccNo";
    public static final int CHECKING_ACCOUNT_NUMBER_COLUMN = 3;

    public static final String SAVINGS_ACCOUNT_NUMBER = "SavingAccNo";
    public static final int SAVINGS_ACCOUNT_NUMBER_COLUMN = 4;


    //table constants for checking account entity
    public static final String CHECKING_ACCOUNT_TABLE = "CheckingAccount";

    public static final String CHECKING_ACCOUNT_FUNDS = "funds";
    public static final int CHECKING_ACCOUNT_FUNDS_COLUMN = 1;


    //table constant for savings account entity
    public static final String SAVINGS_ACCOUNT_TABLE = "SavingsAccount";

    public static final String SAVINGS_ACCOUNT_FUNDS = "funds";
    public static final int SAVINGS_ACCOUNT_FUNDS_COLUMN = 1;


    //DDL create the tables

    //checking account table
    public static final String CREATE_CHECKING_ACCOUNT_TABLE =
            "CREATE TABLE " + CHECKING_ACCOUNT_TABLE + " (" +
                    CHECKING_ACCOUNT_NUMBER + " INTEGER PRIMARY KEY, " +
                    NAME + " TEXT, " +
                    CHECKING_ACCOUNT_FUNDS + " TEXT)";

    //savings account table
    public static final String CREATE_SAVINGS_ACCOUNT_TABLE =
            "CREATE TABLE " + SAVINGS_ACCOUNT_TABLE + " (" +
                    SAVINGS_ACCOUNT_NUMBER + " INTEGER PRIMARY KEY, " +
                    NAME + " TEXT, " +
                    SAVINGS_ACCOUNT_FUNDS + "TEXT)";

    //customer table
    public static final String CREATE_CUSTOMER_TABLE =
            "CREATE TABLE " + CUSTOMERS_TABLE + " (" +
                    ACCESS_NO + " INTEGER PRIMARY KEY, " +
                    NAME + " TEXT, " +
                    PASSWORD + " TEXT, " +
                    CHECKING_ACCOUNT_NUMBER + " INTEGER, " +
                    "FOREIGN KEY (" + CHECKING_ACCOUNT_NUMBER + ") REFERENCES " +
                    CHECKING_ACCOUNT_TABLE + "(" + CHECKING_ACCOUNT_NUMBER + "), " +
                    SAVINGS_ACCOUNT_NUMBER + " INTEGER, " +
                    "FOREIGN KEY (" + SAVINGS_ACCOUNT_NUMBER + ") REFERENCES " +
                    SAVINGS_ACCOUNT_TABLE + "(" + SAVINGS_ACCOUNT_NUMBER + "))";

    public CustomerDatabase(Context context){
        //initialize openHelper class
        openHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    private  class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version){
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_CHECKING_ACCOUNT_TABLE);
            sqLiteDatabase.execSQL(CREATE_SAVINGS_ACCOUNT_TABLE);
            sqLiteDatabase.execSQL(CREATE_CUSTOMER_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + CUSTOMERS_TABLE + "," + CHECKING_ACCOUNT_TABLE + "," + SAVINGS_ACCOUNT_TABLE);

            onCreate(sqLiteDatabase);
        }
    }

    /**
     * Method for populating customer database
     */
    public AccountHolder saveAccountHolder(AccountHolder holder){
        //get writable for db
        database = openHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ACCESS_NO, holder.getAccessCardNo());
        values.put(NAME, holder.getName());
        values.put(PASSWORD, holder.getPassword());
        values.put(CHECKING_ACCOUNT_NUMBER, holder.getCheckingAccountNo());
        values.put(SAVINGS_ACCOUNT_NUMBER, holder.getSavingsAccountNo());
        values.put(CHECKING_ACCOUNT_FUNDS, holder.getCheckingAccountFunds());
        values.put(SAVINGS_ACCOUNT_FUNDS, holder.getSavingsAccountFunds());

        //id of inserted row and set
        long dbId = database.insert(CUSTOMERS_TABLE, null, values);

        holder.setDbId(dbId);

        //close the db
        database.close();

        //return holder with bdId assigned
        return holder;
    }

}