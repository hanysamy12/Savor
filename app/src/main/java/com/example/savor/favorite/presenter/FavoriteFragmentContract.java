package com.example.savor.favorite.presenter;

import com.example.savor.remote.model.pojo.MealsItem;

import java.util.List;

public interface FavoriteFragmentContract {
    void showSuccessMsg(String successMsg);
    void showError(String errorMsg);
}
