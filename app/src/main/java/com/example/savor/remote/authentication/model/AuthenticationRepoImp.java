package com.example.savor.remote.authentication.model;

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
}