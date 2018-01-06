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

   public int currentUserAccessNo;
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
        if(imageViewButtonID == R.id.btn_savingsImage){
            first_account_discription_textview = fragmentView.
                    findViewById(R.id.txt_accounts_balance_discription);
            second_account_discription_textview = fragmentView.
                    findViewById(R.id.txt_accounts_transactions_discription);
            first_account_discription_textview.setText(getResources().getString(R.string.saving_total));
            second_account_discription_textview.setText(String.format(
                    getResources().getString(R.string.transactions_message_for_account_page), 500.00));
        }

        if(imageViewButtonID == R.id.btn_checkingImage){
            first_account_discription_textview = fragmentView.
                    findViewById(R.id.txt_accounts_balance_discription);
            second_account_discription_textview = fragmentView.
                    findViewById(R.id.txt_accounts_transactions_discription);
            first_account_discription_textview.setText(getResources().getString(R.string.checkings_funds));
            second_account_discription_textview.setText(String.format(
                    getResources().getString(R.string.transactions_message_for_account_page), 500.00));
        }

        if(imageViewButtonID == R.id.btn_investmentsImage){
            first_account_discription_textview = fragmentView.
                    findViewById(R.id.txt_accounts_balance_discription);
            second_account_discription_textview = fragmentView.
                    findViewById(R.id.txt_accounts_transactions_discription);
            first_account_discription_textview.setText(getResources().getString(R.string.investments_funds));
            second_account_discription_textview.setText(String.format(
                    getResources().getString(R.string.transactions_message_for_account_page), 500.00));
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
