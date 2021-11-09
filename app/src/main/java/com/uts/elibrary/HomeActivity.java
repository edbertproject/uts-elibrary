package com.uts.elibrary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    //Declare Variable
    List<BookModel> books;
    BookAdapter bookAdapter;
    ListView bookListView;

    public static HomeActivity current;

    BookCRUDHelper crudHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        crudHelper = new BookCRUDHelper(this);
        current = this;

        //Set action when clicked Floating Action Button on Bottom Right move to Store Activity
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, StoreActivity.class);
            startActivity(intent);
        });
        RefreshList();
    }

    public void RefreshList() {
        List<BookModel> row = crudHelper.index();
        books = new ArrayList<>(row.size());
        books.addAll(row);

        bookAdapter = new BookAdapter(HomeActivity.this, books);

        bookListView = findViewById(R.id.bookList);
        //Set Adapter
        bookListView.setAdapter(bookAdapter);
        bookListView.setSelected(true);

        //Set action for onClick one of the item in ListView
        bookListView.setOnItemClickListener((adapterView, view, position, id) -> {
            final BookModel book = new BookModel(
                    books.get(position).getId(),
                    books.get(position).getTitle(),
                    books.get(position).getDescription(),
                    books.get(position).getAuthor(),
                    books.get(position).getPublisher(),
                    books.get(position).getYear()
            );

            //Make Floating Alert Dialog with 2 Option "Edit" or "Delete"
            final CharSequence[] dialogitem = {"Edit Book", "Delete Book"};
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setTitle("Option");
            builder.setItems(dialogitem, (dialog, item) -> {
                switch (item) {
                    case 0:
                        //Set onClick dialogItem 0 : "Edit" to move to Update Activity
                        Intent in = new Intent(getApplicationContext(), UpdateActivity.class);
                        in.putExtra("bookId", book.getId());
                        startActivity(in);
                        break;
                    case 1:
                        //Set onClick dialogItem 1 : "Delete" to move to Update Activity
                        crudHelper.delete(book.getId());
                        Toast.makeText(getApplicationContext(), "Successfully delete book", Toast.LENGTH_LONG).show();
                        RefreshList();
                        break;
                }
            });
            builder.create().show();
        });
        ((BookAdapter) bookListView.getAdapter()).notifyDataSetInvalidated();
    }
}