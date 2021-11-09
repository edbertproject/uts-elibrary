package com.uts.elibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StoreActivity extends AppCompatActivity {
    //Declare Variable
    BookCRUDHelper crudHelper;
    Button submitButton, backButton;
    TextView titleText, descriptionText, authorText, publisherText, yearText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        crudHelper = new BookCRUDHelper(this);

        //Initiate variable with id on Store XML
        titleText = findViewById(R.id.titleTextView);
        descriptionText = findViewById(R.id.descriptionTextView);
        authorText = findViewById(R.id.authorTextView);
        publisherText = findViewById(R.id.publisherTextView);
        yearText = findViewById(R.id.yearTextView);
        submitButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);

        //Set onClick Save Button to insert new BookModel values
        submitButton.setOnClickListener(view -> {
            crudHelper.insert(new BookModel(
                    titleText.getText().toString(),
                    descriptionText.getText().toString(),
                    authorText.getText().toString(),
                    publisherText.getText().toString(),
                    Integer.parseInt(yearText.getText().toString())
            ));

            //Show Successful Message
            Toast.makeText(getApplicationContext(), "Successfully create book", Toast.LENGTH_LONG).show();

            //Refresh List
            HomeActivity.current.RefreshList();
            finish();
        });

        //Set onClick back button to destroy current activity
        backButton.setOnClickListener(view -> finish());
    }
}