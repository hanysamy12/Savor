package com.example.savor.remote.model;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.savor.database.MealsLocalDataSource;
import com.example.savor.remote.model.pojo.AreaResponse;
import com.example.savor.remote.model.pojo.CategoriesResponse;
import com.example.savor.remote.model.pojo.FilteredResponse;
import com.example.savor.remote.model.pojo.IngredientResponse;
import com.example.savor.remote.model.pojo.MealsItem;
import com.example.savor.remote.model.pojo.MealsItemResponse;

import java.util.List;

public class MealsRepositoryImp implements MealsRepositoryInterface {
    private static final String TAG = "MealsRepositoryImp";
    private MealsRemoteDataSource mealsRemoteDataSource;
    private MealsLocalDataSource mealsLocalDataSource;
    public MealsRepositoryImp(MealsRemoteDataSource mealsRemoteDataSource, MealsLocalDataSource mealsLocalDataSource) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
        this.mealsLocalDataSource = mealsLocalDataSource;
    }

    @Override
    public void getRandomMeal(MealsCallBack<MealsItemResponse>mealsCallBack) {
        mealsRemoteDataSource.getRandomMeal(mealsCallBack);
        Log.i(TAG, "getRandomMeal: "+mealsCallBack);
    }

    @Override
    public void getMealsByChar(String firstCharacter, MealsCallBack<MealsItemResponse>mealsCallBack) {
        mealsRemoteDataSource.getMealsByChar(firstCharacter,mealsCallBack);
        Log.i(TAG, "getMealsByChar: ");
    }

    @Override
    public void getMealById(Integer id ,MealsCallBack<MealsItemResponse>mealsCallBack) {
        mealsRemoteDataSource.getMealById(id,mealsCallBack);
    }

    @Override
    public void getAllCategories(MealsCallBack<CategoriesResponse>mealsCallBack) {
        mealsRemoteDataSource.getAllCategories(mealsCallBack);
    }

    @Override
    public void getAllAreas(MealsCallBack<AreaResponse>mealsCallBack) {
        mealsRemoteDataSource.getAllAreas(mealsCallBack);
    }

    @Override
    public void getAllIngredient(MealsCallBack<IngredientResponse>mealsCallBack) {
        mealsRemoteDataSource.getAllIngredient(mealsCallBack);
    }

    @Override
    public void filterByCategory(String categoryName, MealsCallBack<FilteredResponse> mealsCallBack) {
        mealsRemoteDataSource.filterByCategory(categoryName,mealsCallBack);
    }

    @Override
    public void filterByIngredient(String ingredientName, MealsCallBack<FilteredResponse> mealsCallBack) {
        mealsRemoteDataSource.filterByIngredient(ingredientName,mealsCallBack);
    }

    @Override
    public void filterCountry(String countryName, MealsCallBack<FilteredResponse> mealsCallBack) {
        mealsRemoteDataSource.filterByCounter(countryName,mealsCallBack);
    }

    @Override
    public void addFavoriteMeal(MealsItem mealsItem) {
        Log.i(TAG, "addFavoriteMeal: ");
        mealsItem.setFavorite(true);
        mealsLocalDataSource.getMealsItemDao().insertMeal(mealsItem);
    }

    @Override
    public void deleteMealFromFavorite(String id) {
        mealsLocalDataSource.getMealsItemDao().deleteMeal(id);
    }

    @Override
    public LiveData<List<MealsItem>> getFavoriteMeals() {
        return mealsLocalDataSource.getMealsItemDao().getMeals();
    }

    @Override
    public void addPlanMeal(MealsItem mealsItem) {
        mealsLocalDataSource.getMealsItemDao().insertMeal(mealsItem);
    }

    @Override
    public void deleteMealFromPlan(String id) {
        mealsLocalDataSource.getMealsItemDao().deleteMealPlan(id);
    }

    @Override
    public LiveData<List<MealsItem>> getPlaneMeals() {
        return mealsLocalDataSource.getMealsItemDao().getMealsPlan();
    }


}
