package com.example.kenny.codeblocksbankingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kenny.codeblocksbankingapp.db.TransactionsDb;
import com.example.kenny.codeblocksbankingapp.model.CustomerDatabase;
import com.example.kenny.codeblocksbankingapp.model.Transactions;

import org.w3c.dom.Text;

import java.util.ArrayList;

/*This fragment handles the Savings Account page where users can
* 1) See their balance
* 2) See the transaction on this account*/
public class AccountPageFragment extends Fragment {
    //Fields
// The root UI of the fragment
   private View fragmentView;

   //text views to display account information
   public TextView txtAccountBalance;
   public TextView txtAccountNumber;


    //This listView is for the list of transactions on the checking
    // account
   public ListView transaction_listview;

    // This list view is for the header of the transaction listView
   public int currentUserAccessNo;
   public int imageViewButtonID;

   //info will store current user info passed in from bank
   public String[] info;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        //getting the current user sent by BankActivity via setArgument
        currentUserAccessNo = this.getArguments().getInt("CURRENT USER ACCESS NUMBER");
        imageViewButtonID = this.getArguments().getInt("IMAGEVIEW BUTTON ID");
        fragmentView = inflater.inflate(R.layout.account_page, container, false);

        info = getArguments().getStringArray("CURRENT CUSTOMER INFO ARRAY");

        setUpViews(imageViewButtonID);
        displayTransactions(imageViewButtonID);
        return fragmentView;
    }

    //This method sets up the view to display account information
    //depending on what account is to displayed on fragment
    public void setUpViews(int imageViewButtonID){
        if(imageViewButtonID == R.id.btn_savingsImage){
            
            txtAccountBalance = fragmentView.findViewById(R.id.txt_accountBalance);
            txtAccountNumber = fragmentView.findViewById(R.id.txt_accountNumber);

            //set the views with the info
            txtAccountNumber.setText(getResources().getString(R.string.noString, info[3]));
            txtAccountBalance.setText(getResources().getString(R.string.balanceString, info[4]));
        }

        if(imageViewButtonID == R.id.btn_checkingImage){
            txtAccountBalance = fragmentView.findViewById(R.id.txt_accountBalance);
            txtAccountNumber = fragmentView.findViewById(R.id.txt_accountNumber);

            //set the views with the info
            txtAccountNumber.setText(getResources().getString(R.string.noString, info[1]));
            txtAccountBalance.setText(getResources().getString(R.string.balanceString, info[2]));
        }

        if(imageViewButtonID == R.id.btn_investmentsImage){
            //This data is the same as checking account just as a place holder
            //implementation of an investment account was not places in the database
            //due to time constraints
            //TODO: add data about the user investment account in cust database
            txtAccountBalance = fragmentView.findViewById(R.id.txt_accountBalance);
            txtAccountNumber = fragmentView.findViewById(R.id.txt_accountNumber);

            //set the views with the info
            txtAccountNumber.setText(getResources().getString(R.string.noString, info[3]));
            txtAccountBalance.setText(getResources().getString(R.string.balanceString, info[4]));
        }

    }
    // displays the transaction on the listview depending on what account
    //button launched the fragment
    private void displayTransactions(int imageViewButtonID){
        transaction_listview = fragmentView.findViewById(R.id.accounts_transaction_listview);
        TransactionsDb transactionsDb = new TransactionsDb(getContext());
        ArrayList<Transactions> transactions;
        TransactionListViewAdapter transactionListViewAdapter;

        if (imageViewButtonID == R.id.btn_savingsImage){
            transactions = transactionsDb.getSavingsTransactions();
            transactionListViewAdapter = new TransactionListViewAdapter(
                    this.getContext(),
                    R.layout.transactions_listview_layout,
                    transactions
            );
            transaction_listview.setAdapter(transactionListViewAdapter);
        }

        if (imageViewButtonID == R.id.btn_checkingImage){
            transactions = transactionsDb.getCheckingsTransactions();
            transactionListViewAdapter = new TransactionListViewAdapter(
                    this.getContext(),
                    R.layout.transactions_listview_layout,
                    transactions
            );
            transaction_listview.setAdapter(transactionListViewAdapter);
        }
    }
}
