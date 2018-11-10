package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsRecyclerViewAdapter  extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {

    private ArrayList<NewsItem> newsArticles;

    final private NewsItemClickListener mOnClickListener;

    public interface NewsItemClickListener {
        void onNewsItemClick(String url);
    }

    public NewsRecyclerViewAdapter(NewsItemClickListener listener) {
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public NewsRecyclerViewAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForThisItem = R.layout.news_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForThisItem, parent, shouldAttachToParentImmediately);
        NewsViewHolder viewHolder = new NewsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerViewAdapter.NewsViewHolder holder, int position) {
        NewsItem article = newsArticles.get(position);
        holder.titleTextView.setText("Title: " + article.getTitle());
        holder.descTextView.setText("Description: " + article.getDescription());
        holder.dateTextView.setText("Date: " + article.getPublishedAt());
    }

    @Override
    public int getItemCount() {
        return newsArticles == null ? 0 : newsArticles.size();
    }

    public void setNewsArticles(ArrayList<NewsItem> newsArticles) {
        this.newsArticles = newsArticles;
        notifyDataSetChanged();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTextView;
        TextView descTextView;
        TextView dateTextView;

        public NewsViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.title);
            descTextView = itemView.findViewById(R.id.description);
            dateTextView = itemView.findViewById(R.id.date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            NewsItem clickedArticle = newsArticles.get(clickedPosition);
            mOnClickListener.onNewsItemClick(clickedArticle.getUrl());
        }
    }
}
