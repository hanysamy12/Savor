package com.example.savor.favorite.presenter;

import com.example.savor.remote.MealsRepositoryImp;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritePresenterImp implements FavoriteFragmentPresenter{
    MealsRepositoryImp mealsRepositoryImp;
    FavoriteFragmentContract favoriteFragmentContract;

    public FavoritePresenterImp(MealsRepositoryImp mealsRepositoryImp, FavoriteFragmentContract favoriteFragmentContract) {
        this.mealsRepositoryImp = mealsRepositoryImp;
        this.favoriteFragmentContract = favoriteFragmentContract;
    }


    @Override
    public void showMeals() {
        mealsRepositoryImp.getFavoriteMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealsItemList -> {
                    favoriteFragmentContract.showMeals(mealsItemList);
                }, throwable -> {
                    favoriteFragmentContract.showError("No Items To Show");
                });
    }
    @Override
    public void deleteMeal(String id) {
        mealsRepositoryImp.deleteMealFromFavorite(id)
                .andThen(mealsRepositoryImp.deleteUnUsedMeals())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    favoriteFragmentContract.showSuccessMsg("deleted");
                }, throwable -> {
                    favoriteFragmentContract.showError("Not Deleted");
                });

    }
    }