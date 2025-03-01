package com.example.savor.search.view;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savor.R;
import com.example.savor.database.MealsLocalDataSource;
import com.example.savor.remote.MealsRemoteDataSource;
import com.example.savor.remote.MealsRepositoryImp;
import com.example.savor.remote.pojo.AreaResponse;
import com.example.savor.remote.pojo.CategoriesResponse;
import com.example.savor.remote.pojo.FilteredResponse;
import com.example.savor.remote.pojo.IngredientResponse;
import com.example.savor.search.presenter.OnClickListenerArea;
import com.example.savor.search.presenter.OnClickListenerCategory;
import com.example.savor.search.presenter.OnClickListenerIngredient;
import com.example.savor.search.presenter.OnClickMealListener;
import com.example.savor.search.presenter.SearchFragmentContract;
import com.example.savor.search.presenter.SearchPresenter;
import com.example.savor.search.presenter.SearchPresenterImp;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SearchFragment extends Fragment implements SearchFragmentContract
        , OnClickMealListener, OnClickListenerCategory, OnClickListenerIngredient, OnClickListenerArea {
    ChipGroup chipGroup;
    EditText txtSearch;
    SearchPresenter searchPresenter;
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
        /*Animation animation = AnimationUtils.loadAnimation(requireContext(),R.anim.anim_list);
        recyclerView.setAnimation(animation);*/
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
        adapterCategories = new AdapterSearchCategories(requireContext()
                , categoriesResponse.getCategories(), this, this);

        recyclerView.setAdapter(adapterCategories);
        Log.i(TAG, "showAllCategories: " + categoriesResponse.getCategories().get(0).getStrCategory());
    }

    @Override
    public void showAllIngredient(IngredientResponse ingredientResponse) {
        adapterIngredient = new AdapterSearchIngredient(requireContext(), ingredientResponse.getIngredient(), this, this);

        recyclerView.setAdapter(adapterIngredient);
        Log.i(TAG, "showAllIngredient: " + ingredientResponse.getIngredient().get(0).getStrIngredient());
    }

    @Override
    public void showAllAreas(AreaResponse areaResponse) {
        adapterSearchAreas = new AdapterSearchAreas(requireContext(), areaResponse.getAreas(), this, this);
        recyclerView.setAdapter(adapterSearchAreas);
        Log.i(TAG, "showAllAreas: " + areaResponse.getAreas().get(0).getStrArea());
    }

    @Override
    public void showFilteredMeals(FilteredResponse filteredResponse) {
        layoutManager.setSpanCount(1);
        adapterSearchMeals = new AdapterSearchMeals(requireContext(), filteredResponse.getMealsFilteredItems(), this);
        Disposable subscribe = Observable.create(emitter -> {
                    txtSearch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                            emitter.onNext(charSequence);
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                }).debounce(1, TimeUnit.SECONDS)
                .map(charSqence -> charSqence.toString().toLowerCase().trim())
                .distinctUntilChanged()
                .map(search -> filteredResponse.getMealsFilteredItems().stream()
                        .filter(mealsFilteredItem -> mealsFilteredItem.getStrMeal().toLowerCase().contains(search))
                        .collect(Collectors.toList()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealsFilteredItems -> {
                    adapterSearchMeals.setSearchMealChanges(mealsFilteredItems);
                }, throwable -> {
                    Log.i(TAG, "showFilteredMeals: Error " + throwable);
                });
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

    @Override
    public void onClickCatListener(String category) {
        searchPresenter.getFilteredMealsByCategory(category);
    }

    @Override
    public void onClickIngListener(String ing) {
        searchPresenter.getFilteredMealsByIngredient(ing);
    }

    @Override
    public void onClickAreaListener(String area) {
        searchPresenter.getFilteredMealsByCountry(area);
    }
}