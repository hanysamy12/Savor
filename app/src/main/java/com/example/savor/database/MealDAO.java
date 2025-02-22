package com.example.savor.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.savor.remote.model.pojo.MealsItem;

import java.util.List;

@Dao
public interface MealDAO {
    @Query("Select * from MealsItem where isFavorite not null")
    LiveData<List<MealsItem>> getMealsFavorite();

    @Query("Select * from MealsItem where date not null")
    LiveData<List<MealsItem>> getMealsPlan();

    @Query("UPDATE MealsItem SET isFavorite = NULL WHERE idMeal = :id")
    void setFavoriteToNull(int id);

    @Query("UPDATE MealsItem SET date = NULL WHERE idMeal = :id")
    void setDateToNull(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(MealsItem mealsItem);

    @Query("delete from MealsItem where date is null and idMeal = :id")
    void deleteMeal(String id);

    @Query("delete from MealsItem where isFavorite is 0 and idMeal = :id")
    void deleteMealPlan(String id);
}
