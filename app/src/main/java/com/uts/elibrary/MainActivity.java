package com.uts.elibrary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    //Declare Variable
    EditText usernameInput;
    EditText passwordInput;

    TextInputLayout usernameLayout;
    TextInputLayout passwordLayout;

    Button loginButton;

    LoginHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqliteHelper = new LoginHelper(this);
        init();

        //Assign Action when Login button Clicked
        loginButton.setOnClickListener(view -> {

            //Validate Password and Username
            if (validateUsername()&&validatePassword()) {
                //Get Data from EditText in Main Activity
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                //Authenticate User
                UserModel currentUser = sqliteHelper.Authenticate(new UserModel(null, username, null, password));

                //Checking Authentication is Successful or Not
                if (currentUser != null) {
                    //Message if Success
                    Toast.makeText(getApplicationContext(), "Successfully logged in!", Toast.LENGTH_LONG).show();

                    //Move to Home Activity with bring Username
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("username",username);
                    startActivity(intent);
                    finish();
                } else {
                    //Message if Failed to login
                    Toast.makeText(getApplicationContext(), "Username or password is incorrect, please try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Initiate variable with id on Main XML and set onClick to Register Button
    private void init() {
        //Move to Register Activity
        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        //Initiate variable with id on Home XML
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        usernameLayout = findViewById(R.id.username_layout);
        passwordLayout = findViewById(R.id.password_layout);
        loginButton = findViewById(R.id.login_button);
    }

    //Validate Username
    private boolean validateUsername() {
        boolean isValid;

        String username = usernameInput.getText().toString();

        if (username.isEmpty()) {
            isValid = false;
            usernameLayout.setError("Please enter valid username!");
        } else if (username.length() >= 4) {
            isValid = true;
            usernameLayout.setError(null);
        } else {
            isValid = false;
            usernameLayout.setError("Username is too shor!");
        }
        return isValid;
    }

    //Validate Password
    private boolean validatePassword() {
        boolean isValid;

        String password = passwordInput.getText().toString();

        if (password.isEmpty()) {
            isValid = false;
            passwordLayout.setError("Please enter valid username!");
        } else if (password.length() >= 4) {
            isValid = true;
            passwordLayout.setError(null);
        } else {
            isValid = false;
            passwordLayout.setError("Username is too shor!");
        }
        return isValid;
    }
}