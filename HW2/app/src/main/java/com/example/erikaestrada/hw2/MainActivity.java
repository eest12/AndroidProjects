package com.example.erikaestrada.hw2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsRecyclerViewAdapter.NewsItemClickListener {

    private NewsRecyclerViewAdapter mAdapter;
    private RecyclerView mNewsList;
    private NewsItemViewModel mViewModel;

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

        mViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);
        mViewModel.getAllNewsItems().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
                mAdapter.setNewsArticles(newsItems);
            }
        });
    }

    @Override
    public void onNewsItemClick(String url) {
        Uri urlAsUri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, urlAsUri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent); // opens webpage in browser
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
            mViewModel.getRepository().syncWithApi(url);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
