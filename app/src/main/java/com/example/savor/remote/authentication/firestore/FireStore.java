package com.example.savor.remote.authentication.firestore;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.savor.database.MealsLocalDataSource;
import com.example.savor.model.MealsRemoteDataSource;
import com.example.savor.model.MealsRepositoryImp;
import com.example.savor.model.pojo.MealsItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FireStore {
    public static final String FIRE_STORE_USER_NAME_COLLECTION = "users";
    public static final String FIRE_STORE_MEAL_COLLECTION = "meal";
    private static final String TAG = "FireStore";
    MealsRepositoryImp mealsRepositoryImp;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    Context context;
    List<MealsItem> mealsItemList;

    public FireStore(Context context) {
        mealsRepositoryImp = new MealsRepositoryImp(MealsRemoteDataSource.getInstance(), MealsLocalDataSource.getInstance(context));
    }


    public void uploadMeal() {
        Map<String, Object> meal1 = new HashMap<>();
        meal1.put("idmeal", "5789");
        meal1.put("booleanis", true);
        meal1.put("date", "20/2/2025");

        Map<String, Object> meal2 = new HashMap<>();
        meal2.put("idmeal", "1234");
        meal2.put("booleanis", false);
        meal2.put("date", "25/12/2025");

        List<Map<String, Object>> mealsList = new ArrayList<>();
        mealsList.add(meal1);
        mealsList.add(meal2);

        Map<String, Object> docData = new HashMap<>();
        docData.put("meals", mealsList);

        db.collection("hanysamy")
                .document("meals")
                .set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firestore", "meals added!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "error adding meals", e);
                    }
                });

    }


    public void UploadData2(String userEmail) {
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void getData() {

            DocumentReference docRef = db.collection("hanysamy").document("meals");


            docRef.get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            List<Map<String, Object>> meals = (List<Map<String, Object>>) documentSnapshot.get("meals");

                            if (meals != null) {
                                for (Map<String, Object> meal : meals) {
                                    String idMeal = (String) meal.get("idmeal");
                                    Boolean booleanIs = (Boolean) meal.get("booleanis");
                                    String date = (String) meal.get("date");

                                    Log.d(TAG, "idmeal: " + idMeal);
                                    Log.d(TAG, "idfav: " + booleanIs);
                                    Log.d(TAG, "date: " + date);
                                }
                            }
                        } else {
                            Log.d(TAG, "no document found!");
                        }
                    })
                    .addOnFailureListener(error -> {
                        Log.w(TAG, "error ", error);
                    });

    }

    public void UploadData1(String userEmail) {
        mealsRepositoryImp.getAllMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealsItems -> {
                    mealsItemList = mealsItems;
                    for (MealsItem mealsItem : mealsItemList) {

                        // Log.i(TAG, "UploadData: Meal :" + mealsItem.getStrMeal()+"\n");
                    }
                }, throwable -> {
                    Log.i(TAG, "UploadData: Faild to gat Data From DataBase" + throwable.getMessage());
                });

    }
}
