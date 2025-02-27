package com.example.savor.remote.authentication.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.savor.MainActivity;
import com.example.savor.remote.authentication.firestore.FireStore;
import com.example.savor.remote.presenter.AuthenticationCallBack;
import com.example.savor.remote.presenter.AuthenticationRepo;

public class LoginPresenterImp implements AuthenticationCallBack, LoginPresenter {
    AuthenticationRepo authenticationRepo;
    LoginFragmentContract loginFragmentContract;
    SharedPreferences sharedPreferences;
    Context context;
    FireStore fireStore;
    public LoginPresenterImp(AuthenticationRepo authenticationRepo ,LoginFragmentContract loginFragmentContract,Context context) {
        this.loginFragmentContract = loginFragmentContract;
        this.authenticationRepo = authenticationRepo;
        this.context = context;
        fireStore = new FireStore(context);
    }

    @Override
    public void requestLogin(String userName, String password) {

        authenticationRepo.login(userName,password,this);
    }

    @Override
    public void requestGoogleLogin(Activity activity) {
        authenticationRepo.googleLogin(activity,this);
    }


    @Override
    public void onSuccess(String email) {
        sharedPreferences = context.getSharedPreferences(MainActivity.PRES_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.USER_NAME,email);
        editor.apply(); //async
        loginFragmentContract.onLoginSuccess(email);
        fireStore.getData(email);
    }

    @Override
    public void onFailure(String errorMsg) {
        loginFragmentContract.onLoginFailure(errorMsg);
    }
}
