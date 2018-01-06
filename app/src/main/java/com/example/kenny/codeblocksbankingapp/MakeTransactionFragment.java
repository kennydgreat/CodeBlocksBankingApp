package com.example.kenny.codeblocksbankingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Fragment for the make transactions page
 */

public class MakeTransactionFragment extends Fragment {

    // The root UI of the fragment
    private View fragmentView;

    private Spinner spinner;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        //getting the current user sent by BankActivity via setArgument
        fragmentView = inflater.inflate(R.layout.fragment_make_transaction_page, container, false);
        setUpSpinner();
        return fragmentView;
    }
    //Sets up spinner
    public void setUpSpinner(){
        spinner = fragmentView.findViewById(R.id.spinner_what_account);

        String[] accountChoice = getResources().getStringArray(R.array.spinner_item);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getContext(),
                android.R.layout.simple_spinner_item,
                accountChoice
        );

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinner.setAdapter(adapter);
    }
}
