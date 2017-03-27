package com.biryanistudio.booklisting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Aakaash on 24/03/17 at 3:39 PM.
 */

class Utils {
    static List<BookModel> fetchData(String query) {
        return extractData(makeHttpResponse(createUrl(query)));
    }

    private static List<BookModel> extractData(String data) {
        if (!data.isEmpty()) {
            List<BookModel> books = new ArrayList<>();
            try {
                JSONObject base = new JSONObject(data);
                JSONArray items = base.getJSONArray("items");
                for (int i = 0; i < items.length(); i++) {
                    JSONObject book = items.getJSONObject(i);
                    if (book.has("volumeInfo")) {
                        JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                        if (volumeInfo.has("title") && volumeInfo.has("authors"))
                            books.add(new BookModel(volumeInfo.getString("title"), volumeInfo.getJSONArray("authors").get(0).toString()));
                    }
                }
                return books;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String makeHttpResponse(URL url) {
        if (url != null)
            try {
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                if (connection.getResponseCode() == 200)
                    return readFromStream(connection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }

    private static String readFromStream(InputStream stream) {
        if (stream != null) {
            StringBuilder data = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            try {
                String line = reader.readLine();
                while (line != null) {
                    data.append(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data.toString();
        }
        return null;
    }

    private static URL createUrl(String query) {
        try {
            return new URL("https://www.googleapis.com/books/v1/volumes?q=" + URLEncoder.encode(query, "UTF-8"));
        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
