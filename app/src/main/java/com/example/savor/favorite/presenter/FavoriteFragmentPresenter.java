package com.example.savor.favorite.presenter;

import androidx.lifecycle.LiveData;

import com.example.savor.remote.model.pojo.MealsItem;

import java.util.List;

public interface FavoriteFragmentPresenter {
    LiveData<List<MealsItem>> showMeals();
    void deleteMeal(String id);
}
