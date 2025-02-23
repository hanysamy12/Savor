package com.example.savor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.savor.remote.model.MealsRemoteDataSource;
import com.example.savor.remote.model.MealsRepositoryImp;
import com.google.android.material.bottomnavigation.BottomNavigationView;

//userName honi76034@gmail.com
//password Hani@123
public class MainActivity extends AppCompatActivity {
    NavHostFragment navHostFragment;
    BottomNavigationView bottomNavigationView;
    NavController navController;
    public static final String USER_NAME = "userName";
    public static final String PREF_DATE = "dateOfTheCurrentDay";
    public static final String PRES_NAME = "PREF";
    public static final String TODAY_MEAL_ID ="mealId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        MealsRemoteDataSource.getInstance();
        navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        bottomNavigationView = findViewById(R.id.bottomMenue);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }


}