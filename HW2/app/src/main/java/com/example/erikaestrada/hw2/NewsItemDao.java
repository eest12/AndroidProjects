package com.example.erikaestrada.hw2;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NewsItemDao {

    @Query("SELECT * from news_item")
    LiveData<List<NewsItem>> loadAllNewsItems();

    @Insert
    void insert(List<NewsItem> items);

    @Query("DELETE FROM news_item")
    void clearAll(); //clears all current entries in database
}
