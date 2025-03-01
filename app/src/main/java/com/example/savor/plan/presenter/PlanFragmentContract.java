package com.example.savor.plan.presenter;

import com.example.savor.model.pojo.MealsItem;

import java.util.List;

public interface PlanFragmentContract {
    void showPlanMeals(List<MealsItem> mealsItemList);
    void showSuccessMsg(String successMsg);
    void showError(String errorMsg);
}
