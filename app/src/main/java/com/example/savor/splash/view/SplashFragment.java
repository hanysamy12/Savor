package com.example.savor.splash.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.example.savor.MainActivity;
import com.example.savor.R;

public class SplashFragment extends Fragment {
    SharedPreferences sharedPreferences;
    private static final String TAG = "SplashFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = requireContext().getSharedPreferences(MainActivity.PRES_NAME, Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString(MainActivity.USER_NAME, null);
        if (!MainActivity.isSplashed) {
            Log.i(TAG, "onViewCreated: "+MainActivity.isSplashed);
            new Handler().postDelayed(() -> {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                NavDestination currentDestination = navController.getCurrentDestination();

                if (currentDestination != null && currentDestination.getId() == R.id.splashFragment) {
                    if (userName != null) {
                        MainActivity.isSplashed = true;

                        navController.navigate(R.id.action_splashFragment_to_homeFragment);
                    } else {
                        MainActivity.isSplashed = true;

                        navController.navigate(R.id.action_splashFragment_to_loginFragment);
                    }
                }
            }, 3000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}