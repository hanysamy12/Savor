package com.example.savor.homepage.presenter;

import android.util.Log;

import com.example.savor.remote.model.MealsCallBack;
import com.example.savor.remote.model.MealsRepositoryImp;
import com.example.savor.remote.model.pojo.MealsItemResponse;

public class HomeScreenPresenterImp implements HomeScreenPresenter {
    private static final String TAG = "HomeScreenPresenterImp";
    MealsRepositoryImp mealsRepository;
    HomeScreenContract homeScreenContract;

    public HomeScreenPresenterImp(MealsRepositoryImp mealsRepository, HomeScreenContract homeScreenContract) {
        this.mealsRepository = mealsRepository;
        this.homeScreenContract = homeScreenContract;
    }


    @Override
    public void getRandomMeal() {
        Log.i(TAG, "getRandomMeal: ");
        mealsRepository.getRandomMeal(new MealsCallBack<MealsItemResponse>() {
            @Override
            public void onSuccess(MealsItemResponse response) {
                homeScreenContract.showRandomMeal(response);
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.i(TAG, "onFailure: Random");
                homeScreenContract.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void getHomeMeals() {
        mealsRepository.getMealsByChar("a", new MealsCallBack<MealsItemResponse>() {
            @Override
            public void onSuccess(MealsItemResponse response) {
                homeScreenContract.showHomeMeals(response);
                Log.i(TAG, "getHomeMeals: "+response.getMeals().get(2).getStrMeal());
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.i(TAG, "onFailure: HomeMeal");
                homeScreenContract.showErrorMsg(errorMsg);
            }
        });
    }
}
