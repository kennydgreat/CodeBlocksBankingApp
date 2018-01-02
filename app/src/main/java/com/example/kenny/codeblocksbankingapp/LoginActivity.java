package com.example.kenny.codeblocksbankingapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    EditText edtAccessCardNo;
    EditText edtPassword;

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //This to test MainUserPageActivity remove once we get this activity working
        //and properly launching MainUserPageActivity after user authentication
        Intent intent = new Intent(getApplicationContext(), MainUserPageActivity.class);
        startActivity(intent);

        edtAccessCardNo = findViewById(R.id.edt_accessCard);
        edtPassword = findViewById(R.id.edt_password);

        btnLogin = findViewById(R.id.btn_logIn);

    }

}
