package com.example.savor.favorite.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        FavoriteFragmentDirections.ActionFavoriteFragmentToMealDetailsFragment action = FavoriteFragmentDirections.actionFavoriteFragmentToMealDetailsFragment(id);
        Navigation.findNavController(recyclerView).navigate(action);
    }

    private void showDialog(String id) {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_dialog, null);

        TextView dialogTitle = dialogView.findViewById(R.id.dialog_title);
        TextView dialogMessage = dialogView.findViewById(R.id.dialog_message);
        Button positiveButton = dialogView.findViewById(R.id.dialog_positive_button);
        Button negativeButton = dialogView.findViewById(R.id.dialog_negative_button);

        dialogTitle.setText(R.string.warning);
        dialogMessage.setText(R.string.are_you_sure);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        positiveButton.setOnClickListener(v -> {
            favoritePresenterImp.deleteMeal(id);
            dialog.dismiss();
        });

        negativeButton.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }


}