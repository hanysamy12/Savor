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
    @Query("Select * from MealsItem")
    LiveData<List<MealsItem>> getMeals();


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(MealsItem mealsItem);

    @Delete
    void deleteMeal(MealsItem mealsItem);
}
