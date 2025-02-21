package com.example.savor.remote.authentication.model;

public interface AuthenticationRepo {
    void login(String userName , String password , AuthenticationCallBack authenticationCallBack);
    void signUp(String userName , String password ,AuthenticationCallBack authenticationCallBack);
}
