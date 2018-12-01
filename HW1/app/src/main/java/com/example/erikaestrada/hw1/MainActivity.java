package com.example.erikaestrada.hw1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsRecyclerViewAdapter.NewsItemClickListener {

    private NewsRecyclerViewAdapter mAdapter;
    private RecyclerView mNewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsList = findViewById(R.id.news_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNewsList.setLayoutManager(layoutManager);

        mNewsList.setHasFixedSize(true);

        mAdapter = new NewsRecyclerViewAdapter(this);
        mNewsList.setAdapter(mAdapter);
    }

    @Override
    public void onNewsItemClick(String url) {
        Uri urlAsUri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, urlAsUri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent); // opens webpage in browser
        }
    }

    class NewsQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String searchResults = "";
            try {
                // gets json string from the given url
                searchResults = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return searchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            ArrayList<NewsItem> newsArticles = JsonUtils.parseNews(s);
            mAdapter.setNewsArticles(newsArticles);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) { // refresh button
            URL url = NetworkUtils.buildUrl();
            new NewsQueryTask().execute(url);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
