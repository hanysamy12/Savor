package com.example.savor.favorite.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savor.R;
import com.example.savor.database.MealsLocalDataSource;
import com.example.savor.favorite.presenter.FavoriteFragmentContract;
import com.example.savor.favorite.presenter.FavoritePresenterImp;
import com.example.savor.favorite.presenter.OnClickListener;
import com.example.savor.model.MealsRemoteDataSource;
import com.example.savor.model.MealsRepositoryImp;
import com.example.savor.model.pojo.MealsItem;

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
        favoritePresenterImp.showMeals();
        adapterFavorite = new AdapterFavorite(requireContext(), new ArrayList<>(), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterFavorite);

    }


    @Override
    public void showMeals(List<MealsItem> mealsItemList) {
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

    @Override
    public void onDeleteClicked(String id) {
        showDialog(id);
    }

    @Override
    public void onMealClicked(String id) {
        FavoriteFragmentDirections.ActionFavoriteFragmentToMealDetailsFragment action =
                FavoriteFragmentDirections.actionFavoriteFragmentToMealDetailsFragment(id);
        Navigation.findNavController(recyclerView).navigate(action);
    }

    private void showDialog(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Attention")
                .setMessage("Are You Sure")
                .setPositiveButton("yes", (dialog, i) -> {
                    favoritePresenterImp.deleteMeal(id);
                    dialog.dismiss();
                })
                .setNegativeButton("No", (dialogInterface, i) -> {
                    Toast.makeText(requireContext(), "Right", Toast.LENGTH_SHORT).show();
                })
                .setCancelable(true);
        builder.create().show();
    }
}