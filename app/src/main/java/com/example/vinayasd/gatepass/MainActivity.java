package com.example.vinayasd.gatepass;

import android.content.Intent;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vinayasd.gatepass.sql.DatabaseAccess;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity activity = MainActivity.this;


    private EditText editTextUsername;
    private EditText editTextPassword;


    private Button buttonLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        initViews();
        initListeners();

    }


    private void initViews() {


        editTextUsername = (EditText) findViewById(R.id.login_username);
        editTextPassword = (EditText) findViewById(R.id.login_password);

        editTextUsername.setText(getIntent().getStringExtra("USERNAME"));

        buttonLogin = (Button) findViewById(R.id.login);

    }

    private void initListeners() {
        buttonLogin.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                verifyFromSQLite();
                break;
        }
    }


    private void verifyFromSQLite() {

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

        if (databaseAccess.checkStudent(editTextUsername.getText().toString().trim()
                , editTextPassword.getText().toString().trim())) {


            Intent accountsIntent = new Intent(activity, Main2Activity.class);
            accountsIntent.putExtra("USERNAME", editTextUsername.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);


        } else if (databaseAccess.checkParent(editTextUsername.getText().toString().trim()
                , editTextPassword.getText().toString().trim())) {


            Intent accountsIntent = new Intent(activity, ParentActivity.class);
            accountsIntent.putExtra("USERNAME", editTextUsername.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);


        }
        else if (databaseAccess.checkWarden(editTextUsername.getText().toString().trim()
                , editTextPassword.getText().toString().trim())) {


            Intent accountsIntent = new Intent(activity, WardenActivity.class);
            accountsIntent.putExtra("USERNAME", editTextUsername.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);


        }else {
            Toast.makeText(activity, "Enter Correct details", Toast.LENGTH_SHORT).show();
        }
    }

    private void emptyInputEditText() {
        editTextUsername.setText(null);
        editTextPassword.setText(null);
    }


}
