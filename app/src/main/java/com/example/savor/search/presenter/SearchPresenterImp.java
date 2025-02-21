package com.example.savor.search.presenter;

import com.example.savor.remote.model.MealsCallBack;
import com.example.savor.remote.model.MealsRepositoryImp;
import com.example.savor.remote.model.pojo.AreaResponse;
import com.example.savor.remote.model.pojo.CategoriesResponse;
import com.example.savor.remote.model.pojo.FilteredResponse;
import com.example.savor.remote.model.pojo.IngredientResponse;

public class SearchPresenterImp implements SearchPresenter {
    private static final String TAG = "SearchPresenterImp";
    MealsRepositoryImp mealsRepositoryImp;
    SearchFragmentContract searchFragmentContract;

    public SearchPresenterImp(MealsRepositoryImp mealsRepositoryImp, SearchFragmentContract searchFragmentContract) {
        this.mealsRepositoryImp = mealsRepositoryImp;
        this.searchFragmentContract = searchFragmentContract;
    }

    @Override
    public void getAllCategories() {
        mealsRepositoryImp.getAllCategories(new MealsCallBack<CategoriesResponse>() {
            @Override
            public void onSuccess(CategoriesResponse response) {
                searchFragmentContract.showAllCategories(response);
            }

            @Override
            public void onFailure(String errorMsg) {
                searchFragmentContract.showError(errorMsg);
            }
        });
    }

    @Override
    public void getAllIngredient() {
        mealsRepositoryImp.getAllIngredient(new MealsCallBack<IngredientResponse>() {
            @Override
            public void onSuccess(IngredientResponse response) {
                searchFragmentContract.showAllIngredient(response);
            }

            @Override
            public void onFailure(String errorMsg) {
                searchFragmentContract.showError(errorMsg);
            }
        });
    }

    @Override
    public void getAllAreas() {
        mealsRepositoryImp.getAllAreas(new MealsCallBack<AreaResponse>() {
            @Override
            public void onSuccess(AreaResponse response) {
                searchFragmentContract.showAllAreas(response);
            }

            @Override
            public void onFailure(String errorMsg) {
                searchFragmentContract.showError(errorMsg);
            }
        });
    }

    @Override
    public void getFilteredMealsByCategory(String categoryName) {
        mealsRepositoryImp.filterByCategory(categoryName, new MealsCallBack<FilteredResponse>() {
            @Override
            public void onSuccess(FilteredResponse response) {
                searchFragmentContract.showFilteredMeals(response);
            }

            @Override
            public void onFailure(String errorMsg) {
                searchFragmentContract.showError(errorMsg);
            }
        });
    }

    @Override
    public void getFilteredMealsByIngredient(String ingredientName) {
        mealsRepositoryImp.filterByIngredient(ingredientName, new MealsCallBack<FilteredResponse>() {
            @Override
            public void onSuccess(FilteredResponse response) {
                searchFragmentContract.showFilteredMeals(response);
            }

            @Override
            public void onFailure(String errorMsg) {
                searchFragmentContract.showError(errorMsg);
            }
        });
    }

    @Override
    public void getFilteredMealsByCountry(String countryName) {
        mealsRepositoryImp.filterCountry(countryName, new MealsCallBack<FilteredResponse>() {
            @Override
            public void onSuccess(FilteredResponse response) {
                searchFragmentContract.showFilteredMeals(response);
            }

            @Override
            public void onFailure(String errorMsg) {
                searchFragmentContract.showError(errorMsg);
            }
        });
    }


}
