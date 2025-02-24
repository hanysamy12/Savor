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

  /*  @Override
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
*/
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


}
