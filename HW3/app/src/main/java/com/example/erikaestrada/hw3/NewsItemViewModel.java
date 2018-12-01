package com.example.erikaestrada.hw3;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class NewsItemViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<NewsItem>> allNewsItems;

    public NewsItemViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allNewsItems = repository.getAllNewsItems();
    }

    public Repository getRepository() {
        return repository;
    }

    public LiveData<List<NewsItem>> getAllNewsItems() {
        return allNewsItems;
    }
}
