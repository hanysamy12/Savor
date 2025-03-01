package com.example.savor.auth.authentication.signup.presenter;

public interface SignUpFragmentContract {
    void signUpSuccess(String userName);

    void signUpFailure(String errorMsg);
}
