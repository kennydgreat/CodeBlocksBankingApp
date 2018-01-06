package com.example.kenny.codeblocksbankingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kenny.codeblocksbankingapp.db.TransactionsDb;
import com.example.kenny.codeblocksbankingapp.model.Transactions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Fragment for the make transactions page
 */

public class MakeTransactionFragment extends Fragment {

    // The root UI of the fragment
    private View fragmentView;
    //TextView
    private TextView txt_account_balance_on_make_transaction_page;

    private Spinner spinner;
    //Buttons
    private Button cancelButton;
    private Button sendButton;
    //EditText
    private EditText amountToSendEditText;
    private EditText whoToSendToEditText;
    private String accountChosen;
    private String[] currentUserInfo ;
    private int indexAccountBalanceInfo;
    private int indexOfChosenAccount;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        //getting the current user sent by BankActivity via setArgument
        fragmentView = inflater.inflate(R.layout.fragment_make_transaction_page, container, false);
        currentUserInfo = getArguments().getStringArray("CURRENT CUSTOMER INFO ARRAY");
        txt_account_balance_on_make_transaction_page = fragmentView.findViewById(
                R.id.txt_account_balance_on_make_transaction_page);
        setUpEditText();
        setUpButtons();
        setUpSpinner();
        return fragmentView;
    }

    private void setUpEditText() {
        whoToSendToEditText = fragmentView.findViewById(R.id.edt_who_send_to);
        amountToSendEditText = fragmentView.findViewById(R.id.edt_amount_to_send);
    }

    private void setUpButtons() {
        cancelButton = fragmentView.findViewById(R.id.cancel_transaction_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        sendButton = fragmentView.findViewById(R.id.button_make_transaction);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(amountToSendEditText.getText().toString().equals("")){
                    Toast.makeText(getContext(),"Please enter an amount",Toast.LENGTH_LONG).show();
                }
                else if(whoToSendToEditText.getText().toString().equals("")){
                    Toast.makeText(getContext(),"Please enter an name",Toast.LENGTH_LONG).show();
                }
                else if(
                 isTransactionPossible(amountToSendEditText.getText().toString(),
                        currentUserInfo[indexAccountBalanceInfo])){
                    currentUserInfo[indexAccountBalanceInfo] = makeTransaction(amountToSendEditText.getText().toString(),
                            currentUserInfo[indexAccountBalanceInfo]);

                    addTransactionToDB();

                    //giving the main user_page the undated balance
                    Bundle arg = new Bundle();
                    arg.putStringArray("CURRENT CUSTOMER INFO ARRAY",currentUserInfo);
                    getActivity().getSupportFragmentManager().findFragmentById(R.id.main_user_page_container).setArguments(arg);
                    amountToSendEditText.setText("");
                    whoToSendToEditText.setText("");
                    // display update balance
                    if ( indexOfChosenAccount == 0)//Checking
                    {//Getting the checking balance or funds
                        txt_account_balance_on_make_transaction_page.setText(String.format(getString(
                                R.string.checking_account_balance_message_on_make_transaction_page),currentUserInfo[2]));
                    } else //savings
                    {//Getting the checking balance or funds
                        txt_account_balance_on_make_transaction_page.setText(String.format(getString(
                                R.string.saving_account_balance_message_on_make_transaction_page), currentUserInfo[4]));
                    }
                }




            }
        });

    }
    //Method to do the actually trnsaction
    private String makeTransaction(String amount, String balanc) {
        double balanceAfter = Double.valueOf(balanc) - Double.valueOf(amount);
        return String.valueOf(balanceAfter);
    }


    //Sets up spinner and their listeners
    public void setUpSpinner(){
        spinner = fragmentView.findViewById(R.id.spinner_what_account);

        final String[] accountChoice = getResources().getStringArray(R.array.spinner_item);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getContext(),
                android.R.layout.simple_spinner_item,
                accountChoice
        );

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                indexOfChosenAccount = i;
                if (i == 0)//Checking
                {
                    accountChosen = accountChoice[i];
                    //Getting the checking balance or funds
                    txt_account_balance_on_make_transaction_page.setText(String.format(getString(
                            R.string.checking_account_balance_message_on_make_transaction_page),currentUserInfo[2]));
                            indexAccountBalanceInfo = 2;
                } else //savings
                {
                    accountChosen = accountChoice[i];
                    //Getting the checking balance or funds
                    txt_account_balance_on_make_transaction_page.setText(String.format(getString(
                            R.string.saving_account_balance_message_on_make_transaction_page), currentUserInfo[4]));
                    indexAccountBalanceInfo = 4;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public boolean isTransactionPossible(String amount, String balance){

        if (Double.valueOf(amount) > Double.valueOf(balance)){
            Toast.makeText(getContext(),"Not enough funds",Toast.LENGTH_LONG).show();
            return false;
        }else return true;

    }
    public void addTransactionToDB(){
        Transactions transaction = new Transactions(
                accountChosen,
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()),
                whoToSendToEditText.getText().toString(),
                amountToSendEditText.getText().toString()

        );
        //Saving the trnsaction in the database
        TransactionsDb transactionsDb = new TransactionsDb(getContext());
        transactionsDb.saveTransaction(transaction);
        Toast.makeText(getContext(),"Amount have been sent.", Toast.LENGTH_LONG).show();
    }
}
