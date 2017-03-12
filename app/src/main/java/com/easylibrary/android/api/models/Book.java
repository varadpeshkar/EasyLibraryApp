package com.easylibrary.android.api.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by varad on 14/2/17.
 */

public class Book extends RealmObject {

    @PrimaryKey
    private int id;

    private String isbn;

    private String name;

    private String author;

    private String publisher;

    private String category;

    private BookLocation bookLocation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BookLocation getBookLocation() {
        return bookLocation;
    }

    public void setBookLocation(BookLocation bookLocation) {
        this.bookLocation = bookLocation;
    }
}
