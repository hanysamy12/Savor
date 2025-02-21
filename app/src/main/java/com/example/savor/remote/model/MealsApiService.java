package com.example.savor.remote.model;

import com.example.savor.remote.model.pojo.AreaResponse;
import com.example.savor.remote.model.pojo.CategoriesResponse;
import com.example.savor.remote.model.pojo.FilteredResponse;
import com.example.savor.remote.model.pojo.IngredientResponse;
import com.example.savor.remote.model.pojo.MealsItemResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsApiService {
    @GET("api/json/v1/1/random.php")
    Call<MealsItemResponse> getRandomMeal();

    @GET("api/json/v1/1/search.php")
    Call<MealsItemResponse> getMealByFirstLetter(@Query("f") String firstLetter);

    @GET("api/json/v1/1/lookup.php")
    Call<MealsItemResponse> getMealById(@Query("i") int id);

    @GET("api/json/v1/1/categories.php")
    Call<CategoriesResponse> getAllCategories();

    @GET("api/json/v1/1/list.php?a=list")
    Call<AreaResponse> getAllAreas();

    @GET("api/json/v1/1/list.php?i=list")
    Call<IngredientResponse> getAllIngeIngredient();

    @GET("api/json/v1/1/filter.php")
    Call<FilteredResponse> filterByCategory(@Query("c") String category);

    @GET("api/json/v1/1/filter.php")
    Call<FilteredResponse> filterByIngredient(@Query("i") String ingredient);

    @GET("api/json/v1/1/filter.php")
    Call<FilteredResponse> filterByCounter(@Query("a") String country);


}
