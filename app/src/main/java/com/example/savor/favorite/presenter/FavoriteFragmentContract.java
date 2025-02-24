package com.example.savor.favorite.presenter;

import com.example.savor.model.pojo.MealsItem;

import java.util.List;

public interface FavoriteFragmentContract {
    void showMeals(List<MealsItem> mealsItemList);
    void showSuccessMsg(String successMsg);
    void showError(String errorMsg);
}
