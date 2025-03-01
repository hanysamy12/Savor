package com.example.savor.auth.presenter;

public interface AuthenticationCallBack {
    void onSuccess(String email);
    void onFailure(String errorMsg);
}
