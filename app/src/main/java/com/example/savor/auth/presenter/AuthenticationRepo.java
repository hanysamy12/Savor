package com.example.savor.auth.presenter;

import android.app.Activity;

public interface AuthenticationRepo {
    void login(String userName , String password , AuthenticationCallBack authenticationCallBack);
    void signUp(String userName , String password ,AuthenticationCallBack authenticationCallBack);
    void googleLogin(Activity activity ,AuthenticationCallBack authenticationCallBack);
    void logOut();
}
