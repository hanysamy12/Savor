package com.example.savor.remote.authentication.login.presenter;

public interface LoginFragmentContract {
    void onLoginSuccess(String userName);
    void onLoginFailure(String errorMsg);
}
