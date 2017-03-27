package com.biryanistudio.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aakaash on 24/03/17 at 3:11 PM.
 */

class BookAdapter extends ArrayAdapter<BookModel> {

    BookAdapter(@NonNull Context context, @NonNull List<BookModel> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.book_item, parent, false);
        BookModel book = getItem(position);
        ((TextView) convertView.findViewById(R.id.book_title))
                .setText(book != null ? book.getTitle() : "No title");
        ((TextView) convertView.findViewById(R.id.book_authors))
                .setText(book != null ? book.getAuthor() : "No author data");
        return convertView;
    }
}
