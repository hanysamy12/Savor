package com.example.savor.mealdetails.presenter;

import com.example.savor.remote.pojo.MealsItem;

import java.util.List;

public interface MealDetailsFragmentContract {
    void showMealDetails(MealsItem mealsItem , List<String> ingredientList , List<String> measureList);
    void showError(String errorMsg);
    void showSuccessMessage(String succcesMsg);
    void showDialog();
    void hideLotti();
}
