package com.example.savor.mealdetails.presenter;

import com.example.savor.remote.pojo.MealsItem;

public interface MealDetailsPresenter {
    void getMealById(Integer mealId);
    void addToFavorite(MealsItem mealsItem);
    void addToPlan(MealsItem mealsItem);

}
