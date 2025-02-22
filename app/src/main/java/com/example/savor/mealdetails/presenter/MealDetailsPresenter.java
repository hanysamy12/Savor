package com.example.savor.mealdetails.presenter;

import androidx.lifecycle.LiveData;

import com.example.savor.remote.model.pojo.MealsItem;

import java.util.List;

public interface MealDetailsPresenter {
    void getMealById(Integer mealId);
    void addToFavorite(MealsItem mealsItem);
    //LiveData<List<MealsItem>> getFavoriteMeals();
}
