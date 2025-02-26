package com.example.savor.homepage.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.savor.R;
import com.example.savor.database.MealsLocalDataSource;
import com.example.savor.homepage.presenter.HomeScreenContract;
import com.example.savor.homepage.presenter.HomeScreenPresenterImp;
import com.example.savor.model.MealsRemoteDataSource;
import com.example.savor.model.MealsRepositoryImp;
import com.example.savor.model.pojo.MealsItemResponse;
import com.example.savor.search.presenter.OnClickMealListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment implements HomeScreenContract, OnClickMealListener {
    HomeScreenPresenterImp homeScreenPresenterImp;
    BottomNavigationView bottomNavigationView;
    ImageView imgRandomMeal;
    TextView txtRandomMeal;
    TextView txtMealOfToday;
    TextView txtMealsForYou;
    TextView txtNoConnection;
    RecyclerView recyclerViewHome;
    AdapterHomeList adapterHomeList;
    Boolean isOnline;
    CardView cardViewHome;
    LottieAnimationView lotti;
    private static final String TAG = "HomeFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        isOnline = HomeFragmentArgs.fromBundle(getArguments()).getIsOnline();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView = requireActivity().findViewById(R.id.bottomMenue);
        bottomNavigationView.setVisibility(VISIBLE);
        txtRandomMeal = view.findViewById(R.id.txtRandomMeal);
        txtMealOfToday = view.findViewById(R.id.txtMealOftoDAy);
        txtMealsForYou = view.findViewById(R.id.txtMealsForYou);
        txtNoConnection = view.findViewById(R.id.txtNoInterNet);
        imgRandomMeal = view.findViewById(R.id.imageRandomMeal);
        recyclerViewHome = view.findViewById(R.id.recyclerViewHome);
        cardViewHome = view.findViewById(R.id.cardViewHome);
        lotti = view.findViewById(R.id.lottyImage);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewHome.setLayoutManager(layoutManager);
        homeScreenPresenterImp = new HomeScreenPresenterImp(new MealsRepositoryImp(MealsRemoteDataSource.getInstance(), MealsLocalDataSource.getInstance(requireContext()))
                , this, requireContext());

        homeScreenPresenterImp.handelOnConnectionChanges(isOnline);


    }

    @Override
    public void showRandomMeal(MealsItemResponse mealsItemResponse) {
        txtRandomMeal.setText(mealsItemResponse.getMeals().get(0).getStrMeal());
        Log.i(TAG, "showRandomMeal: " + mealsItemResponse.getMeals().get(0).getStrMeal());
        Glide.with(requireContext()).load(mealsItemResponse.getMeals().get(0).getStrMealThumb())
                .apply(new RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)).into(imgRandomMeal);
        imgRandomMeal.setOnClickListener(view -> {
            HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
                    HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(mealsItemResponse.getMeals().get(0).getIdMeal());
            Navigation.findNavController(view).navigate(action);
        });
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showHomeMeals(MealsItemResponse mealsItemResponse) {
        adapterHomeList = new AdapterHomeList(requireContext(), mealsItemResponse.getMeals(), this);
        recyclerViewHome.setAdapter(adapterHomeList);
    }

    @Override
    public void showLotti() {
        onOffline();
    }

    @Override
    public void hideLotti() {
        lotti.setVisibility(GONE);
        onOnline();
    }

    @Override
    public void onClickListener(String mealId) {
        HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
                HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(mealId);
        Navigation.findNavController(requireView()).navigate(action);
    }

    private void onOffline() {
        cardViewHome.setVisibility(GONE);
        txtMealsForYou.setVisibility(GONE);
        txtMealOfToday.setVisibility(GONE);
        recyclerViewHome.setVisibility(GONE);
        txtNoConnection.setVisibility(VISIBLE);
        lotti.setVisibility(VISIBLE);

    }

    private void onOnline() {
        cardViewHome.setVisibility(VISIBLE);
        txtMealsForYou.setVisibility(VISIBLE);
        txtMealOfToday.setVisibility(VISIBLE);
        recyclerViewHome.setVisibility(VISIBLE);
        txtNoConnection.setVisibility(GONE);
        lotti.setVisibility(GONE);

    }
}