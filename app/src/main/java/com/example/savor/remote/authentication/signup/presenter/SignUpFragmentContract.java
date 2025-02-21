package com.example.savor.remote.authentication.signup.presenter;

public interface SignUpFragmentContract {
    void signUpSuccess(String userName);

    void signUpFailure(String errorMsg);
}
