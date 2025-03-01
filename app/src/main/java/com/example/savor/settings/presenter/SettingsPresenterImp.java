package com.example.savor.settings.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.savor.MainActivity;
import com.example.savor.auth.authentication.firestore.FireStore;
import com.example.savor.auth.presenter.Authentication;

public class SettingsPresenterImp implements SettingsPresenter {
    private static final String TAG = "SettingsPresenterImp";
    FireStore fireStore;
    Authentication authentication;
    SharedPreferences sharedPreferences;
    String useEmail;
    SettingsFragmentContract settingsFragmentContract;

    public SettingsPresenterImp(Context context, SettingsFragmentContract settingsFragmentContract, Activity activity) {
        fireStore = new FireStore(context);
        authentication = new Authentication(activity);
        sharedPreferences = context.getSharedPreferences(MainActivity.PRES_NAME, Context.MODE_PRIVATE);
        useEmail = sharedPreferences.getString(MainActivity.USER_NAME, null);
        this.settingsFragmentContract = settingsFragmentContract;

    }

    @Override
    public void logOut() {
        Log.i(TAG, "logOut User : " + useEmail);
        fireStore.uploadData();

        authentication.signOut();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(MainActivity.USER_NAME);
        editor.apply();
    }

    @Override
    public void getUserName() {
        if (useEmail != null) {
            settingsFragmentContract.showUseName(useEmail.substring(0, 5));
        }
    }
}
