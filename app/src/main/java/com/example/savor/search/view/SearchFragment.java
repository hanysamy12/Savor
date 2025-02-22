package com.example.savor.search.view;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.savor.R;
import com.example.savor.database.MealsLocalDataSource;
import com.example.savor.homepage.view.HomeFragmentDirections;
import com.example.savor.remote.model.MealsRemoteDataSource;
import com.example.savor.remote.model.MealsRepositoryImp;
import com.example.savor.remote.model.pojo.AreaResponse;
import com.example.savor.remote.model.pojo.CategoriesResponse;
import com.example.savor.remote.model.pojo.FilteredResponse;
import com.example.savor.remote.model.pojo.IngredientResponse;
import com.example.savor.search.presenter.OnClickMealListener;
import com.example.savor.search.presenter.SearchFragmentContract;
import com.example.savor.search.presenter.SearchPresenter;
import com.example.savor.search.presenter.SearchPresenterImp;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;


public class SearchFragment extends Fragment implements SearchFragmentContract , OnClickMealListener {
    ChipGroup chipGroup;
    EditText txtSearch;
    SearchPresenter searchPresenter;
    //LinearLayoutManager layoutManager;
    GridLayoutManager layoutManager;
    RecyclerView recyclerView;
    AdapterSearchCategories adapterCategories;
    AdapterSearchIngredient adapterIngredient;
    AdapterSearchAreas adapterSearchAreas;
    AdapterSearchMeals adapterSearchMeals;
    private static final String TAG = "SearchFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
        chipGroup = view.findViewById(R.id.chipGroupSearch);
        txtSearch = view.findViewById(R.id.txtSearch);
        recyclerView = view.findViewById(R.id.searchRecyclerView);
        layoutManager = new GridLayoutManager(requireContext(), 2, VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        searchPresenter = new SearchPresenterImp(new MealsRepositoryImp(MealsRemoteDataSource.getInstance()
                , MealsLocalDataSource.getInstance(requireContext())), this);
        searchPresenter.getAllCategories();
        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (!checkedIds.isEmpty()) {
                layoutManager.setSpanCount(2);
                Chip chip = group.findViewById(checkedIds.get(0));
                String chipTxt = chip.getText().toString();
                switch (chipTxt) {
                    case "Category":
                        searchPresenter.getAllCategories();
                        break;
                    case "Ingredient":
                        searchPresenter.getAllIngredient();
                        break;
                    case "Country":
                        searchPresenter.getAllAreas();
                        break;
                }
                txtSearch.setHint(chip.getText());
                // Log.i(TAG, "onViewCreated: chip " + chip.getText());
            }
        });
    }

    @Override
    public void showAllCategories(CategoriesResponse categoriesResponse) {
        adapterCategories = new AdapterSearchCategories(requireContext(), categoriesResponse.getCategories(),this);
        recyclerView.setAdapter(adapterCategories);
        Log.i(TAG, "showAllCategories: " + categoriesResponse.getCategories().get(0).getStrCategory());
    }

    @Override
    public void showAllIngredient(IngredientResponse ingredientResponse) {
        adapterIngredient = new AdapterSearchIngredient(requireContext(), ingredientResponse.getIngredient(),this);
        recyclerView.setAdapter(adapterIngredient);
        Log.i(TAG, "showAllIngredient: " + ingredientResponse.getIngredient().get(0).getStrIngredient());
    }

    @Override
    public void showAllAreas(AreaResponse areaResponse) {
        adapterSearchAreas = new AdapterSearchAreas(requireContext(), areaResponse.getAreas(),this);
        recyclerView.setAdapter(adapterSearchAreas);
        Log.i(TAG, "showAllAreas: " + areaResponse.getAreas().get(0).getStrArea());
    }

    @Override
    public void showFilteredMeals(FilteredResponse filteredResponse) {
        layoutManager.setSpanCount(1);
        adapterSearchMeals = new AdapterSearchMeals(requireContext(), filteredResponse.getMealsFilteredItems(),this);
        recyclerView.setAdapter(adapterSearchMeals);
    }


    @Override
    public void showError(String errorNsg) {
        Log.i(TAG, "showError: " + errorNsg);
    }

    @Override
    public void onClickListener(String mealId) {
        SearchFragmentDirections.ActionSearchFragmentToMealDetailsFragment action = SearchFragmentDirections.actionSearchFragmentToMealDetailsFragment(mealId);
        Navigation.findNavController(requireView()).navigate(action);
    }
}