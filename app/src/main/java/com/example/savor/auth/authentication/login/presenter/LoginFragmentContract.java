package com.example.savor.auth.authentication.login.presenter;

public interface LoginFragmentContract {
    void onLoginSuccess(String userName);
    void onLoginFailure(String errorMsg);
}
