package com.example.kenny.codeblocksbankingapp.model;

/**
 *
 * This class is the polo for each transaction made on this app
 * transaction can be made with all accounts
 */

public class Transactions {
    private String account; // the type of account, C for checking
    // S for Savings and I for investment

    private String date; //date of transaction
    private String info; // Debit or credit and to/from who
    private String amount; // the amount
    private long dbId; // the id of the transaction on the database

    public Transactions(String account, String date, String info, String amount) {
        this.account = account;
        this.date = date;
        this.info = info;
        this.amount = amount;
    }
    public Transactions(String account, String date, String info, String amount, long dbIb) {
       this(account,date,info,amount);
        this.dbId = dbId ;
    }
    public void setAccount(String account) {
        this.account = account;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public String getAccount() {
        return account;
    }

    public String getDate() {
        return date;
    }

    public String getInfo() {
        return info;
    }

    public String getAmount() {
        return amount;
    }

    public long getDbId() {
        return dbId;
    }
}
