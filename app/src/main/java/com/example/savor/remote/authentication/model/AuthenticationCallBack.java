package com.example.savor.remote.authentication.model;

public interface AuthenticationCallBack {
    void onSuccess(String email);
    void onFailure(String errorMsg);
}
