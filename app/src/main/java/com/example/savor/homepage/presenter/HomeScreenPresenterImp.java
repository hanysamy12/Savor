package com.example.savor.homepage.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.util.Log;

import com.example.savor.MainActivity;
import com.example.savor.remote.model.MealsCallBack;
import com.example.savor.remote.model.MealsRepositoryImp;
import com.example.savor.remote.model.pojo.MealsItemResponse;

import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.Random;

public class HomeScreenPresenterImp implements HomeScreenPresenter {
    private static final String TAG = "HomeScreenPresenterImp";
    MealsRepositoryImp mealsRepository;
    HomeScreenContract homeScreenContract;
    Context context;
    SharedPreferences sharedPreferences;
    String currentDate;
    public HomeScreenPresenterImp(MealsRepositoryImp mealsRepository, HomeScreenContract homeScreenContract,Context context) {
        this.mealsRepository = mealsRepository;
        this.homeScreenContract = homeScreenContract;
        this.context = context;
        sharedPreferences = context.getSharedPreferences(MainActivity.PRES_NAME,Context.MODE_PRIVATE);
    }


    @Override
    public void getRandomMeal() {
        String prefDate = sharedPreferences.getString(MainActivity.PREF_DATE,null);
        if(isTheSameDate(prefDate))
        {
            String storedMealId = sharedPreferences.getString(MainActivity.TODAY_MEAL_ID,null);
            Log.i(TAG, "The Same Meal : "+storedMealId);
            mealsRepository.getMealById(Integer.valueOf(storedMealId), new MealsCallBack<MealsItemResponse>() {
                @Override
                public void onSuccess(MealsItemResponse response) {
                    homeScreenContract.showRandomMeal(response);
                }

                @Override
                public void onFailure(String errorMsg) {
                    homeScreenContract.showErrorMsg(errorMsg);
                }
            });
        }
        else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(MainActivity.PREF_DATE,currentDate);


            mealsRepository.getRandomMeal(new MealsCallBack<MealsItemResponse>() {
                @Override
                public void onSuccess(MealsItemResponse response) {
                    homeScreenContract.showRandomMeal(response);
                    editor.putString(MainActivity.TODAY_MEAL_ID,response.getMeals().get(0).getIdMeal());
                    editor.apply();
                    Log.i(TAG, "DifferentMeal: ");
                }

                @Override
                public void onFailure(String errorMsg) {
                    Log.i(TAG, "onFailure: Random");
                    homeScreenContract.showErrorMsg(errorMsg);
                }
            });
        }
    }

    @Override
    public void getHomeMeals() {
        String[]charsList={"a","b","c","d","e","f","g","h","i","j","k"};
        Random random = new Random();
        int rIndex = random.nextInt(charsList.length);
        String randomChar = charsList[rIndex];
        String randomString = String.valueOf(randomChar);
        Log.i(TAG, "getHomeString: "+randomString);
        mealsRepository.getMealsByChar(randomString, new MealsCallBack<MealsItemResponse>() {
            @Override
            public void onSuccess(MealsItemResponse response) {
                homeScreenContract.showHomeMeals(response);
                Log.i(TAG, "getHomeMeals: "+response.getMeals().get(2).getStrMeal());
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.i(TAG, "onFailure: HomeMeal");
                homeScreenContract.showErrorMsg(errorMsg);
            }
        });
    }
    private boolean isTheSameDate(String date)
    {
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH)+1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        currentDate =year+month+day;
        Log.i(TAG, "isTheSameDate date: "+date);
        Log.i(TAG, "isTheSameDate current: "+currentDate);
        return currentDate.equals(date);
    }
}
