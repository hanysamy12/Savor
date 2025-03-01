package com.example.savor.remote;

import com.example.savor.remote.pojo.AreaResponse;
import com.example.savor.remote.pojo.CategoriesResponse;
import com.example.savor.remote.pojo.FilteredResponse;
import com.example.savor.remote.pojo.IngredientResponse;
import com.example.savor.remote.pojo.MealsItemResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSource {
    private final static String BASE_URL = "https://themealdb.com/";
    private static final String TAG = "MealsRemoteDataSource";
    private static MealsRemoteDataSource instance = null;
    Retrofit retrofit;
    MealsApiService mealsApiService;


    private MealsRemoteDataSource() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory
                        .create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        mealsApiService = retrofit.create(MealsApiService.class);
    }

    public static synchronized MealsRemoteDataSource getInstance() {
        if (instance == null) {
            instance = new MealsRemoteDataSource();
        }
        return instance;
    }

    public Single<MealsItemResponse> getRandomMeal() {

        return mealsApiService.getRandomMeal();

    }

    //to be used in the firstScreen as suggested meals
    public Single<MealsItemResponse> getMealsByChar(String firstLetter) {
        return mealsApiService.getMealByFirstLetter(firstLetter);

    }

    public Single<MealsItemResponse> getMealById(int id) {
        return mealsApiService.getMealById(id);

    }

    public Single<MealsItemResponse> getMealByName(String mealName) {
        return mealsApiService.getMealByName(mealName);
    }

    public Single<CategoriesResponse> getAllCategories() {
        return mealsApiService.getAllCategories();

    }

    public Single<AreaResponse> getAllAreas() {
        return mealsApiService.getAllAreas();

    }

    public Single<IngredientResponse> getAllIngredient() {
        return mealsApiService.getAllIngeIngredient();

    }

    public Single<FilteredResponse> filterByCategory(String categoryName) {
        return mealsApiService.filterByCategory(categoryName);
    }

    public Single<FilteredResponse> filterByIngredient(String ingredientName) {
        return mealsApiService.filterByIngredient(ingredientName);

    }

    public Single<FilteredResponse> filterByCounter(String countryName) {
        return mealsApiService.filterByCounter(countryName);

    }
}
