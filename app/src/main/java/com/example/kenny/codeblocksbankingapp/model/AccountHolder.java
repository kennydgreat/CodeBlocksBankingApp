package com.example.kenny.codeblocksbankingapp.model;

/**
 * Created by diego on 2018-01-02.
 */

public class AccountHolder{

    //account holder information
    private String name;
    private int accessCardNo;
    private String password;

    //will be used as foreign keys to other databases
    private int checkingAccountNo;
    private int savingsAccountNo;

    //funds in account types
    private double checkingAccountFunds;
    private double savingsAccountFunds;

    private long dbId;

    public AccountHolder(String name, int accessCardNo, String password, int checkingAccountNo, int savingsAccountNo, double checkingAccountFunds, double savingsAccountFunds ){
        this.name = name;
        this.accessCardNo =accessCardNo;
        this.password = password;
        this.checkingAccountNo = checkingAccountNo;
        this.savingsAccountNo = savingsAccountNo;
        this.checkingAccountFunds = checkingAccountFunds;
        this.savingsAccountFunds = savingsAccountFunds;
    }

    public AccountHolder(String name, int accessCardNo, String password, int checkingAccountNo, int savingsAccountNo, double checkingAccountFunds, double savingsAccountFunds, long dbId){
        this(name, accessCardNo, password, checkingAccountNo, savingsAccountNo, checkingAccountFunds, savingsAccountFunds);
        this.dbId = dbId;
    }

    //getters and setters for the account holder information
    // will be used by the bank class to update the views and open new accounts
    public String getName(){
        return name;
    }

    public void setName(){
        this.name = name;
    }

    public int getAccessCardNo(){
        return accessCardNo;
    }

    public void setAccessCardNo(){
        this.accessCardNo = accessCardNo;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(){
        this.password = password;
    }

    public int getCheckingAccountNo(){
        return checkingAccountNo;
    }

    public void setCheckingAccountNo(){
        this.checkingAccountNo = checkingAccountNo;
    }

    public int getSavingsAccountNo(){
        return savingsAccountNo;
    }

    public void setSavingsAccountNo(){
        this.savingsAccountNo = savingsAccountNo;
    }

    public double getCheckingAccountFunds(){
        return checkingAccountFunds;
    }

    public void setCheckingAccountFunds(){
        this.checkingAccountFunds = checkingAccountFunds;
    }

    public double getSavingsAccountFunds(){
        return savingsAccountFunds;
    }

    public void setSavingsAccountFunds(){
        this.savingsAccountFunds = savingsAccountFunds;
    }

    public long getDbId(){
        return dbId;
    }

    public void setDbId(long dbId){
        this.dbId = dbId;
    }
}
