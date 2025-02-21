package com.example.savor.homepage.presenter;

import com.example.savor.remote.model.pojo.MealsItemResponse;

public interface HomeScreenContract {
    void showRandomMeal(MealsItemResponse mealsItemResponse);
    void showErrorMsg(String errorMsg);
    void showHomeMeals(MealsItemResponse mealsItemResponse);

}
