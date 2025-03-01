package com.example.savor.search.presenter;

import com.example.savor.remote.pojo.AreaResponse;
import com.example.savor.remote.pojo.CategoriesResponse;
import com.example.savor.remote.pojo.FilteredResponse;
import com.example.savor.remote.pojo.IngredientResponse;

public interface SearchFragmentContract {
    void showAllCategories(CategoriesResponse categoriesResponse);
    void showAllIngredient(IngredientResponse ingredientResponse);
    void showAllAreas(AreaResponse areaResponse);
    void showFilteredMeals(FilteredResponse filteredResponse);
    void showError(String errorNsg);
}
