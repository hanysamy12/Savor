package com.example.savor.remote.authentication.model;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.io.IOException;

public class Authentication {

    Activity activity;
    private FirebaseAuth mAuth;
    private static final String TAG = "Authentication";


    public Authentication( Activity activity) {
        // this.context = context;
        this.activity = activity;

        mAuth = FirebaseAuth.getInstance();
    }

    public void signUp(String email, String password, AuthenticationCallBack authenticationCallBack) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(activity, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        authenticationCallBack.onSuccess(authResult.getUser().getEmail());
                    }
                }).addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String message = "Tray Again Later";
                        //user already registered
                        if (e instanceof FirebaseAuthUserCollisionException) {
                            message = "Use existed";
                        }
                        // Handle network connection issues (e.g., no internet)
                        else if (e instanceof IOException) {
                            message = "No Internet";
                        }
                        authenticationCallBack.onFailure(message);

                    }
                });

    }

    public void logIn(String userName, String password, AuthenticationCallBack authenticationCallBack) {
        Log.i(TAG, "logIn: " + userName + " " + password);
        //String userId ="1";
        mAuth.signInWithEmailAndPassword(userName, password)
                .addOnSuccessListener(activity, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        authenticationCallBack.onSuccess(authResult.getUser().getEmail());
                        //Snackbar.make(view,"Welcome "+authResult.getUser().getEmail(),Snackbar.ANIMATION_MODE_FADE).show();

                    }
                })
                .addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String message = "Tray Again Later";
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            message = "Wrong Email Or Password";
                        }
                        // Handle network connection issues (e.g., no internet)
                        else if (e instanceof IOException) {
                            message = "No Internet";
                        }
                        authenticationCallBack.onFailure(message);
                        // Log.i(TAG, "onFailure: "+ e.getMessage());
                    }
                });

    }

}
