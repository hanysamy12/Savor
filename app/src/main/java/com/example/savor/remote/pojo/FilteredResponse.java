package com.example.savor.remote.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilteredResponse {
    @SerializedName("meals")
    private List<MealsFilteredItem> mealsFilteredItems;
    public List<MealsFilteredItem> getMealsFilteredItems() {
        return mealsFilteredItems;
    }
}