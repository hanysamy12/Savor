package com.example.savor.remote.authentication.login.view;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.savor.R;
import com.example.savor.remote.authentication.model.Authentication;
import com.example.savor.remote.authentication.model.AuthenticationRepoImp;
import com.example.savor.remote.authentication.login.presenter.LoginFragmentContract;
import com.example.savor.remote.authentication.login.presenter.LoginPresenterImp;
import com.google.android.material.snackbar.Snackbar;

public class LoginFragment extends Fragment implements LoginFragmentContract {
    private LoginPresenterImp loginPresenter;
    Button btnLogin;
    EditText txtUseName;
    EditText txtPassWord;
    TextView clickableTxtCreateAccount;
    TextView skipLogin;
    View view;
    ProgressBar loginProgressBar;



    private static final String TAG = "LoginFragment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loginPresenter = new LoginPresenterImp(new AuthenticationRepoImp(new Authentication(requireActivity())),this) ;
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        clickableTxtCreateAccount = view.findViewById(R.id.clickableTxtSignIn);
        txtUseName = view.findViewById(R.id.txtEmailLogin);
        txtPassWord =view.findViewById(R.id.txtSearch);
        skipLogin = view.findViewById(R.id.txtSkipLogin);
        btnLogin = view.findViewById(R.id.btnLogin);
        loginProgressBar = view.findViewById(R.id.loginProgressBar);
        clickableTxtCreateAccount.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.signUpFragment);
        });
        skipLogin.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.homeFragment);
        });
        btnLogin.setOnClickListener(view1 -> {

            String userName = txtUseName.getText().toString().trim();
            String password = txtPassWord.getText().toString().trim();
            if(!userName.isEmpty() && !password.isEmpty())
            {
                btnLogin.setVisibility(INVISIBLE);
                loginProgressBar.setVisibility(VISIBLE);
                loginPresenter.requestLogin(userName,password);
            }else {
                Snackbar.make(view,"Fill Email And Password",Snackbar.ANIMATION_MODE_FADE).show();
            }

        });
    }

    @Override
    public void onLoginSuccess(String userName) {
        btnLogin.setVisibility(VISIBLE);
        loginProgressBar.setVisibility(INVISIBLE);
        Snackbar.make(view,userName,Snackbar.ANIMATION_MODE_FADE).show();
        Navigation.findNavController(view).navigate(R.id.homeFragment);
    }

    @Override
    public void onLoginFailure(String errorMsg) {
        btnLogin.setVisibility(VISIBLE);
        loginProgressBar.setVisibility(INVISIBLE);
        Snackbar.make(view,errorMsg,Snackbar.ANIMATION_MODE_FADE).show();
    }

}