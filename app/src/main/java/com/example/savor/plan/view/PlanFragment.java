package com.example.savor.plan.view;

import android.os.Bundle;
import android.util.Log;
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
import com.example.savor.favorite.presenter.OnClickListener;
import com.example.savor.favorite.view.AdapterFavorite;
import com.example.savor.remote.MealsRemoteDataSource;
import com.example.savor.remote.MealsRepositoryImp;
import com.example.savor.remote.pojo.MealsItem;
import com.example.savor.plan.presenter.PlanFragmentContract;
import com.example.savor.plan.presenter.PlanFragmentPresenter;
import com.example.savor.plan.presenter.PlanFragmentPresenterImp;

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
        recyclerView = view.findViewById(R.id.recyclerViewPlan);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        planFragmentPresenter = new PlanFragmentPresenterImp(new MealsRepositoryImp(MealsRemoteDataSource.getInstance()
                , MealsLocalDataSource.getInstance(requireContext())), this);
        planFragmentPresenter.showPlan();
        adapterFavorite = new AdapterFavorite(requireContext(), new ArrayList<>(), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterFavorite);

    }

    @Override
    public void onDeleteClicked(String id) {
        showDialog(id);
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
            planFragmentPresenter.deleteFromPlan(id);
            dialog.dismiss();
        });

        negativeButton.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }



}