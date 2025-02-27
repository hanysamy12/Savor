package com.example.savor.model;

import com.example.savor.model.pojo.AreaResponse;
import com.example.savor.model.pojo.CategoriesResponse;
import com.example.savor.model.pojo.FilteredResponse;
import com.example.savor.model.pojo.IngredientResponse;
import com.example.savor.model.pojo.MealsItemResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsApiService {
    @GET("api/json/v1/1/random.php")
    Single<MealsItemResponse> getRandomMeal();

    @GET("api/json/v1/1/search.php")
    Single<MealsItemResponse> getMealByFirstLetter(@Query("f") String firstLetter);

    @GET("api/json/v1/1/search.php")
    Single<MealsItemResponse> getMealByName(@Query("s") String mealName);
    @GET("api/json/v1/1/lookup.php")
    Single<MealsItemResponse> getMealById(@Query("i") int id);

    @GET("api/json/v1/1/categories.php")
    Single<CategoriesResponse> getAllCategories();

    @GET("api/json/v1/1/list.php?a=list")
    Single<AreaResponse> getAllAreas();

    @GET("api/json/v1/1/list.php?i=list")
    Single<IngredientResponse> getAllIngeIngredient();

    @GET("api/json/v1/1/filter.php")
    Single<FilteredResponse> filterByCategory(@Query("c") String category);

    @GET("api/json/v1/1/filter.php")
    Single<FilteredResponse> filterByIngredient(@Query("i") String ingredient);

    @GET("api/json/v1/1/filter.php")
    Single<FilteredResponse> filterByCounter(@Query("a") String country);


}
