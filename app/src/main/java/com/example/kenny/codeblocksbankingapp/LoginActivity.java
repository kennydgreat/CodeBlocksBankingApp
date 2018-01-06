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

        getAndSaveDummyHolders();


        edtAccessCardNo = findViewById(R.id.edt_accessCard);
        edtPassword = findViewById(R.id.edt_password);

        txtErrorDisplay = findViewById(R.id.txt_errorDisplay);

        btnLogin = findViewById(R.id.btn_logIn);

        final BankActivity masterBank = new BankActivity();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtAccessCardNo.length() != 0 || edtPassword.length() != 0){
                    String accessNoInput = edtAccessCardNo.getText().toString();
                    String passwordInput = edtPassword.getText().toString();

                    boolean checkVerify = isVerifiedUser(accessNoInput, passwordInput);

                    if(checkVerify){
                        //if verified start bank activity and pass the access card number
                        Intent openBankACcount = new Intent(getApplicationContext(), BankActivity.class);
                        openBankACcount.putExtra("customerAccountNumber",accessNoInput);
                        startActivity(openBankACcount);
                    }else{
                        txtErrorDisplay.setText("* Unable to login. Check that you entered the information correctly.");
                    }
                }else{
                    edtAccessCardNo.setHint("This is a required field");
                    edtPassword.setHint("This is a required field");
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

    //verify the user using method in customer database
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
