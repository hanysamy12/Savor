package com.example.savor.remote.model;

import com.example.savor.remote.model.pojo.AreaResponse;
import com.example.savor.remote.model.pojo.CategoriesResponse;
import com.example.savor.remote.model.pojo.FilteredResponse;
import com.example.savor.remote.model.pojo.IngredientResponse;
import com.example.savor.remote.model.pojo.MealsItemResponse;

public interface MealsRepositoryInterface {
    void getRandomMeal(MealsCallBack<MealsItemResponse>mealsCallBack);
    void getMealsByChar(String firstCharacter, MealsCallBack<MealsItemResponse>mealsCallBack);
    void getMealById(Integer id,MealsCallBack<MealsItemResponse>mealsCallBack);
    void getAllCategories(MealsCallBack<CategoriesResponse>mealsCallBack);
    void getAllAreas(MealsCallBack<AreaResponse>mealsCallBack);
    void getAllIngredient(MealsCallBack<IngredientResponse>mealsCallBack);
    void filterByCategory(String categoryName, MealsCallBack<FilteredResponse> mealsCallBack);
    void filterByIngredient(String ingredientName, MealsCallBack<FilteredResponse>mealsCallBack);
    void filterCountry(String countryName, MealsCallBack<FilteredResponse>mealsCallBack);


}
