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
    private MealsRemoteDataSource mealsRemoteDataSource;
    private MealsRepositoryImp mealsRepositoryImp;
    private static final String TAG = "MainActivity";

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
        NavigationUI.setupWithNavController(bottomNavigationView,navController);


/*        mealsRemoteDataSource.getRandomMeal();
        mealsRemoteDataSource.getAllDetailedMeals("a");
        mealsRemoteDataSource.getMealById(52924);
        mealsRemoteDataSource.getAllCategories();
        mealsRemoteDataSource.getAllAreas();
        mealsRemoteDataSource.getAllIngredient();*/

       // mealsRepositoryImp = new MealsRepositoryImp(MealsRemoteDataSource.getInstance());
       /* mealsRepositoryImp.getRandomMeal(new MealsCallBack<MealsItemResponse>() {
            @Override
            public void onSuccess(MealsItemResponse response) {
                Log.i(TAG, "onSuccess: "+response.getMeals().get(0).getStrMeal());
                Toast.makeText(MainActivity.this, "Congrat", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.i(TAG, "onFailure: main -"+errorMsg);
            }
        });
*/    }



}