package com.example.savor.favorite.presenter;

import androidx.lifecycle.LiveData;

import com.example.savor.remote.model.MealsRepositoryImp;
import com.example.savor.remote.model.pojo.MealsItem;

import java.util.List;

public class FavoritePresenterImp implements FavoriteFragmentPresenter{
    MealsRepositoryImp mealsRepositoryImp;
    FavoriteFragmentContract favoriteFragmentContract;

    public FavoritePresenterImp(MealsRepositoryImp mealsRepositoryImp, FavoriteFragmentContract favoriteFragmentContract) {
        this.mealsRepositoryImp = mealsRepositoryImp;
        this.favoriteFragmentContract = favoriteFragmentContract;
    }

    @Override
    public LiveData<List<MealsItem>> showMeals() {
        return mealsRepositoryImp.getFavoriteMeals();
    }

    @Override
    public void deleteMeal(String id) {
        new Thread(() -> {
            mealsRepositoryImp.deleteMealFromFavorite(id);
        }).start();

    }
}
