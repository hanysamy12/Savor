package com.example.savor.search.presenter;

public interface SearchPresenter {
    void getAllCategories();
    void getAllIngredient();
    void getAllAreas();
    void getFilteredMealsByCategory(String categoryName);
    void getFilteredMealsByIngredient(String IngredientName);
    void getFilteredMealsByCountry(String countryName);
}

