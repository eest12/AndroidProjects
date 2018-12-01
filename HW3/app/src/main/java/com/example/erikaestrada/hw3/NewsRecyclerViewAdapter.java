package com.example.erikaestrada.hw3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsRecyclerViewAdapter  extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {

    private List<NewsItem> newsArticles;

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
        holder.titleTextView.setText(article.getTitle());
        holder.descTextView.setText(article.getPublishedAt() + " . " + article.getDescription());

        String imgUrl = article.getUrlToImage();
        if (imgUrl != null) {
            Picasso.get()
                    .load(imgUrl)
                    .into(holder.thumbImageView);
        }
    }

    @Override
    public int getItemCount() {
        return newsArticles == null ? 0 : newsArticles.size();
    }

    public void setNewsArticles(List<NewsItem> newsArticles) {
        this.newsArticles = newsArticles;
        notifyDataSetChanged();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView thumbImageView;
        TextView titleTextView;
        TextView descTextView;

        public NewsViewHolder(View itemView) {
            super(itemView);

            thumbImageView = itemView.findViewById(R.id.thumb);
            titleTextView = itemView.findViewById(R.id.title);
            descTextView = itemView.findViewById(R.id.description);

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
