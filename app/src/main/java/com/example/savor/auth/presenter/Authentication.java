package com.example.savor.auth.presenter;

import static com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.example.savor.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.IOException;
import java.util.concurrent.Executors;

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
                .addOnSuccessListener(activity, authResult -> authenticationCallBack.onSuccess(authResult.getUser().getEmail())).addOnFailureListener(activity, e -> {
                    String message = "Tray Again Later";
                    if (e instanceof FirebaseAuthUserCollisionException) {
                        message = "User existed";
                    }
                    else if (e instanceof IOException) {
                        message = "No Internet";
                    }
                    authenticationCallBack.onFailure(message);

                });

    }
//project-318047228197
    public void logIn(String userName, String password, AuthenticationCallBack authenticationCallBack) {
        Log.i(TAG, "logIn: " + userName + " " + password);
        mAuth.signInWithEmailAndPassword(userName, password)
                .addOnSuccessListener(activity, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        authenticationCallBack.onSuccess(authResult.getUser().getEmail());

                    }
                })
                .addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String message = "Tray Again Later";
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            message = "Wrong Email Or Password";
                        }
                        else if (e instanceof IOException) {
                            message = "No Internet";
                        }
                        authenticationCallBack.onFailure(message);
                        // Log.i(TAG, "onFailure: "+ e.getMessage());
                    }
                });

    }

    public void googleSignIn(Activity activity,AuthenticationCallBack authenticationCallBack){
        Log.i(TAG, "googleSignIn: ");
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
              // .setServerClientId("318047228197-l1egji3bv7ebm79f4kavk20sks2u4ap6.apps.googleusercontent.com")
               .setServerClientId(activity.getString(R.string.default_web_client_id))
                .build();

        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();
        CredentialManager credentialManager = CredentialManager.create(activity);
        credentialManager.getCredentialAsync(activity, request, null
                , Executors.newSingleThreadExecutor()
                , new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                    @Override
                    public void onResult(GetCredentialResponse getCredentialResponse) {
                        Log.i(TAG, "onResult: "+Thread.currentThread().getName());

                        activity.runOnUiThread(() -> {
                            handleSignIn(getCredentialResponse.getCredential(),activity,authenticationCallBack);
                        });
                    }

                    @Override
                    public void onError(@NonNull GetCredentialException e) {
                        authenticationCallBack.onFailure("Google SignIn Field"+e.getMessage());
                    }
                });
    }


    private void handleSignIn(Credential credential,Activity activity,AuthenticationCallBack authenticationCallBack)
    {
        if(credential instanceof CustomCredential )
        {
            CustomCredential customCredential = (CustomCredential) credential;
            if(customCredential.getType().equals(TYPE_GOOGLE_ID_TOKEN_CREDENTIAL))
            {
                Bundle credentialData = customCredential.getData();
                GoogleIdTokenCredential googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credentialData);
                firebaseAuthWithGoogle(googleIdTokenCredential.getIdToken(),activity,authenticationCallBack);
               // authenticationCallBack.onSuccess("signInWithCredential:success");
            }else {
                authenticationCallBack.onFailure("signInWithCredential:failure");
            }
        }else {
            authenticationCallBack.onFailure("Invalid Credential Type");
        }
    }
    private void firebaseAuthWithGoogle(String token,Activity activity,AuthenticationCallBack authenticationCallBack)
    {
        AuthCredential credential= GoogleAuthProvider.getCredential(token,null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity,task -> {
                    if (task.isSuccessful())
                    {
                        Log.i(TAG, "firebaseAuthWithGoogle: success ");
                        FirebaseUser user =mAuth.getCurrentUser();
                        if(user!=null) {
                            Log.i(TAG, "firebaseAuthWithGoogle: Google UserNull");
                            authenticationCallBack.onSuccess(user.getEmail());
                        }else {
                            Log.i(TAG, "firebaseAuthWithGoogle: Google UserNotNul");
                            authenticationCallBack.onFailure("User Null");
                        }
                    }else {
                        Log.w(TAG, "signInWithGoogle:failure", task.getException());
                        authenticationCallBack.onFailure("Google Sign-In failed: ");
                    }
                });
    }
    public void signOut()
    {
        mAuth.signOut();

    }
}
