package com.example.kenny.codeblocksbankingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kenny.codeblocksbankingapp.model.AccountHolder;
import com.example.kenny.codeblocksbankingapp.model.CustomerDatabase;

public class LoginActivity extends Activity {

    //arrays that will store the dummy data
    String[] holderNames;
    int[] accessNo;
    String[] passwords;
    int[] checkAccNo;
    int [] savAccNo;
    String[] checkAccFunds;
    String[] savAccFunds;

    //views
    EditText edtAccessCardNo;
    EditText edtPassword;

    TextView txtErrorDisplay;

    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //get the content of the string-arrays from strings.xml
        holderNames = getResources().getStringArray(R.array.holder_names);
        accessNo = getResources().getIntArray(R.array.dummy_access_card_no);
        passwords = getResources().getStringArray(R.array.dummy_password);
        checkAccNo = getResources().getIntArray(R.array.dummy_checking_account_no);
        savAccNo = getResources().getIntArray(R.array.dummy_savings_account_no);
        checkAccFunds = getResources().getStringArray(R.array.dummy_checking_account_funds);
        savAccFunds = getResources().getStringArray(R.array.dummy_savings_account_funds);

        //call method to populate database
        getAndSaveDummyHolders();

        //initialize views
        edtAccessCardNo = findViewById(R.id.edt_accessCard);
        edtPassword = findViewById(R.id.edt_password);

        txtErrorDisplay = findViewById(R.id.txt_errorDisplay);

        btnLogin = findViewById(R.id.btn_logIn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if there is data in edit texts
                if(edtAccessCardNo.length() != 0 || edtPassword.length() != 0){
                    String accessNoInput = edtAccessCardNo.getText().toString();
                    String passwordInput = edtPassword.getText().toString();
                    //store results of isVerified to allow/deny access
                    boolean checkVerify = isVerifiedUser(accessNoInput, passwordInput);

                    if(checkVerify){
                        //if verified start bank activity and pass the access card number
                        Intent accessBank = new Intent(getApplicationContext(), BankActivity.class);
                        accessBank.putExtra("customerAccountNumber",accessNoInput);
                        startActivity(accessBank);
                    }else{
                        txtErrorDisplay.setText(getResources().getString(R.string.errorLoggingInString));
                    }
                }else{
                    edtAccessCardNo.setHint(getResources().getString(R.string.requiredFieldString));
                    edtPassword.setHint(getResources().getString(R.string.requiredFieldString));
                }
            }
        });

    }

    //Method to create dummy accountHolder objects
    public void getAndSaveDummyHolders(){
        CustomerDatabase custDb = new CustomerDatabase(this);

        for(int i = 0; i < 5; i++){
            AccountHolder holder = new AccountHolder(
                    holderNames[i], accessNo[i],
                    passwords[i], checkAccNo[i], savAccNo[i],
                    checkAccFunds[i], savAccFunds[i]);

            custDb.saveAccHolder(holder);
        }


    }

    //provide credentials to database to verify user
    public boolean isVerifiedUser(String userAccessCard, String userPassword){
        CustomerDatabase custDb = new CustomerDatabase(this);

        int check = custDb.login(userAccessCard, userPassword);

        if(check == 1){
            return true;
        }else{
            return false;
        }
    }


}
