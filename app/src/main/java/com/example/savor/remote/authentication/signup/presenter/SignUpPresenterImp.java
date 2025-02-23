package com.example.savor.remote.authentication.signup.presenter;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.savor.MainActivity;
import com.example.savor.remote.authentication.model.AuthenticationCallBack;
import com.example.savor.remote.authentication.model.AuthenticationRepo;

public class SignUpPresenterImp implements SignUpPresenter, AuthenticationCallBack {
    AuthenticationRepo authenticationRepo;
    SignUpFragmentContract signUpFragmentContract;
    Context context;
    SharedPreferences sharedPreferences;

    public SignUpPresenterImp(AuthenticationRepo authenticationRepo, SignUpFragmentContract signUpFragment, Context context) {
        this.authenticationRepo = authenticationRepo;
        this.signUpFragmentContract = signUpFragment;
        this.context = context;

    }

    @Override
    public void onSuccess(String email) {
        signUpFragmentContract.signUpSuccess(email);
        sharedPreferences = context.getSharedPreferences(MainActivity.PRES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.USER_NAME, email);
        editor.apply(); //async

    }

    @Override
    public void onFailure(String errorMsg) {
        signUpFragmentContract.signUpFailure(errorMsg);
    }

    @Override
    public void signUp(String userName, String password) {
        authenticationRepo.signUp(userName, password, this);
    }
}
