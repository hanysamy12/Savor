package com.example.savor.auth.authentication.signup.view;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.savor.R;
import com.example.savor.auth.authentication.signup.presenter.SignUpFragmentContract;
import com.example.savor.auth.authentication.signup.presenter.SignUpPresenterImp;
import com.example.savor.auth.presenter.Authentication;
import com.example.savor.auth.presenter.AuthenticationRepoImp;

public class SignUpFragment extends Fragment implements SignUpFragmentContract {
    SignUpPresenterImp signUpPresenterImp;
    EditText txtUseName;
    EditText txtPassword;
    Button btnSignUp;
    Button btnSignUpGoogle;
    TextView haveAccount;
    TextView skipSignUp;
    TextView txtConfirmPassword;
    View view;
    ProgressBar progressBar;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        signUpPresenterImp = new SignUpPresenterImp(new AuthenticationRepoImp(new Authentication(requireActivity())), this, requireContext(), requireActivity());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //this.view = view;
        txtUseName = view.findViewById(R.id.txtUserNameSignUp);
        txtPassword = view.findViewById(R.id.txtPasswordSignUp);
        txtConfirmPassword = view.findViewById(R.id.txtConfirmPasswordSignUp);
        skipSignUp = view.findViewById(R.id.txtSkipSigUp);
        haveAccount = view.findViewById(R.id.btnHaveAccount);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        btnSignUpGoogle = view.findViewById(R.id.btnSignUpToGoogle);
        progressBar = view.findViewById(R.id.signUpProgressBar);
        skipSignUp.setOnClickListener(view1 -> {
            Navigation.findNavController(requireView()).navigate(R.id.homeFragment);
        });
        haveAccount.setOnClickListener(view1 -> {
            Navigation.findNavController(requireView()).navigate(R.id.loginFragment);
        });
       btnSignUpGoogle.setOnClickListener(view1 -> {
            signUpPresenterImp.signUpGoogle();
        });
        btnSignUp.setOnClickListener(view1 -> {
            String userName = txtUseName.getText().toString();
            String password = txtPassword.getText().toString();
            String confirmPassword = txtConfirmPassword.getText().toString();
            if (!userName.isEmpty() && !password.isEmpty()) {
                if (!password.equals(confirmPassword)) {
                    txtConfirmPassword.setText("");
                    Toast.makeText(requireContext(), "Unmatched Password", Toast.LENGTH_SHORT).show();
                } else {
                    btnSignUp.setVisibility(INVISIBLE);
                    progressBar.setVisibility(VISIBLE);
                    signUpPresenterImp.signUp(userName, password);
                }
            } else {
                Toast.makeText(requireContext(), "Please Fill All Fields", Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public void signUpSuccess(String userName) {
        btnSignUp.setVisibility(VISIBLE);
        progressBar.setVisibility(INVISIBLE);
        Toast.makeText(requireContext(), userName, Toast.LENGTH_SHORT).show();
        NavController navController = Navigation.findNavController(requireView());
        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.signUpFragment, true)
                .build();
        navController.navigate(R.id.homeFragment, null, navOptions);
    }

    @Override
    public void signUpFailure(String errorMsg) {
        btnSignUp.setVisibility(VISIBLE);
        progressBar.setVisibility(INVISIBLE);
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}