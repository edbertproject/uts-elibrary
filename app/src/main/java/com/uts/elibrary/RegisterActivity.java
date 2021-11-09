package com.uts.elibrary;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    //Declare Variable
    EditText emailInput;
    EditText usernameInput;
    EditText passwordInput;

    TextInputLayout emailLayout;
    TextInputLayout usernameLayout;
    TextInputLayout passwordLayout;

    Button registerButton;

    LoginHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sqliteHelper = new LoginHelper(this);
        this.init();

        //Assign Action when Register button Clicked
        registerButton.setOnClickListener(view -> {

            //Validate Email, Username, and Password
            if (validateEmail()&&validateUsername()&&validatePassword()) {
                //Get Data from EditText in Register Activity
                String email = emailInput.getText().toString();
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                //Check in DB is there a User associated with this email
                if (!sqliteHelper.isEmailExists(email)) {
                    //if there is no same email and add new user
                    UserModel user = new UserModel(null, username, email, password);
                    sqliteHelper.addUser(user);
                    Toast.makeText(getApplicationContext(), "User created successfully! please login!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    //if there is same email
                    Toast.makeText(getApplicationContext(), "User already exists with same email!", Toast.LENGTH_LONG).show();
                }
            } else {
                //If one or more validation get false return
                Toast.makeText(getApplicationContext(), "Failed to register!", Toast.LENGTH_LONG).show();
            }
        });
    }

    //Initiate variable with id on Register XML and set onClick to Login Activity by Destroy this session
    private void init() {
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(view -> finish());

        emailInput = findViewById(R.id.email_input);
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        emailLayout = findViewById(R.id.email_layout);
        usernameLayout = findViewById(R.id.username_layout);
        passwordLayout = findViewById(R.id.password_layout);
        registerButton = findViewById(R.id.register_button);
    }

    //Validate Email
    private boolean validateEmail() {
        boolean isValid;

        String email = emailInput.getText().toString();

        Log.d("Email",email);

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false;
            emailLayout.setError("Please enter valid email!");
        } else {
            isValid = true;
            emailLayout.setError(null);
        }

        return isValid;
    }

    //Validate Username
    private boolean validateUsername() {
        boolean isValid;

        String username = usernameInput.getText().toString();

        Log.d("Username",username);

        if (username.isEmpty()) {
            isValid = false;
            usernameLayout.setError("Please enter valid username!");
        } else if (username.length() >= 4) {
            isValid = true;
            usernameLayout.setError(null);
        } else {
            isValid = false;
            usernameLayout.setError("Username is too short!");
        }

        return isValid;
    }

    //Validate Password
    private boolean validatePassword() {
        boolean isValid;

        String password = passwordInput.getText().toString();

        Log.d("Password",password);

        if (password.isEmpty()) {
            isValid = false;
            passwordLayout.setError("Please enter valid username!");
        } else if (password.length() >= 4) {
            isValid = true;
            passwordLayout.setError(null);
        } else {
            isValid = false;
            passwordLayout.setError("Username is too short!");
        }

        return isValid;
    }
}