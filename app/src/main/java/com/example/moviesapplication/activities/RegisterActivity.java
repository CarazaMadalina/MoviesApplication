package com.example.moviesapplication.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.moviesapplication.R;
import com.example.moviesapplication.model.User;
import com.example.moviesapplication.sql.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {
    //Declaration EditTexts
    EditText editTextUserName, editTextEmail, editTextPassword;

    //Declaration TextInputLayout
    TextInputLayout textInputLayoutUserName, textInputLayoutEmail, textInputLayoutPassword;

    //Declaration Button
    Button registerButton;

    //Declaration DatabaseHelper
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        databaseHelper = new DatabaseHelper(this);
        initTextViewLogin();
        initializingViews();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String UserName = editTextUserName.getText().toString();
                    String Email = editTextEmail.getText().toString();
                    String Password = editTextPassword.getText().toString();

                    if (!databaseHelper.isEmailExists(Email)) {
                        databaseHelper.addUser(new User(null, UserName, Email, Password));
                        Snackbar.make(registerButton, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);
                    } else {
                        Snackbar.make(registerButton, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    //this method used to set Login TextView click event
    private void initTextViewLogin() {
        TextView textViewLogin = findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //this method is used to connect XML views to its Objects
    private void initializingViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUserName = findViewById(R.id.editTextUserName);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        textInputLayoutUserName = findViewById(R.id.textInputLayoutUserName);
        registerButton = findViewById(R.id.buttonRegister);

    }

    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String username = editTextUserName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        //Handling validation for UserName field
        if (username.isEmpty()) {
            valid = false;
            textInputLayoutUserName.setError("Please enter valid username!");
        } else {
            if (username.length() > 5) {
                valid = true;
                textInputLayoutUserName.setError(null);
            } else {
                valid = false;
                textInputLayoutUserName.setError("Username is to short!");
            }
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            valid = false;
            textInputLayoutEmail.setError("Please enter valid email!");
        } else {
            valid = true;
            textInputLayoutEmail.setError(null);
        }

        //Handling validation for Password field
        if (password.isEmpty()) {
            valid = false;
            textInputLayoutPassword.setError("Please enter valid password!");
        } else {
            if (password.length() > 5) {
                valid = true;
                textInputLayoutPassword.setError(null);
            } else {
                valid = false;
                textInputLayoutPassword.setError("Password is to short!");
            }
        }
        return valid;
    }

}
