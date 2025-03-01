package com.example.savor.remote.authentication.login.presenter;

import android.app.Activity;

import com.example.savor.remote.presenter.AuthenticationCallBack;

public interface LoginPresenter {
    void requestLogin(String userName, String password);
    void requestGoogleLogin(Activity activity);
}
