package com.example.savor.favorite.view.presenter;

import androidx.lifecycle.LiveData;

import com.example.savor.remote.model.pojo.MealsItem;

import java.util.List;

public interface FavoriteFragmentPresenter {
    LiveData<List<MealsItem>> showFavorite(String userId);
    void deleteFromFavorite(MealsItem mealsItem);
}
