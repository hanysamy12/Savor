package com.example.savor.remote.authentication.signup.view;

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
import com.example.savor.remote.authentication.signup.presenter.SignUpFragmentContract;
import com.example.savor.remote.authentication.signup.presenter.SignUpPresenterImp;
import com.google.android.material.snackbar.Snackbar;

public class SignUpFragment extends Fragment implements SignUpFragmentContract {
    SignUpPresenterImp signUpPresenterImp;

    /*private Authentication authentication;*/
    EditText txtUseName;
    EditText txtPassword;
    Button btnSignUp;
    TextView haveAccount;
    TextView skipSignUp;
    View view;
    ProgressBar progressBar;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         signUpPresenterImp = new SignUpPresenterImp(new AuthenticationRepoImp(new Authentication(requireActivity())),this);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        txtUseName=view.findViewById(R.id.txtUserNameSignUp);
        txtPassword=view.findViewById(R.id.txtPasswordSignUp);
        skipSignUp = view.findViewById(R.id.txtSkipSigUp);
        haveAccount = view.findViewById(R.id.btnHaveAccount);
        btnSignUp=view.findViewById(R.id.btnSignUp);
        skipSignUp.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.homeFragment);
        });
        haveAccount.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.loginFragment);

        });
        btnSignUp.setOnClickListener(view1 -> {
            String userName = txtUseName.getText().toString();
            String password = txtPassword.getText().toString();
            if(!userName.isEmpty() && !password.isEmpty())
            {
                btnSignUp.setVisibility(INVISIBLE);
               // signUpProgressBar.setVisibility(VISIBLE);
                //signUoPresenter.requestLogin(userName,password);
                signUpPresenterImp.signUp(userName,password);
            }else {
                Snackbar.make(view,"Fill Email And Password",Snackbar.ANIMATION_MODE_FADE).show();
            }
       /*     authentication =new Authentication(requireActivity());
            authentication.signUp(useName,passWord,this);*/
        });
    }

    @Override
    public void signUpSuccess(String userName) {
        btnSignUp.setVisibility(VISIBLE);
        Snackbar.make(view,userName,Snackbar.ANIMATION_MODE_FADE).show();


    }
    @Override
    public void signUpFailure(String errorMsg) {
        btnSignUp.setVisibility(VISIBLE);
        Snackbar.make(view,errorMsg,Snackbar.ANIMATION_MODE_FADE).show();

    }
}