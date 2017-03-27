package com.biryanistudio.booklisting;

/**
 * Created by Aakaash on 24/03/17 at 3:09 PM.
 */

class BookModel {

    private final String title;
    private final String author;

    BookModel(String title, String author) {
        this.title = title;
        this.author = author;
    }

    String getTitle() {
        return title;
    }

    String getAuthor() {
        return author;
    }
}
