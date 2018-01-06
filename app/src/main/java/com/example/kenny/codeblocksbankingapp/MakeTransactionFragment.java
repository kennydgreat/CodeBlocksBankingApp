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

    private Spinner spinner;
    //Buttons
    private Button cancelButton;
    private Button sendButton;
    //EditText
    private EditText amountToSendEditText;
    private EditText whoToSendToEditText;
    private String accountChosen;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        //getting the current user sent by BankActivity via setArgument
        fragmentView = inflater.inflate(R.layout.fragment_make_transaction_page, container, false);
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

                amountToSendEditText.setText("");
                whoToSendToEditText.setText("");

            }
        });

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
                accountChosen = accountChoice[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
