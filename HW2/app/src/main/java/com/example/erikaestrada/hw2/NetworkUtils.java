package com.example.erikaestrada.hw2;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final static String BASE_URL = "https://newsapi.org/v1/articles";

    final static String SOURCE_PARAMETER = "source";
    final static String SORT_PARAMETER = "sortBy";
    final static String KEY_PARAMETER = "apiKey";

    final static String SOURCE_VAL = "the-next-web";
    final static String SORT_VAL = "latest";
    final static String KEY_VAL = "api-key"; // redacted

    public static URL buildUrl() {
        // builds uri with the parameters above
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(SOURCE_PARAMETER, SOURCE_VAL)
                .appendQueryParameter(SORT_PARAMETER, SORT_VAL)
                .appendQueryParameter(KEY_PARAMETER, KEY_VAL)
                .build();

        // builds url from uri
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A"); // to read everything together

            // returns contents or null if empty
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
