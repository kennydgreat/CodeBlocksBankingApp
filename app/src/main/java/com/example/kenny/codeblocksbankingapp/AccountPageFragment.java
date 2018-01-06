package com.example.kenny.codeblocksbankingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/*This fragment handles the Savings Account page where users can
* 1) See their balance
* 2) See the transaction on this account*/
public class AccountPageFragment extends Fragment {
    //Fields
// The root UI of the fragment
   private View fragmentView;
    //This textview is tells the user their account balance
    // and summary about their Savings account
   private TextView first_account_discription_textview;

   private TextView second_account_discription_textview ;


    //This listView is for the list of transactions on the checking
    // account
   private ListView transaction_listview;

    // This list view is for the header of the transaction listView

   private int currentUserAccessNo;
   private int imageViewButtonID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        //getting the current user sent by BankActivity via setArgument
        currentUserAccessNo = this.getArguments().getInt("CURRENT USER ACCESS NUMBER");
        imageViewButtonID = this.getArguments().getInt("IMAGEVIEW BUTTON ID");
        fragmentView = inflater.inflate(R.layout.account_page, container, false);
        setUpViews(imageViewButtonID);
        displayTransactions(imageViewButtonID);
        return fragmentView;
    }
    //This method sets up the view to display account information
    //depending on what account is to displayed on fragment
    public void setUpViews(int imageViewButtonID){
        if(imageViewButtonID == R.id.savings_imageview_button){
            first_account_discription_textview = fragmentView.
                    findViewById(R.id.txt_accounts_balance_discription);
            second_account_discription_textview = fragmentView.
                    findViewById(R.id.txt_accounts_transactions_discription);
            first_account_discription_textview.setText(getResources().getString(R.string.saving_total));
            second_account_discription_textview.setText(String.format(
                    getResources().getString(R.string.transactions_message_for_account_page), 500.00));
        }

        if(imageViewButtonID == R.id.checkings_imageview_button){
            first_account_discription_textview = fragmentView.
                    findViewById(R.id.txt_accounts_balance_discription);
            second_account_discription_textview = fragmentView.
                    findViewById(R.id.txt_accounts_transactions_discription);
            first_account_discription_textview.setText(getResources().getString(R.string.checkings_funds));
            second_account_discription_textview.setText(String.format(
                    getResources().getString(R.string.transactions_message_for_account_page), 500.00));
        }

        if(imageViewButtonID == R.id.investments_imageview_button){
            first_account_discription_textview = fragmentView.
                    findViewById(R.id.txt_accounts_balance_discription);
            second_account_discription_textview = fragmentView.
                    findViewById(R.id.txt_accounts_transactions_discription);
            first_account_discription_textview.setText(getResources().getString(R.string.investments_funds));
            second_account_discription_textview.setText(String.format(
                    getResources().getString(R.string.transactions_message_for_account_page), 500.00));
        }

    }

    private void displayTransactions(int imageViewButtonID){
        transaction_listview = fragmentView.findViewById(R.id.accounts_transaction_listview);
        ArrayList<String> info = new ArrayList<String>();
        //{"WTD", "Staples Inc.", "Presto", "Apple Inc."}
        info.add("WTD");
        info.add("WTD");
        info.add("Staples Inc.");
        info.add("Presto");
        info.add("Apple Inc.");
        TransactionListViewAdapter transactionListViewAdapter;

        if (imageViewButtonID == R.id.checkings_imageview_button){
            transactionListViewAdapter = new TransactionListViewAdapter(
                    this.getContext(),
                    R.layout.transactions_listview_layout,
                    info
            );
            transaction_listview.setAdapter(transactionListViewAdapter);
        }
    }
}
