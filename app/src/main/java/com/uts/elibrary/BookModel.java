package com.uts.elibrary;

public class BookModel {
    //Declare Variable
    public int id, year;
    public String title, description, author, publisher;

    //Constructor Without ID
    public BookModel(String title, String description, String author, String publisher, int year){
        this.title = title;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
    }

    //Constructor With ID
    public BookModel(int id, String title, String description, String author, String publisher, int year){
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
    }

    //Functions
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getYear() {
        return year;
    }
}
