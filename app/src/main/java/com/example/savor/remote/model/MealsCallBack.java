package com.example.savor.remote.model;

public interface MealsCallBack<T> {
    void onSuccess(T response);
    void onFailure(String errorMsg);
}
