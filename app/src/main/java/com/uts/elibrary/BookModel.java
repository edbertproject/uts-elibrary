package com.uts.elibrary;

public class BookModel {
    public int id, year;
    public String title, description, author, publisher;

    public BookModel(String title, String description, String author, String publisher, int year){
        this.title = title;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
    }

    public BookModel(int id, String title, String description, String author, String publisher, int year){
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
    }

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
