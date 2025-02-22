package com.example.savor.homepage.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.savor.R;
import com.example.savor.database.MealsLocalDataSource;
import com.example.savor.homepage.presenter.HomeScreenContract;
import com.example.savor.homepage.presenter.HomeScreenPresenterImp;
import com.example.savor.remote.model.MealsRemoteDataSource;
import com.example.savor.remote.model.MealsRepositoryImp;
import com.example.savor.remote.model.pojo.MealsItemResponse;
import com.example.savor.search.presenter.OnClickMealListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment implements HomeScreenContract , OnClickMealListener {
    HomeScreenPresenterImp homeScreenPresenterImp;
    BottomNavigationView bottomNavigationView;
    ImageView imgRandomMeal;
    TextView txtRandomMeal;
    RecyclerView recyclerViewHome;
    AdapterHomeList adapterHomeList;

    private static final String TAG = "HomeFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView = requireActivity().findViewById(R.id.bottomMenue);
        bottomNavigationView.setVisibility(View.VISIBLE);
        txtRandomMeal = view.findViewById(R.id.txtRandomMeal);
        imgRandomMeal = view.findViewById(R.id.imageRandomMeal);
        recyclerViewHome = view.findViewById(R.id.recyclerViewHome);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewHome.setLayoutManager(layoutManager);
        homeScreenPresenterImp = new HomeScreenPresenterImp(new MealsRepositoryImp(MealsRemoteDataSource.getInstance(), MealsLocalDataSource.getInstance(requireContext()))
                ,this);
        homeScreenPresenterImp.getRandomMeal();
        homeScreenPresenterImp.getHomeMeals();


    }


    @Override
    public void showRandomMeal(MealsItemResponse mealsItemResponse) {
        txtRandomMeal.setText(mealsItemResponse.getMeals().get(0).getStrMeal());
        Log.i(TAG, "showRandomMeal: "+mealsItemResponse.getMeals().get(0).getStrMeal());
        Glide.with(requireContext()).load(mealsItemResponse.getMeals().get(0).getStrMealThumb())
                .apply(new RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground)).into( imgRandomMeal);
        imgRandomMeal.setOnClickListener(view -> {
            HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action = HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(mealsItemResponse.getMeals().get(0).getIdMeal());
            Navigation.findNavController(view).navigate(action);
        });
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        Log.i(TAG, "showErrorMsg: "+errorMsg);
    }

    @Override
    public void showHomeMeals(MealsItemResponse mealsItemResponse) {
        Log.i(TAG, "showHomeMeals: "+mealsItemResponse.getMeals().get(2).getStrMeal());
        adapterHomeList  = new AdapterHomeList(requireContext(),mealsItemResponse.getMeals(),this);
        recyclerViewHome.setAdapter(adapterHomeList);
    }

    @Override
    public void onClickListener(String mealId) {
        HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action = HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(mealId);
        Navigation.findNavController(requireView()).navigate(action);
    }
}