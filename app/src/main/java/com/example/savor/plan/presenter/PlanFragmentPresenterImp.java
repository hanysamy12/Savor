package com.example.savor.plan.presenter;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.savor.remote.model.MealsRepositoryImp;
import com.example.savor.remote.model.pojo.MealsItem;

import java.util.List;

public class PlanFragmentPresenterImp implements PlanFragmentPresenter{
    MealsRepositoryImp mealsRepositoryImp;
    PlanFragmentContract  planFragmentContract;
    private static final String TAG = "PlanFragmentPresenterIm";
    public PlanFragmentPresenterImp(MealsRepositoryImp mealsRepositoryImp, PlanFragmentContract planFragmentContract) {
        this.mealsRepositoryImp = mealsRepositoryImp;
        this.planFragmentContract = planFragmentContract;
    }


    @Override
    public LiveData<List<MealsItem>> showPlan() {
        return mealsRepositoryImp.getPlaneMeals();
    }

    @Override
    public void deleteFromPlan(String id) {

        new Thread(() -> {

            mealsRepositoryImp.deleteMealFromPlan(id);
        }).start();
    }
}
