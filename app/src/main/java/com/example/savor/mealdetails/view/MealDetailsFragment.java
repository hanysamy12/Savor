package com.example.savor.mealdetails.view;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.savor.R;
import com.example.savor.database.MealsLocalDataSource;
import com.example.savor.mealdetails.presenter.MealDetailsFragmentContract;
import com.example.savor.mealdetails.presenter.MealDetailsPresenterImp;
import com.example.savor.model.MealsRemoteDataSource;
import com.example.savor.model.MealsRepositoryImp;
import com.example.savor.model.pojo.MealsItem;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Calendar;
import java.util.List;

public class MealDetailsFragment extends Fragment implements MealDetailsFragmentContract {
    private static final String TAG = "MealDetailsFragment";
    MealDetailsPresenterImp mealDetailsPresenterImp;
    ImageView imgMeal;
    ImageView imgAddToFav;
    ImageView imgAddToPlan;
    TextView txtMealName;
    TextView txtCountry;
    TextView txtCategory;
    TextView txtInstructions;
    YouTubePlayerView videoView;
    RecyclerView recyclerViewIngredient;
    String mealId; //from Saved Argus
    MealsItem mealItem; //from showMealDetails
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mealId = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealId();
        // Log.i(TAG, "onCreateView: MealId" + mealId);
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgMeal = view.findViewById(R.id.imageMealDetails);
        imgAddToFav = view.findViewById(R.id.imgAddToFav);
        imgAddToPlan = view.findViewById(R.id.imgToPlan);
        txtMealName = view.findViewById(R.id.txtMealNameMealDetails);
        txtCategory = view.findViewById(R.id.txtCategoryMealDetails);
        txtCountry = view.findViewById(R.id.txtAreaMealDetails);
        txtInstructions = view.findViewById(R.id.txtInstructions);
        videoView = view.findViewById(R.id.videoViewMealDetails);
        recyclerViewIngredient = view.findViewById(R.id.recyclerViewIngredientMealDetails);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        recyclerViewIngredient.setLayoutManager(layoutManager);
        mealDetailsPresenterImp = new MealDetailsPresenterImp(new MealsRepositoryImp(MealsRemoteDataSource.getInstance(), MealsLocalDataSource.getInstance(requireContext()))
                , this,requireContext());
        mealDetailsPresenterImp.getMealById(Integer.valueOf(mealId));
        imgAddToPlan.setOnClickListener(view1 -> {
            showDatePicker();
        });

    }

    @Override
    public void showMealDetails(MealsItem mealsItem, List<String> ingredientList, List<String> measureList) {
        this.mealItem = mealsItem;
        txtMealName.setText(mealsItem.getStrMeal());
        txtCountry.setText(mealsItem.getStrArea());
        txtCategory.setText(mealsItem.getStrCategory());
        txtInstructions.setText(mealsItem.getStrInstructions());
        Glide.with(requireContext()).load(mealsItem.getStrMealThumb())
                .apply(new RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.ic_app)
                        .error(R.drawable.ic_app)).into(imgMeal);
        AdapterIngredientMealDetails adapterIngredientMealDetails = new AdapterIngredientMealDetails(requireContext(), ingredientList, measureList, this);
        recyclerViewIngredient.setAdapter(adapterIngredientMealDetails);
        playVideo(mealsItem.getStrYoutube());
        imgAddToFav.setOnClickListener(view1 -> {
            mealDetailsPresenterImp.addToFavorite(mealsItem);
        });

    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessMessage(String successMsg) {
        Toast.makeText(requireContext(), successMsg, Toast.LENGTH_SHORT).show();
    }

    private void playVideo(String videoUrl) {
        if (videoUrl != null && !videoUrl.isEmpty()) {
            String videoId = videoUrl.split("v=")[1];
            Log.i(TAG, "videoID: " + videoId);
            videoView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    super.onReady(youTubePlayer);
                    youTubePlayer.cueVideo(videoId, 0); //loaVideo is auto Play

                }
            });
        }
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    Log.i(TAG, "Selected Date: " + selectedDate);
                    mealItem.setDate(selectedDate);
                    mealDetailsPresenterImp.addToPlan(mealItem);
                }, year, month, day);//set today as default
        datePickerDialog.show();
        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setMinDate(calendar.getTimeInMillis());
    }

}