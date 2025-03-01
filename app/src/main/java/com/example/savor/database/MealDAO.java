package com.example.savor.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.savor.model.pojo.MealsItem;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealDAO {

    @Query("Select * from MealsItem")
    Flowable<List<MealsItem>> getAllMeals();

    @Query("Select * from MealsItem where isFavorite =1")
    Flowable<List<MealsItem>> getFavoriteMeals();

    @Query("Select * from MealsItem where date not null")
    Flowable<List<MealsItem>> getPlanMeals();

    @Query("Select * from MealsItem where idMeal = :mealId")
    Flowable<List<MealsItem>> getMealById(String mealId);

    @Query("UPDATE MealsItem SET isFavorite = 0 WHERE idMeal = :id")
    Completable removeFromFavorite(String id);

    @Query("UPDATE MealsItem SET date = NULL WHERE idMeal = :id")
    Completable removeFromPlan(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(MealsItem mealsItem);

    @Query("delete from MealsItem where date is null and isFavorite = 0")
    Completable deleteMeal();

    @Query("delete from MealsItem")
    Completable deleteAllMeals();


}
