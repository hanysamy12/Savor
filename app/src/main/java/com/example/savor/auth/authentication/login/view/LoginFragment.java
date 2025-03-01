package com.example.savor.auth.authentication.login.view;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.savor.R;
import com.example.savor.auth.presenter.Authentication;
import com.example.savor.auth.presenter.AuthenticationRepoImp;
import com.example.savor.auth.authentication.login.presenter.LoginFragmentContract;
import com.example.savor.auth.authentication.login.presenter.LoginPresenterImp;

public class LoginFragment extends Fragment implements LoginFragmentContract {
    private LoginPresenterImp loginPresenter;
    Button btnLogin;
    Button btnGoogle;
    EditText txtUseName;
    EditText txtPassword;
    TextView clickableTxtCreateAccount;
    TextView skipLogin;
    View view;
    ProgressBar loginProgressBar;

    private boolean isPasswordVisible = false;

    private static final String TAG = "LoginFragment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loginPresenter = new LoginPresenterImp(new AuthenticationRepoImp(new Authentication(requireActivity())), this,requireContext());
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        clickableTxtCreateAccount = view.findViewById(R.id.clickableTxtSignUp);
        txtUseName = view.findViewById(R.id.txtEmailLogin);
        txtPassword = view.findViewById(R.id.txtPasswordLogin);
        skipLogin = view.findViewById(R.id.txtSkipLogin);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnGoogle =view.findViewById(R.id.btnSignInTGoogle);
        loginProgressBar = view.findViewById(R.id.loginProgressBar);
        clickableTxtCreateAccount.setOnClickListener(view1 -> {
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.signUpFragment);
        });
        skipLogin.setOnClickListener(view1 -> {
            NavController navController = Navigation.findNavController(requireView());
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.loginFragment,true)
                    .build();
            navController.navigate(R.id.homeFragment,null,navOptions);
        });

        txtPassword.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (txtPassword.getRight() - txtPassword.getCompoundDrawables()[2].getBounds().width())) {
                    togglePasswordVisibility();
                    return true;
                }
            }
            return false;
        });
        btnLogin.setOnClickListener(view1 -> {

            String userName = txtUseName.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();
            if (!userName.isEmpty() && !password.isEmpty()) {
                btnLogin.setVisibility(INVISIBLE);
                loginProgressBar.setVisibility(VISIBLE);
                loginPresenter.requestLogin(userName, password);
            } else {
                Toast.makeText(requireContext(), "Fill Email And Password", Toast.LENGTH_LONG).show();
            }

        });
        btnGoogle.setOnClickListener(view1 -> {
            loginPresenter.requestGoogleLogin(requireActivity());
        });
    }

    @Override
    public void onLoginSuccess(String userName) {
        btnLogin.setVisibility(VISIBLE);
        loginProgressBar.setVisibility(INVISIBLE);
//        Toast.makeText(requireContext(), userName.substring(0,5), Toast.LENGTH_SHORT).show();
        NavController navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView);
        navController.navigate(R.id.action_loginFragment_to_homeFragment);
        Log.i(TAG, "onLoginSuccess: "+userName);
    }

    @Override
    public void onLoginFailure(String errorMsg) {
        btnLogin.setVisibility(VISIBLE);
        loginProgressBar.setVisibility(INVISIBLE);
        Log.e(TAG, "onLoginFailure: "+errorMsg);
       Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            txtPassword.setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_lock),
                    null,
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_eye),
                    null
            );
        } else {
            // Show password
            txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            txtPassword.setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_lock),
                    null,
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_eye_off),
                    null
            );
        }
        txtPassword.setSelection(txtPassword.getText().length());
        isPasswordVisible = !isPasswordVisible;
    }

}