package com.example.savor.mealdetails.presenter;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.savor.remote.model.MealsCallBack;
import com.example.savor.remote.model.MealsRepositoryImp;
import com.example.savor.remote.model.pojo.MealsItem;
import com.example.savor.remote.model.pojo.MealsItemResponse;

import java.util.ArrayList;
import java.util.List;

public class MealDetailsPresenterImp implements MealDetailsPresenter {
    MealsRepositoryImp mealsRepositoryImp;
    MealDetailsFragmentContract mealDetailsFragmentContract;
    List<String> ingredienList;
    List<String> measureList;
    private static final String TAG = "MealDetailsPresenterImp";

    public MealDetailsPresenterImp(MealsRepositoryImp mealsRepositoryImp, MealDetailsFragmentContract mealDetailsFragmentContract) {
        this.mealsRepositoryImp = mealsRepositoryImp;
        this.mealDetailsFragmentContract = mealDetailsFragmentContract;
    }

    @Override
    public void getMealById(Integer mealId) {
        mealsRepositoryImp.getMealById(mealId, new MealsCallBack<MealsItemResponse>() {
            @Override
            public void onSuccess(MealsItemResponse response) {
                MealsItem meal = response.getMeals().get(0);
                ingredienList = new ArrayList<>();
                measureList = new ArrayList<>();
                for (int i = 1; i <= 20; i++) {
                    String ingredient = getValidIngredient(meal, i);
                    String measure = getMeasure(meal, i);

                    if (ingredient != null && !ingredient.isEmpty()) {
                        ingredienList.add(ingredient);
                        measureList.add(measure);
                    }
                }

                mealDetailsFragmentContract.showMealDetails(response.getMeals().get(0), ingredienList, measureList);
            }

            @Override
            public void onFailure(String errorMsg) {
                mealDetailsFragmentContract.showError(errorMsg);
            }
        });
    }

    @Override
    public void addToFavorite(MealsItem mealsItem) {

        new Thread(() -> {
            Log.i(TAG, "addToFavorite: Thread");
            mealsRepositoryImp.addFavoriteMeal(mealsItem);
        }).start();
    }

    @Override
    public void addToPlan(MealsItem mealsItem) {
        new Thread(() -> {
            Log.i(TAG, "addToPlan: Thread");
            mealsRepositoryImp.addPlanMeal(mealsItem);
        }).start();
    }

    private String getValidIngredient(MealsItem mealsItem, int i) {
        switch (i) {
            case 1:
                return mealsItem.getStrIngredient1();
            case 2:
                return mealsItem.getStrIngredient2();
            case 3:
                return mealsItem.getStrIngredient3();
            case 4:
                return mealsItem.getStrIngredient4();
            case 5:
                return mealsItem.getStrIngredient5();
            case 6:
                return mealsItem.getStrIngredient6();
            case 7:
                return mealsItem.getStrIngredient7();
            case 8:
                return mealsItem.getStrIngredient8();
            case 9:
                return mealsItem.getStrIngredient9();
            case 10:
                return mealsItem.getStrIngredient10();
            case 11:
                return mealsItem.getStrIngredient11();
            case 12:
                return mealsItem.getStrIngredient12();
            case 13:
                return mealsItem.getStrIngredient13();
            case 14:
                return mealsItem.getStrIngredient14();
            case 15:
                return mealsItem.getStrIngredient15();
            case 16:
                return mealsItem.getStrIngredient16();
            case 17:
                return mealsItem.getStrIngredient17();
            case 18:
                return mealsItem.getStrIngredient18();
            case 19:
                return mealsItem.getStrIngredient19();
            case 20:
                return mealsItem.getStrIngredient20();
        }
        return null;
    }

    private String getMeasure(MealsItem mealsItem, int i) {
        switch (i) {
            case 1:
                return mealsItem.getStrMeasure1();
            case 2:
                return mealsItem.getStrMeasure2();
            case 3:
                return mealsItem.getStrMeasure3();
            case 4:
                return mealsItem.getStrMeasure4();
            case 5:
                return mealsItem.getStrMeasure5();
            case 6:
                return mealsItem.getStrMeasure6();
            case 7:
                return mealsItem.getStrMeasure7();
            case 8:
                return mealsItem.getStrMeasure8();
            case 9:
                return mealsItem.getStrMeasure9();
            case 10:
                return mealsItem.getStrMeasure10();
            case 11:
                return mealsItem.getStrMeasure11();
            case 12:
                return mealsItem.getStrMeasure12();
            case 13:
                return mealsItem.getStrMeasure13();
            case 14:
                return mealsItem.getStrMeasure14();
            case 15:
                return mealsItem.getStrMeasure15();
            case 16:
                return mealsItem.getStrMeasure16();
            case 17:
                return mealsItem.getStrMeasure17();
            case 18:
                return mealsItem.getStrMeasure18();
            case 19:
                return mealsItem.getStrMeasure19();
            case 20:
                return mealsItem.getStrMeasure20();
        }
        return null;
    }
}
