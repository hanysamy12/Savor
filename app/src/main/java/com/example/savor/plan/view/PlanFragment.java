package com.example.savor.plan.view;

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
import android.widget.Toast;

import com.example.savor.R;
import com.example.savor.database.MealsLocalDataSource;
import com.example.savor.favorite.presenter.OnClickListener;
import com.example.savor.favorite.view.AdapterFavorite;
import com.example.savor.plan.presenter.PlanFragmentContract;
import com.example.savor.plan.presenter.PlanFragmentPresenter;
import com.example.savor.plan.presenter.PlanFragmentPresenterImp;
import com.example.savor.model.MealsRemoteDataSource;
import com.example.savor.model.MealsRepositoryImp;
import com.example.savor.model.pojo.MealsItem;

import java.util.ArrayList;
import java.util.List;

public class PlanFragment extends Fragment implements PlanFragmentContract, OnClickListener {
PlanFragmentPresenter planFragmentPresenter;
AdapterFavorite adapterFavorite;
RecyclerView recyclerView;
    private static final String TAG = "PlanFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recyclerViewPlan);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        planFragmentPresenter = new PlanFragmentPresenterImp(new MealsRepositoryImp(MealsRemoteDataSource.getInstance()
                , MealsLocalDataSource.getInstance(requireContext())),this);
        planFragmentPresenter.showPlan();
        adapterFavorite = new AdapterFavorite(requireContext(), new ArrayList<>(),this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterFavorite);

    }

    @Override
    public void onDeleteClicked(String id) {
        planFragmentPresenter.deleteFromPlan(id);
        Log.i(TAG, "onPlanClicked: ");
    }

    @Override
    public void onMealClicked(String id) {
        PlanFragmentDirections.ActionPlanFragmentToMealDetailsFragment action = PlanFragmentDirections.actionPlanFragmentToMealDetailsFragment(id);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void showPlanMeals(List<MealsItem> mealsItemList) {
        adapterFavorite.setMealsIteList(mealsItemList);
    }

    @Override
    public void showSuccessMsg(String successMsg) {
        Toast.makeText(requireContext(), successMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}