package com.example.savor.remote.authentication.firestore;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.savor.MainActivity;
import com.example.savor.database.MealsLocalDataSource;
import com.example.savor.model.MealsRemoteDataSource;
import com.example.savor.model.MealsRepositoryImp;
import com.example.savor.model.pojo.MealsItem;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FireStore {
    public static final String FIRE_STORE_USER_NAME_COLLECTION = "users";
    public static final String FIRE_STORE_MEAL_COLLECTION = "meal";
    private static final String TAG = "FireStore";
    MealsRepositoryImp mealsRepositoryImp;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    Context context;


    public FireStore(Context context) {
        this.context = context;
        mealsRepositoryImp = new MealsRepositoryImp(MealsRemoteDataSource.getInstance(), MealsLocalDataSource.getInstance(context));
        sharedPreferences = context.getSharedPreferences(MainActivity.PRES_NAME, Context.MODE_PRIVATE);

    }


    public void uploadData() {
        String userEmail = sharedPreferences.getString(MainActivity.USER_NAME, null);
        if (userEmail != null) {
            Disposable subscribe = getStoredMeals()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(meals -> {
                        if (!meals.isEmpty()) {
                            Map<String, Object> storedMeals = new HashMap<>();
                            storedMeals.put("meals", meals);
                            //  Log.i(TAG, "Stored Meal: " + meals);
                            db.collection(userEmail)
                                    .document("meals")
                                    .set(storedMeals)
                                    .addOnSuccessListener(s -> {
                                        Disposable disposable = mealsRepositoryImp.cleanDataBase()
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(() -> {
                                                    Log.i(TAG, "DataBase Cleard: ");
                                                }, throwable -> {
                                                    Log.i(TAG, "DataNotCleared: " + throwable);
                                                });

                                        Log.i(TAG, "uploadMeal: Success");

                                    }).addOnFailureListener(e -> {
                                        Log.i(TAG, "uploadMeal: Faild " + e);

                                    });
                        }
                    }, throwable -> {

                        Log.i(TAG, "Stored Meal: error " + throwable);
                    });


        }
    }


    public void getData(String userEmail) {
        //String userEmail = sharedPreferences.getString(MainActivity.USER_NAME, null);
        if (userEmail != null) {
            DocumentReference docRef = db.collection(userEmail).document("meals");
            docRef.get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            List<Map<String, Object>> meals = (List<Map<String, Object>>) documentSnapshot.get("meals");
                            meals.stream().map(meal -> {
                                String idMeal = (String) meal.get("idmeal");
                                Boolean isFavorite = (Boolean) meal.get("isfavorite");
                                String date = (String) meal.get("date");
                                MealsItem mealsItem = new MealsItem();
                                mealsItem.setIdMeal(idMeal);
                                mealsItem.setFavorite(isFavorite);
                                mealsItem.setDate(date);
                                return mealsItem;
                            }).forEach(mealsItem -> {
                                Disposable subscribe = mealsRepositoryImp.getMealById(Integer.valueOf(mealsItem.getIdMeal()))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(mealsItemResponse -> {
                                            //addFavoriteMeal adding isFavorite
                                            MealsItem mealFromApi = mealsItemResponse.getMeals().get(0);
                                            mealFromApi.setFavorite(mealsItem.isFavorite());
                                            mealFromApi.setDate(mealsItem.getDate());
                                            Disposable subscribe1 = mealsRepositoryImp.addPlanMeal(mealFromApi)
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(() -> {
                                                        Log.i(TAG, "Meal: " + mealsItemResponse.getMeals().get(0).getIdMeal());
                                                    }, throwable -> {
                                                        Log.i(TAG, "getData: Failed");
                                                    });

                                            Log.i(TAG, "MealFrom Retrofit: " + mealsItemResponse.getMeals().get(0).getStrMeal());
                                        }, throwable -> {
                                            Log.i(TAG, "MealFrom Retrofit Error: " + throwable);
                                        });
                            });

                        } else {
                            Log.d(TAG, "no document found!");
                        }
                    })
                    .addOnFailureListener(error -> {
                        Log.w(TAG, "error ", error);
                    });
        }
    }

    private Flowable<List<Map<String, Object>>> getStoredMeals() {
        return mealsRepositoryImp.getAllMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(mealsItems -> mealsItems.stream()//convert list of meals to stream to
                        .map(mealsItem -> { // function
                            Map<String, Object> meal = new HashMap<>();
                            meal.put("idmeal", mealsItem.getIdMeal());
                            meal.put("isfavorite", mealsItem.isFavorite());
                            meal.put("date", mealsItem.getDate());
                            Log.i(TAG, "getStoredMeals: " + meal);
                            return meal;
                        })
                        .collect(Collectors.toList()))
                .doOnError(throwable -> {
                    Log.i(TAG, "getStoredMeals: Failed", throwable);
                });
    }

}
