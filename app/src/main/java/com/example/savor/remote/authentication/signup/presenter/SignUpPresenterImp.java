package com.example.savor.remote.authentication.signup.presenter;

import com.example.savor.remote.authentication.model.AuthenticationCallBack;
import com.example.savor.remote.authentication.model.AuthenticationRepo;

public class SignUpPresenterImp implements SignUpPresenter, AuthenticationCallBack {
   AuthenticationRepo authenticationRepo;
   SignUpFragmentContract signUpFragmentContract;

    public SignUpPresenterImp(AuthenticationRepo authenticationRepo, SignUpFragmentContract signUpFragment) {
        this.authenticationRepo = authenticationRepo;
        this.signUpFragmentContract = signUpFragment;
    }

    @Override
    public void onSuccess(String email) {
        signUpFragmentContract.signUpSuccess(email);
    }

    @Override
    public void onFailure(String errorMsg) {
        signUpFragmentContract.signUpFailure(errorMsg);
    }

    @Override
    public void signUp(String userName, String password) {
        authenticationRepo.signUp(userName,password,this);
    }
}
