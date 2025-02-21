package com.example.savor.remote.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.savor.remote.model.pojo.AreaResponse;
import com.example.savor.remote.model.pojo.CategoriesResponse;
import com.example.savor.remote.model.pojo.FilteredResponse;
import com.example.savor.remote.model.pojo.IngredientResponse;
import com.example.savor.remote.model.pojo.MealsItemResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSource {
    private final static String BASE_URL = "https://themealdb.com/";
    private static final String TAG = "MealsRemoteDataSource";
    private static MealsRemoteDataSource instance = null;
    Retrofit retrofit;
    MealsApiService mealsApiService;
    MealsItemResponse responseMealsItem;
    CategoriesResponse responseCategories;
    AreaResponse areaResponse;
    IngredientResponse ingredientResponse;

    private MealsRemoteDataSource() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealsApiService = retrofit.create(MealsApiService.class);
    }

    public static synchronized MealsRemoteDataSource getInstance() {
        if (instance == null) {
            instance = new MealsRemoteDataSource();
        }
        return instance;
    }

    public void getRandomMeal(MealsCallBack<MealsItemResponse> mealsCallBack) {

        Call<MealsItemResponse> responseMealsItemCall = mealsApiService.getRandomMeal();
        responseMealsItemCall.enqueue(new Callback<MealsItemResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealsItemResponse> call, @NonNull Response<MealsItemResponse> response) {
                responseMealsItem = response.body();
                mealsCallBack.onSuccess(response.body());
                Log.i(TAG, "GetRandomMeal onResponse: " + responseMealsItem.getMeals().get(0).getStrMeal());
            }

            @Override
            public void onFailure(Call<MealsItemResponse> call, Throwable throwable) {
                mealsCallBack.onFailure(throwable.getMessage());
                Log.i(TAG, "GetRandomMeal onFailure " + throwable);
            }
        });
    }

    //to be used in the firstScreen as suggested meals
    public void getMealsByChar(String firstLetter, MealsCallBack<MealsItemResponse> mealsCallBack) {
        Call<MealsItemResponse> responseMealsItemCall = mealsApiService.getMealByFirstLetter(firstLetter);
        responseMealsItemCall.enqueue(new Callback<MealsItemResponse>() {
            @Override
            public void onResponse(Call<MealsItemResponse> call, Response<MealsItemResponse> response) {
                //responseMealsItem = response.body();
                mealsCallBack.onSuccess(response.body());
                Log.i(TAG, "MealByChar onResponse" + response.body().getMeals().get(0).getStrMeal());
            }

            @Override
            public void onFailure(Call<MealsItemResponse> call, Throwable throwable) {
                mealsCallBack.onFailure(throwable.getMessage());
                Log.i(TAG, "onFailure: " + throwable.getMessage());
            }
        });
    }

    public void getMealById(int id, MealsCallBack<MealsItemResponse> mealsCallBack) {
        Call<MealsItemResponse> responseMealsItemCall = mealsApiService.getMealById(id);
        responseMealsItemCall.enqueue(new Callback<MealsItemResponse>() {
            @Override
            public void onResponse(Call<MealsItemResponse> call, Response<MealsItemResponse> response) {
                mealsCallBack.onSuccess(response.body());
                responseMealsItem = response.body();
                Log.i(TAG, "GetMealById onResponse: " + responseMealsItem.getMeals().get(0).getStrMeal());

            }

            @Override
            public void onFailure(Call<MealsItemResponse> call, Throwable throwable) {
                mealsCallBack.onFailure(throwable.getMessage());
                Log.i(TAG, "GetMealById onFailure: " + throwable.getMessage());
            }
        });
    }

    public void getAllCategories(MealsCallBack<CategoriesResponse> mealsCallBack) {
        Call<CategoriesResponse> responseCategoriesCall = mealsApiService.getAllCategories();
        responseCategoriesCall.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                mealsCallBack.onSuccess(response.body());
                responseCategories = response.body();
                Log.i(TAG, "GetAllCategories onResponse: " + responseCategories.getCategories().get(1).getStrCategory());
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable throwable) {
                mealsCallBack.onFailure(throwable.getMessage());
                Log.i(TAG, "onFailure: " + throwable.getMessage());

            }
        });
    }

    public void getAllAreas(MealsCallBack<AreaResponse> mealsCallBack) {
        Call<AreaResponse> areaResponseCall = mealsApiService.getAllAreas();
        areaResponseCall.enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                {
                    mealsCallBack.onSuccess(response.body());
                    areaResponse = response.body();
                    Log.i(TAG, "GetALlAreas onResponse: " + areaResponse.getAreas().get(0).getStrArea());
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable throwable) {
                mealsCallBack.onFailure(throwable.getMessage());
                Log.i(TAG, "GetALlAreas onFailure: " + throwable.getMessage() + throwable);
            }
        });
    }

    public void getAllIngredient(MealsCallBack<IngredientResponse> mealsCallBack) {
        Call<IngredientResponse> ingredientResponseCall = mealsApiService.getAllIngeIngredient();
        ingredientResponseCall.enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                mealsCallBack.onSuccess(response.body());
                ingredientResponse = response.body();
                Log.i(TAG, "GetAllIngredient onResponse: " + ingredientResponse.getIngredient().get(0).getStrIngredient());
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable throwable) {
                mealsCallBack.onFailure(throwable.getMessage());
                Log.i(TAG, "GetAllIngredient onFailure: " + throwable.getMessage());
            }
        });
    }

    public void filterByCategory(String categoryName, MealsCallBack<FilteredResponse> mealsCallBack) {
        Call<FilteredResponse> filteredResponseCall = mealsApiService.filterByCategory(categoryName);
        filteredResponseCall.enqueue(new Callback<FilteredResponse>() {
            @Override
            public void onResponse(Call<FilteredResponse> call, Response<FilteredResponse> response) {
                mealsCallBack.onSuccess(response.body());
                Log.i(TAG, "onResponse: " + response.body().getMealsFilteredItems());
            }

            @Override
            public void onFailure(Call<FilteredResponse> call, Throwable throwable) {
                mealsCallBack.onFailure(throwable.getMessage());
                Log.i(TAG, "onFailure: " + throwable.getMessage());
            }
        });
    }

    void filterByIngredient(String ingredientName, MealsCallBack<FilteredResponse> mealsCallBack) {
        Call<FilteredResponse> filterByIngredientResponseCall = mealsApiService.filterByIngredient(ingredientName);
        filterByIngredientResponseCall.enqueue(new Callback<FilteredResponse>() {
            @Override
            public void onResponse(Call<FilteredResponse> call, Response<FilteredResponse> response) {
                mealsCallBack.onSuccess(response.body());
                Log.i(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<FilteredResponse> call, Throwable throwable) {
                mealsCallBack.onFailure(throwable.getMessage());
                Log.i(TAG, "onFailure: " + throwable.getMessage());
            }
        });
    }
    public void filterByCounter(String countryName, MealsCallBack<FilteredResponse>mealsCallBack)
    {
        Call<FilteredResponse> filterByCountryResponseCall = mealsApiService.filterByCounter(countryName);
        filterByCountryResponseCall.enqueue(new Callback<FilteredResponse>() {
            @Override
            public void onResponse(Call<FilteredResponse> call, Response<FilteredResponse> response) {
                mealsCallBack.onSuccess(response.body());
                Log.i(TAG, "onResponse: "+response.body());
            }

            @Override
            public void onFailure(Call<FilteredResponse> call, Throwable throwable) {
                mealsCallBack.onFailure(throwable.getMessage());
                Log.i(TAG, "onFailure: "+throwable.getMessage());
            }
        });
    }
}
