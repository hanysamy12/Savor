package com.example.savor.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilteredResponse {
    @SerializedName("meals")
    private List<MealsFilteredItem> mealsFilteredItems;
    public List<MealsFilteredItem> getMealsFilteredItems() {
        return mealsFilteredItems;
    }
}