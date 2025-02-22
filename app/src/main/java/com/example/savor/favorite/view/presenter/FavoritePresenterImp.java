package com.example.savor.favorite.view.presenter;

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
    public LiveData<List<MealsItem>> showFavorite(String userId) {
        return mealsRepositoryImp.getFavoriteMeals();
    }

    @Override
    public void deleteFromFavorite(MealsItem mealsItem) {
        new Thread(() -> {
            mealsRepositoryImp.deleteMealFromFavorite(mealsItem.getIdMeal());
        }).start();

    }
}
