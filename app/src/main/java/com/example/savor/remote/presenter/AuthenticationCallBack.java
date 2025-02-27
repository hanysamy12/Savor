package com.example.savor.remote.presenter;

public interface AuthenticationCallBack {
    void onSuccess(String email);
    void onFailure(String errorMsg);
}
