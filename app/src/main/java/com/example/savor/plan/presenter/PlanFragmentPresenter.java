package com.example.savor.plan.presenter;

import androidx.lifecycle.LiveData;

import com.example.savor.remote.model.pojo.MealsItem;

import java.util.List;

public interface PlanFragmentPresenter {
    LiveData<List<MealsItem>> showPlan();
    void deleteFromPlan(String id);
}
