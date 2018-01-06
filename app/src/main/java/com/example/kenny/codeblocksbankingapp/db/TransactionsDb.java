package com.example.kenny.codeblocksbankingapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kenny.codeblocksbankingapp.model.Transactions;

import java.util.ArrayList;

/**
 * This class is manages the Transactions database
 */

public class TransactionsDb {
    private SQLiteDatabase transactionsDatabase;
    private SQLiteOpenHelper openHelper;
    //constants for the database
    public static final String DB_NAME = "transactions.db";
    public static final int DB_VERSION = 1;

    // constants for the table
    public static final String TRANSACTIONS_TABLE = "Transactions";

    public static final String ID = "_id";
    public static final int ID_COLUMN = 0;

    public static final String DATE = "date";
    public static final int DATE_COLUMN = 1;

    public static final String ACCOUNT = "account";
    public static final int ACCOUNT_COLUMN = 2;

    public static final String INFO = "info";
    public static final int INFO_COLUMN = 3;

    public static final String AMOUNT = "amount";
    public static final int AMOUNT_COLUMN = 4;

    //DDL for creating the table
    public static final String CREATE_TRANSACTIONS_TABLE =
            "CREATE TABLE " + TRANSACTIONS_TABLE +
                    " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DATE + " TEXT, " +
                    ACCOUNT + " TEXT, " +
                    INFO + " TEXT, " +
                    AMOUNT + " TEXT)";

    public  TransactionsDb(Context context){
        // initializing the openHelper object
        openHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    private class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context, String dbName,
                        SQLiteDatabase.CursorFactory factory, int dbVersion) {
            super(context,dbName,factory,dbVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            //Creating the datebase
            sqLiteDatabase.execSQL(CREATE_TRANSACTIONS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }


    }
    // This method stores a Trnasaction in the database
    public Transactions saveTransaction(Transactions transaction){
        transactionsDatabase = openHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(DATE, transaction.getDate());
        values.put(ACCOUNT, transaction.getAccount());
        values.put(INFO, transaction.getInfo());
        values.put(AMOUNT, transaction.getAmount());

        long dbId = transactionsDatabase.insert(TRANSACTIONS_TABLE, null,
                values);
        transaction.setDbId(dbId);

        transactionsDatabase.close();
        return transaction;


    }
    //This method querys the Transactions table for
    //savings accountn transactions and returns an arraylist of transactions
    //objects
    public ArrayList<Transactions> getSavingsTransactions(){
        ArrayList<Transactions> transactions = new ArrayList<>();
        transactionsDatabase = openHelper.getReadableDatabase();

        //the columns needed
      //  String[] cols = new String[] {"date","info","amount"};
        String sel = "account like 'S%'";

        Cursor result = transactionsDatabase.query(TRANSACTIONS_TABLE,
        null, sel,null, null,null, "date");

        // filling up arraylist with the results
        while(result.moveToNext()){
            Long dbId = result.getLong(ID_COLUMN);
            String date = result.getString(DATE_COLUMN);
            String acount = result.getString(ACCOUNT_COLUMN);
            String info = result.getString(INFO_COLUMN);
            String amount = result.getString(AMOUNT_COLUMN);

            // putting transaction in arraylist
            transactions.add(new Transactions(date,acount,info,amount));
        }
        result.close();
        transactionsDatabase.close();

        return  transactions;
    }
    //This method querys the Transactions table for
    //checking accountn transactions and returns an arraylist of transactions
    //objects
    public ArrayList<Transactions> getCheckingsTransactions(){
        ArrayList<Transactions> transactions = new ArrayList<>();
        transactionsDatabase = openHelper.getReadableDatabase();

        //the columns needed
        //  String[] cols = new String[] {"date","info","amount"};
        String sel = "account like 'C%'";

        Cursor result = transactionsDatabase.query(TRANSACTIONS_TABLE,
                null, sel,null, null,null, "date");

        // filling up arraylist with the results
        while(result.moveToNext()){
            Long dbId = result.getLong(ID_COLUMN);
            String date = result.getString(DATE_COLUMN);
            String acount = result.getString(ACCOUNT_COLUMN);
            String info = result.getString(INFO_COLUMN);
            String amount = result.getString(AMOUNT_COLUMN);

            // putting transaction in arraylist
            transactions.add(new Transactions(date,acount,info,amount));
        }
        result.close();
        transactionsDatabase.close();

        return  transactions;
    }
}
