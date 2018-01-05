package com.example.kenny.codeblocksbankingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

    EditText edtAccessCardNo;
    EditText edtPassword;
    TextView txtErrorDisplay;

    Button btnLogin;

    //bank instance
    BankActivity masterBank = new BankActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //This to test BankActivity remove once we get this activity working
        //and properly launching BankActivity after user authentication
        //Intent intent = new Intent(getApplicationContext(), BankActivity.class);
        //startActivity(intent);


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

                    boolean checkVerify = masterBank.isVerifiedUser(accessNoInput, passwordInput);

                    if(checkVerify){
                        Intent intent = new Intent(getApplicationContext(), BankActivity.class);
                        startActivity(intent);
                    }else{
                        txtErrorDisplay.setText("Unable to login.");
                    }
                }else{
                    edtAccessCardNo.setHint("This is a required field");
                    edtPassword.setHint("This is a required field");
                }

            }
        });

    }

}
