package com.uts.elibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    BookCRUDHelper crudHelper;
    Button submitButton, backButton;
    TextView titleText, descriptionText, authorText, publisherText, yearText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        crudHelper = new BookCRUDHelper(this);
        titleText = findViewById(R.id.titleTextView);
        descriptionText = findViewById(R.id.descriptionTextView);
        authorText = findViewById(R.id.authorTextView);
        publisherText = findViewById(R.id.publisherTextView);
        yearText = findViewById(R.id.yearTextView);
        submitButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);

        BookModel book = crudHelper.find(getIntent().getIntExtra("bookId",0));
        titleText.setText(book.getTitle());
        descriptionText.setText(book.getDescription());
        authorText.setText(book.getAuthor());
        publisherText.setText(book.getPublisher());
        yearText.setText(String.valueOf(book.getYear()));

        submitButton.setOnClickListener(view -> {
            crudHelper.update(new BookModel(
                    book.getId(),
                    titleText.getText().toString(),
                    descriptionText.getText().toString(),
                    authorText.getText().toString(),
                    publisherText.getText().toString(),
                    Integer.parseInt(yearText.getText().toString())
            ));
            Toast.makeText(getApplicationContext(), "Successfully update book", Toast.LENGTH_LONG).show();
            HomeActivity.current.RefreshList();
            finish();
        });

        backButton.setOnClickListener(view -> finish());
    }
}