package com.example.savor.favorite.view.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.savor.R;
import com.example.savor.database.MealsLocalDataSource;
import com.example.savor.favorite.view.presenter.FavoriteFragmentContract;
import com.example.savor.favorite.view.presenter.FavoritePresenterImp;
import com.example.savor.favorite.view.presenter.OnClickListener;
import com.example.savor.remote.model.MealsRemoteDataSource;
import com.example.savor.remote.model.MealsRepositoryImp;
import com.example.savor.remote.model.pojo.MealsItem;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment implements FavoriteFragmentContract, OnClickListener {
    FavoritePresenterImp favoritePresenterImp;
    RecyclerView recyclerView;
    AdapterFavorite adapterFavorite;

    private static final String TAG = "FavoriteFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewFavorite);
        favoritePresenterImp = new FavoritePresenterImp(new MealsRepositoryImp(MealsRemoteDataSource.getInstance(), MealsLocalDataSource.getInstance(requireContext())), this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LiveData<List<MealsItem>> liveMealItemList = favoritePresenterImp.showFavorite("1");
        adapterFavorite = new AdapterFavorite(requireContext(), new ArrayList<>(),this);
       recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterFavorite);
        liveMealItemList.observe((LifecycleOwner) requireContext(), mealsItemList -> {
            adapterFavorite.setMealsFavoriteList(mealsItemList);
            Log.i(TAG, "FavoriteFragment: "+mealsItemList.size());
        });
    }

    @Override
    public void showFavoriteMeals(List<MealsItem> mealsItemList) {

    }

    @Override
    public void showSuccessMsg(String successMsg) {

    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    public void onFavoriteClicked(MealsItem mealsItem) {
        favoritePresenterImp.deleteFromFavorite(mealsItem);
    }
}