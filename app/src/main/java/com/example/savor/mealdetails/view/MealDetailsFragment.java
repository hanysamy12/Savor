package com.example.savor.mealdetails.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.savor.R;
import com.example.savor.mealdetails.presenter.MealDetailsFragmentContract;
import com.example.savor.mealdetails.presenter.MealDetailsPresenterImp;
import com.example.savor.remote.model.MealsRemoteDataSource;
import com.example.savor.remote.model.MealsRepositoryImp;
import com.example.savor.remote.model.pojo.MealsItem;

import java.util.List;

public class MealDetailsFragment extends Fragment implements MealDetailsFragmentContract {
    private static final String TAG = "MealDetailsFragment";
    MealDetailsPresenterImp mealDetailsPresenterImp;
    ImageView imgMeal;
    TextView txtMealName;
    TextView txtCountry;
    TextView txtCategory;
    TextView txtInstructions;
    VideoView videoView;
    RecyclerView recyclerViewIngredient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgMeal = view.findViewById(R.id.imageMealDetails);
        txtMealName=view.findViewById(R.id.txtMealNameMealDetails);
        txtCategory = view.findViewById(R.id.txtCategoryMealDetails);
        txtCountry = view.findViewById(R.id.txtAreaMealDetails);
        txtInstructions = view.findViewById(R.id.txtInstructions);
        videoView = view.findViewById(R.id.videoViewMealDetails);
        recyclerViewIngredient = view.findViewById(R.id.recyclerViewIngredientMealDetails);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL,false);
        recyclerViewIngredient.setLayoutManager(layoutManager);
        mealDetailsPresenterImp = new MealDetailsPresenterImp(new MealsRepositoryImp(MealsRemoteDataSource.getInstance()),this);
        mealDetailsPresenterImp.getMealById(52812);


    }

    @Override
    public void showMealDetails(MealsItem mealsItem , List<String>ingredientList,List<String> measureList) {
        txtMealName.setText(mealsItem.getStrMeal());
        txtCountry.setText(mealsItem.getStrArea());
        txtCategory.setText(mealsItem.getStrCategory());
        txtInstructions.setText(mealsItem.getStrInstructions());
        Glide.with(requireContext()).load(mealsItem.getStrMealThumb())
                .apply(new RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.ic_app)
                        .error(R.drawable.ic_app)).into(imgMeal);

        AdapterIngredientMealDetails adapterIngredientMealDetails= new AdapterIngredientMealDetails(requireContext(),ingredientList,measureList,this);
        recyclerViewIngredient.setAdapter(adapterIngredientMealDetails);
        Log.i(TAG, "showIng: "+ingredientList);
        Log.i(TAG, "showMeg: "+measureList);
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

}