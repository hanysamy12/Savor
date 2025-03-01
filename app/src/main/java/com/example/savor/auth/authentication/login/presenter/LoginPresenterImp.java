package com.example.savor.auth.authentication.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.savor.MainActivity;
import com.example.savor.auth.authentication.firestore.FireStore;
import com.example.savor.auth.presenter.AuthenticationCallBack;
import com.example.savor.auth.presenter.AuthenticationRepo;

public class LoginPresenterImp implements AuthenticationCallBack, LoginPresenter {
    private static final String TAG = "LoginPresenterImp";
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
        Log.i(TAG, "LOgin Presenter onSuccess: "+email);
        editor.apply();
        loginFragmentContract.onLoginSuccess(email);
        fireStore.getData(email);
    }

    @Override
    public void onFailure(String errorMsg) {
        loginFragmentContract.onLoginFailure(errorMsg);
    }
}
