package com.example.savor.auth.presenter;

import android.app.Activity;

public class AuthenticationRepoImp implements AuthenticationRepo {
    Authentication authentication;
    public AuthenticationRepoImp(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public void login(String userName, String password, AuthenticationCallBack authenticationCallBack) {
        authentication.logIn(userName,password,authenticationCallBack);
    }

    @Override
    public void signUp(String userName, String password, AuthenticationCallBack authenticationCallBack) {
        authentication.signUp(userName,password,authenticationCallBack);
    }

    @Override
    public void googleLogin(Activity activity, AuthenticationCallBack authenticationCallBack) {
        authentication.googleSignIn(activity,authenticationCallBack);
    }

    @Override
    public void logOut() {
        authentication.signOut();
    }
}