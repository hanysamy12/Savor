package com.example.savor.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.savor.model.pojo.MealsItem;

@Database(entities = {MealsItem.class}, version =1)
public abstract class MealsLocalDataSource extends RoomDatabase {
    private static MealsLocalDataSource instance = null;

    public abstract MealDAO getMealsItemDao();
    public static synchronized MealsLocalDataSource getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext()
                    , MealsLocalDataSource.class,"favoritedb" ).build();
        }
        return instance;
    }
}
