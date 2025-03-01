package com.example.savor.auth.authentication.login.presenter;

import android.app.Activity;

public interface LoginPresenter {
    void requestLogin(String userName, String password);
    void requestGoogleLogin(Activity activity);
}
