package com.example.savor.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.savor.remote.model.pojo.MealsItem;

import java.util.List;

@Dao
public interface MealDAO {
    @Query("Select * from MealsItem where isFavorite not null")
    LiveData<List<MealsItem>> getMeals();

    @Query("Select * from MealsItem where date not null")
    LiveData<List<MealsItem>> getMealsPlan();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(MealsItem mealsItem);

    @Query("delete from MealsItem where date is null and idMeal = :id")
    void deleteMeal(String id);

    @Query("delete from MealsItem where isFavorite is null and idMeal = :id")
    void deleteMealPlan(String id);
}
