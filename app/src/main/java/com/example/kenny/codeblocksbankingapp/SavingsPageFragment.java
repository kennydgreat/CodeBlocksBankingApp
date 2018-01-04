package com.example.kenny.codeblocksbankingapp;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
/*This fragment handles the Savings Account page where users can
* 1) See their balance
* 2) See the transaction on this account*/
public class SavingsPageFragment extends Fragment {
    //Fields

    //This textview is tells the user their account balance
    // and summary about their Savings account
   private TextView saving_account_discription_textview ;

    //This listView is for the list of transactions on the checking
    // account
   private ListView savings_transaction_listview;

    // This list view is for the header of the transaction listView
    private ListView savings_activity_transaction_header_listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_savings_page, container, false);
    }


}
