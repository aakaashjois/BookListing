package com.biryanistudio.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Aakaash on 24/03/17 at 3:28 PM.
 */

class BookLoader extends AsyncTaskLoader<List<BookModel>> {

    private final String query;

    BookLoader(Context context, String query) {
        super(context);
        this.query = query;
    }

    @Override
    public List<BookModel> loadInBackground() {
        return Utils.fetchData(query);
    }
}
