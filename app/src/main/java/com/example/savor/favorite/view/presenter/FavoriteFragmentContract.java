package com.example.savor.favorite.view.presenter;

import com.example.savor.remote.model.pojo.MealsItem;

import java.util.List;

public interface FavoriteFragmentContract {
    void showFavoriteMeals(List<MealsItem> mealsItemList);
    void showSuccessMsg(String successMsg);
    void showError(String errorMsg);
}
