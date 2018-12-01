package com.example.erikaestrada.hw2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Repository {

    private NewsItemDao newsItemDao;
    private LiveData<List<NewsItem>> allNewsItems;

    public Repository(Application application) {
        NewsItemRoomDatabase db = NewsItemRoomDatabase.getDatabase(application);
        newsItemDao = db.newsItemDao();
        allNewsItems = newsItemDao.loadAllNewsItems();
    }

    public LiveData<List<NewsItem>> getAllNewsItems() {
        return allNewsItems;
    }

    public void loadFromDatabase() {
        new loadAsyncTask(newsItemDao).execute();
    }

    public void syncWithApi(URL url) {
        new syncApiAsyncTask(newsItemDao).execute(url);
    }

    /* AsyncTask Classes */

    private class loadAsyncTask extends AsyncTask<Void, Void, Void> {

        private NewsItemDao mAsyncTaskDao;

        loadAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            allNewsItems = mAsyncTaskDao.loadAllNewsItems();
            return null;
        }
    }

    private static class syncApiAsyncTask extends AsyncTask<URL, Void, Void> {

        private NewsItemDao mAsyncTaskDao;

        syncApiAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(URL... urls) {
            // call api
            String searchResults = "";
            try {
                // gets json string from the given url
                searchResults = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!searchResults.equals("")) { // prevents clearing database if api call fails
                // clear database
                mAsyncTaskDao.clearAll();

                // parse json into news items and add them to the database
                List<NewsItem> newsArticles = JsonUtils.parseNews(searchResults);
                mAsyncTaskDao.insert(newsArticles);
            }

            return null;
        }
    }
}
