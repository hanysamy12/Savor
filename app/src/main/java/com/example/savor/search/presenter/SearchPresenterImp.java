package com.example.savor.search.presenter;

import com.example.savor.model.MealsRepositoryImp;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
        Disposable subscribe = mealsRepositoryImp.getAllCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoriesResponse -> {
                    searchFragmentContract.showAllCategories(categoriesResponse);
                }, throwable -> {
                    searchFragmentContract.showError(throwable.getMessage());
                });

    }

    @Override
    public void getAllIngredient() {
        Disposable subscribe = mealsRepositoryImp.getAllIngredient()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingredientResponse -> {
                    searchFragmentContract.showAllIngredient(ingredientResponse);
                }, throwable -> {
                    searchFragmentContract.showError(throwable.getMessage());
                });

    }

    @Override
    public void getAllAreas() {
        Disposable subscribe = mealsRepositoryImp.getAllAreas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(areaResponse -> {
                    searchFragmentContract.showAllAreas(areaResponse);
                }, throwable -> {
                    searchFragmentContract.showError(throwable.getMessage());
                });

    }

    @Override
    public void getFilteredMealsByCategory(String categoryName) {
        Disposable subscribe = mealsRepositoryImp.filterByCategory(categoryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(filteredMeals -> {
                    searchFragmentContract.showFilteredMeals(filteredMeals);
                }, throwable -> {
                    searchFragmentContract.showError(throwable.getMessage());
                });

    }

    @Override
    public void getFilteredMealsByIngredient(String ingredientName) {
        Disposable subscribe = mealsRepositoryImp.filterByIngredient(ingredientName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(filteredMeals -> {
                    searchFragmentContract.showFilteredMeals(filteredMeals);
                }, throwable -> {
                    searchFragmentContract.showError(throwable.getMessage());
                });
   
    }

    @Override
    public void getFilteredMealsByCountry(String countryName) {
        mealsRepositoryImp.filterCountry(countryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(filteredMeals -> {
                    searchFragmentContract.showFilteredMeals(filteredMeals);
                }, throwable -> {
                    searchFragmentContract.showError(throwable.getMessage());
                });
    }


}
