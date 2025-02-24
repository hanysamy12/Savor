package com.example.savor.model;

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

public interface MealsRepositoryInterface {
    Single<MealsItemResponse> getRandomMeal();
    Single<MealsItemResponse> getMealsByChar(String firstCharacter);
    Single<MealsItemResponse> getMealById(Integer id);
    Single<CategoriesResponse> getAllCategories();
    Single<AreaResponse> getAllAreas();
    Single<IngredientResponse> getAllIngredient();
    Single<FilteredResponse> filterByCategory(String categoryName);
    Single<FilteredResponse> filterByIngredient(String ingredientName);
    Single<FilteredResponse> filterCountry(String countryName);
    ////
    Completable addFavoriteMeal(MealsItem mealsItem);
    Completable deleteMealFromFavorite(String id);
    Flowable<List<MealsItem>> getFavoriteMeals();
    Completable addPlanMeal(MealsItem mealsItem);
    Completable deleteMealFromPlan(String id);
    Flowable<List<MealsItem>> getPlaneMeals();
    Completable deleteUnUsedMeals();
}
