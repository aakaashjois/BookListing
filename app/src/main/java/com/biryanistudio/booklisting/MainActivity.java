package com.biryanistudio.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<BookModel>> {

    private final int BOOK_LOADER_ID = 100;
    private String query;
    private EditText userQuery;
    private TextView emptyView;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userQuery = (EditText) findViewById(R.id.user_query);
        ListView bookList = (ListView) findViewById(R.id.book_list);
        emptyView = (TextView) findViewById(R.id.empty_view);
        bookList.setEmptyView(emptyView);
        emptyView.setText(R.string.search_above);
        ArrayList<BookModel> booksList = new ArrayList<>();
        bookAdapter = new BookAdapter(getApplicationContext(), booksList);
        bookList.setAdapter(bookAdapter);
        getLoaderManager().initLoader(BOOK_LOADER_ID, null, MainActivity.this);
        userQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                    if (v.getText().toString().isEmpty())
                        userQuery.setError(getString(R.string.search_error));
                    else {
                        NetworkInfo activeNetwork = ((ConnectivityManager) getSystemService(
                                Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
                        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                            query = v.getText().toString();
                            getLoaderManager().restartLoader(
                                    BOOK_LOADER_ID, null, MainActivity.this).forceLoad();
                        } else
                            emptyView.setText(R.string.no_network);
                    }
                return true;
            }
        });
    }

    @Override
    public Loader<List<BookModel>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(getApplicationContext(), query);
    }

    @Override
    public void onLoadFinished(Loader<List<BookModel>> loader, List<BookModel> data) {
        bookAdapter.clear();
        if (data != null && !data.isEmpty())
            bookAdapter.addAll(data);
        else
            emptyView.setText(R.string.no_data);
    }

    @Override
    public void onLoaderReset(Loader<List<BookModel>> loader) {
        bookAdapter.clear();
    }
}
