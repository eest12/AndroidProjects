package com.example.erikaestrada.hw1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonUtils {

    public static ArrayList<NewsItem> parseNews(String JSONString) {
        ArrayList<NewsItem> newsList = new ArrayList<>();
        try {
            JSONObject mainJSONObject = new JSONObject(JSONString);
            JSONArray articles = mainJSONObject.getJSONArray("articles");

            // parse each article, create a news item, and add it to the list
            for(int i = 0; i < articles.length(); i++){
                JSONObject article = articles.getJSONObject(i);
                newsList.add(new NewsItem(article.getString("author"),
                        article.getString("title"),
                        article.getString("description"),
                        article.getString("url"),
                        article.getString("urlToImage"),
                        article.getString("publishedAt")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsList;
    }
}


