package com.uts.elibrary;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BookCRUDHelper extends SQLiteOpenHelper {
    //Database Name
    public static final String DATABASE_NAME = "book_db";
    //Database Version
    public static final int DATABASE_VERSION = 1;
    //Table Name
    public static final String TABLE_BOOKS = "books";

    //All Field in DB
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_PUBLISHER = "publisher";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_YEAR = "year";

    //declare valriable to contain query creating table
    public static final String SQL_TABLE_STUDENTS = "CREATE TABLE " + TABLE_BOOKS +
            " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_TITLE + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_AUTHOR + " TEXT, "
            + KEY_PUBLISHER + " TEXT, "
            + KEY_YEAR + " INTEGER "
            + " ) ";

    //Construct
    public BookCRUDHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //run query from SQL_TABLE_STUDENTS
        db.execSQL(SQL_TABLE_STUDENTS);
        //Add new books for example
        this.generateStudent(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }

    //Adding new example of books
    private void generateStudent(SQLiteDatabase db) {
        this.insertWithContext(new BookModel(
                "Atomic Habits: Perubahan Kecil yang Memberikan Hasil Luar Biasa",
                "Orang mengira ketika Anda ingin mengubah hidup, Anda perlu memikirkan hal-hal besar. Namun pakar kebiasaan terkenal kelas dunia James Clear telah menemukan sebuah cara",
                "James Clear",
                "Gramedia Pustaka Utama",
                2019
        ),db);
    }


    public List<BookModel> index() {
        List<BookModel> books = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_BOOKS;
        SQLiteDatabase database = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                books.add(new BookModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5)
                ));
            } while (cursor.moveToNext());
        }
        database.close();
        return books;
    }

    public BookModel find(int id) {
        BookModel book = null;

        //Initiate to Write DB
        SQLiteDatabase database = this.getWritableDatabase();
        //Query Selecting a Record in DB
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(
                    "SELECT * FROM "+TABLE_BOOKS+" WHERE "+KEY_ID+" = '"+id+ "'",
                null
        );

        //Get Values from BookModel
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            cursor.moveToPosition(0);
            book = new BookModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5)
            );
        }
        database.close();
        return book;
    }

    public void insert(BookModel book) {
        //Initiate to write DB
        SQLiteDatabase db = this.getWritableDatabase();
        //Put Values from BookModel to Vaariable DB
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, book.getTitle());
        values.put(KEY_AUTHOR, book.getAuthor());
        values.put(KEY_DESCRIPTION, book.getDescription());
        values.put(KEY_PUBLISHER, book.getPublisher());
        values.put(KEY_YEAR, book.getYear());

        //Insert new record to DB
        db.insert(TABLE_BOOKS, null, values);
        db.close();
    }

    public void insertWithContext(BookModel book, SQLiteDatabase db) {
        //Put Values from BookModel to Vaariable DB
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, book.getTitle());
        values.put(KEY_AUTHOR, book.getAuthor());
        values.put(KEY_DESCRIPTION, book.getDescription());
        values.put(KEY_PUBLISHER, book.getPublisher());
        values.put(KEY_YEAR, book.getYear());

        //Insert new Record in DB
        db.insert(TABLE_BOOKS, null, values);
    }

    public void update(BookModel book) {
        //Initiate to write DB
        SQLiteDatabase db = this.getWritableDatabase();
        //Put Values from BookModel to Vaariable DB
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, book.getTitle());
        values.put(KEY_AUTHOR, book.getAuthor());
        values.put(KEY_DESCRIPTION, book.getDescription());
        values.put(KEY_PUBLISHER, book.getPublisher());
        values.put(KEY_YEAR, book.getYear());

        //Update values of record in DB where has the same ID
        db.update(TABLE_BOOKS, values,"id = ?",new String[]{String.valueOf(book.getId())});
        db.close();
    }

    public void delete(int id) {
        //Initiate to write DB
        SQLiteDatabase db = this.getWritableDatabase();

        //Delete Record in DB
        db.delete(TABLE_BOOKS,"id = ?",new String[]{String.valueOf(id)});
        db.close();
    }
}
