package com.example.savor.remote.model;
import android.util.Log;

import com.example.savor.remote.model.pojo.AreaResponse;
import com.example.savor.remote.model.pojo.CategoriesResponse;
import com.example.savor.remote.model.pojo.FilteredResponse;
import com.example.savor.remote.model.pojo.IngredientResponse;
import com.example.savor.remote.model.pojo.MealsItemResponse;

public class MealsRepositoryImp implements MealsRepositoryInterface {
    private static final String TAG = "MealsRepositoryImp";
    private MealsRemoteDataSource mealsRemoteDataSource;
    public MealsRepositoryImp(MealsRemoteDataSource mealsRemoteDataSource) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
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
}
