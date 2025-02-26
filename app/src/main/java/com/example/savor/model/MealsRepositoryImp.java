package com.example.savor.model;
import android.util.Log;

import com.example.savor.database.MealsLocalDataSource;
import com.example.savor.model.pojo.AreaResponse;
import com.example.savor.model.pojo.CategoriesResponse;
import com.example.savor.model.pojo.FilteredResponse;
import com.example.savor.model.pojo.IngredientResponse;
import com.example.savor.model.pojo.MealsItem;
import com.example.savor.model.pojo.MealsItemResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealsRepositoryImp implements MealsRepositoryInterface {
    private static final String TAG = "MealsRepositoryImp";
    private MealsRemoteDataSource mealsRemoteDataSource;
    private MealsLocalDataSource mealsLocalDataSource;
    public MealsRepositoryImp(MealsRemoteDataSource mealsRemoteDataSource, MealsLocalDataSource mealsLocalDataSource) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
        this.mealsLocalDataSource = mealsLocalDataSource;
    }

    @Override
    public Single<MealsItemResponse> getRandomMeal() {
        return mealsRemoteDataSource.getRandomMeal();
        /*mealsRemoteDataSource.getRandomMeal(mealsCallBack);
        Log.i(TAG, "getRandomMeal: "+mealsCallBack);*/
    }

    @Override
    public Single<MealsItemResponse> getMealsByChar(String firstCharacter) {
        return mealsRemoteDataSource.getMealsByChar(firstCharacter);
    }

    @Override
    public Single<MealsItemResponse> getMealById(Integer id) {
        return mealsRemoteDataSource.getMealById(id);
    }

    @Override
    public Single<CategoriesResponse> getAllCategories() {
        return mealsRemoteDataSource.getAllCategories();
    }

    @Override
    public Single<AreaResponse> getAllAreas() {
        return mealsRemoteDataSource.getAllAreas();
    }

    @Override
    public Single<IngredientResponse> getAllIngredient() {
        return mealsRemoteDataSource.getAllIngredient();
    }

    @Override
    public Single<FilteredResponse> filterByCategory(String categoryName) {
        return mealsRemoteDataSource.filterByCategory(categoryName);
    }

    @Override
    public Single<FilteredResponse> filterByIngredient(String ingredientName) {
        return mealsRemoteDataSource.filterByIngredient(ingredientName);
    }

    @Override
    public Single<FilteredResponse> filterCountry(String countryName) {
        return mealsRemoteDataSource.filterByCounter(countryName);
    }


    @Override
    public Completable addFavoriteMeal(MealsItem mealsItem) {
        Log.i(TAG, "addFavoriteMeal: ");
        mealsItem.setFavorite(true);
        return  mealsLocalDataSource.getMealsItemDao().insertMeal(mealsItem);
    }

    @Override
    public Completable deleteMealFromFavorite(String id) {
        return mealsLocalDataSource.getMealsItemDao().removeFromFavorite(id);
    }

    @Override
    public Flowable<List<MealsItem>> getFavoriteMeals() {
        return mealsLocalDataSource.getMealsItemDao().getFavoriteMeals();
    }

    @Override
    public Completable addPlanMeal(MealsItem mealsItem) {
        Log.i(TAG, "addPlanMeal: "+mealsItem.getStrMeal());
        return mealsLocalDataSource.getMealsItemDao().insertMeal(mealsItem);

    }

    @Override
    public Completable deleteMealFromPlan(String id) {
        Log.i(TAG, "deleteMealFromPlan: ");
        return mealsLocalDataSource.getMealsItemDao().removeFromPlan(id);

    }

    @Override
    public Flowable<List<MealsItem>> getPlaneMeals() {
        return mealsLocalDataSource.getMealsItemDao().getPlanMeals();
    }

    @Override
    public Completable deleteUnUsedMeals() {
        return mealsLocalDataSource.getMealsItemDao().deleteMeal();
    }

    @Override
    public Flowable<List<MealsItem>> getStoredMealById(String mealId) {
        return mealsLocalDataSource.getMealsItemDao().getMealById(mealId);
    }

    @Override
    public Flowable<List<MealsItem>> getAllMeals() {
        return mealsLocalDataSource.getMealsItemDao().getAllMeals();
    }

    @Override
    public Completable cleanDataBase() {
        return mealsLocalDataSource.getMealsItemDao().deleteAllMeals();
    }


}
