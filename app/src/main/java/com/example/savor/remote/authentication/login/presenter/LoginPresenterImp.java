package com.example.savor.remote.authentication.login.presenter;

import com.example.savor.remote.authentication.model.AuthenticationCallBack;
import com.example.savor.remote.authentication.model.AuthenticationRepo;

public class LoginPresenterImp implements AuthenticationCallBack, LoginPresenter {
    AuthenticationRepo authenticationRepo;
    LoginFragmentContract loginFragmentContract;
    public LoginPresenterImp(AuthenticationRepo authenticationRepo ,LoginFragmentContract loginFragmentContract) {
        this.loginFragmentContract = loginFragmentContract;
        this.authenticationRepo = authenticationRepo;
    }

    @Override
    public void requestLogin(String userName, String password) {

        authenticationRepo.login(userName,password,this);
    }



    @Override
    public void onSuccess(String email) {
        loginFragmentContract.onLoginSuccess(email);
    }

    @Override
    public void onFailure(String errorMsg) {
        loginFragmentContract.onLoginFailure(errorMsg);
    }
}
