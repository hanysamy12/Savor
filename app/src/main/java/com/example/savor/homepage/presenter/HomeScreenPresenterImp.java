package com.example.savor.homepage.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.savor.MainActivity;
import com.example.savor.model.MealsRepositoryImp;

import java.util.Calendar;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeScreenPresenterImp implements HomeScreenPresenter {
    private static final String TAG = "HomeScreenPresenterImp";
    MealsRepositoryImp mealsRepository;
    HomeScreenContract homeScreenContract;
    Context context;
    SharedPreferences sharedPreferences;
    String currentDate;

    public HomeScreenPresenterImp(MealsRepositoryImp mealsRepository, HomeScreenContract homeScreenContract, Context context) {
        this.mealsRepository = mealsRepository;
        this.homeScreenContract = homeScreenContract;
        this.context = context;
        sharedPreferences = context.getSharedPreferences(MainActivity.PRES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void handelOnConnectionChanges(Boolean isOnline) {

        if(!isOnline || isOnline == null)
        {homeScreenContract.showLotti();

        }else {
            getHomeContent();
        }
    }
    public void getHomeContent()
    {
        homeScreenContract.hideLotti();
        getRandomMeal();
        getHomeMeals();

    }


    private void getRandomMeal() {
        String prefDate = sharedPreferences.getString(MainActivity.STORED_DATE, null);
        if (isTheSameDate(prefDate)) {
            String storedMealId = sharedPreferences.getString(MainActivity.TODAY_MEAL_ID, null);
            Log.i(TAG, "The Same Meal : " + storedMealId);
            mealsRepository.getMealById(Integer.valueOf(storedMealId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(meal -> {
                        homeScreenContract.showRandomMeal(meal);
                    }, throwable -> {
                        Log.i(TAG, "getRandomMeal: Faild");
                      //  homeScreenContract.showErrorMsg(throwable.getMessage());
                    });
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(MainActivity.STORED_DATE, currentDate);
            mealsRepository.getRandomMeal()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(meal -> {
                        editor.putString(MainActivity.TODAY_MEAL_ID, meal.getMeals().get(0).getIdMeal());
                        editor.apply();
                        homeScreenContract.showRandomMeal(meal);
                    }, throwable -> {
                        Log.i(TAG, "getRandomMeal: Faild");
                        //homeScreenContract.showErrorMsg(throwable.getMessage());
                    });


        }
    }


    private void getHomeMeals() {
        String[] charsList = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"};
        Random random = new Random();
        int rIndex = random.nextInt(charsList.length);
        String randomChar = charsList[rIndex];
        String randomString = String.valueOf(randomChar);
        mealsRepository.getMealsByChar(randomString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    homeScreenContract.showHomeMeals(meals);
                }, throwable -> {
                    Log.i(TAG, "getHomeMeals: failed");
                 //   homeScreenContract.showErrorMsg(throwable.getMessage());
                });

    }


    private boolean isTheSameDate(String date) {
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        currentDate = year + month + day;
        Log.i(TAG, "isTheSameDate date: " + date);
        Log.i(TAG, "isTheSameDate current: " + currentDate);
        return currentDate.equals(date);
    }
}
