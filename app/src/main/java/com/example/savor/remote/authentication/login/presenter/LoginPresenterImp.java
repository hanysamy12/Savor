package com.example.savor.remote.authentication.login.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.savor.MainActivity;
import com.example.savor.remote.authentication.model.AuthenticationCallBack;
import com.example.savor.remote.authentication.model.AuthenticationRepo;

public class LoginPresenterImp implements AuthenticationCallBack, LoginPresenter {
    AuthenticationRepo authenticationRepo;
    LoginFragmentContract loginFragmentContract;
    SharedPreferences sharedPreferences;
    Context context;
    public LoginPresenterImp(AuthenticationRepo authenticationRepo ,LoginFragmentContract loginFragmentContract,Context context) {
        this.loginFragmentContract = loginFragmentContract;
        this.authenticationRepo = authenticationRepo;
        this.context = context;
    }

    @Override
    public void requestLogin(String userName, String password) {

        authenticationRepo.login(userName,password,this);
    }



    @Override
    public void onSuccess(String email) {
        sharedPreferences = context.getSharedPreferences(MainActivity.PRES_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.USER_NAME,email);
        editor.apply(); //async
        loginFragmentContract.onLoginSuccess(email);
    }

    @Override
    public void onFailure(String errorMsg) {
        loginFragmentContract.onLoginFailure(errorMsg);
    }
}
