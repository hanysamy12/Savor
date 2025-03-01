package com.example.savor.mealdetails.view;

import static android.view.View.GONE;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.savor.R;
import com.example.savor.database.MealsLocalDataSource;
import com.example.savor.mealdetails.presenter.MealDetailsFragmentContract;
import com.example.savor.mealdetails.presenter.MealDetailsPresenterImp;
import com.example.savor.remote.MealsRemoteDataSource;
import com.example.savor.remote.MealsRepositoryImp;
import com.example.savor.remote.pojo.MealsItem;
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
    LottieAnimationView lotti;
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
        lotti = view.findViewById(R.id.lottiImageMealDetails);
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
                        .centerCrop()
                        //.placeholder(R.drawable.ic_app)
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

        Calendar maxCalender = Calendar.getInstance();
        maxCalender.add(Calendar.DAY_OF_MONTH , 7);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(selectedYear, selectedMonth, selectedDay);
                    String dayOfWeek = getDayOfWeek(selectedCalendar.get(Calendar.DAY_OF_WEEK));
                    Log.i(TAG, "showDatePicker: "+dayOfWeek);
                    mealItem.setDate(dayOfWeek);
                    mealDetailsPresenterImp.addToPlan(mealItem);
                }, year, month, day);
        datePickerDialog.show();
        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setMinDate(calendar.getTimeInMillis());
        datePicker.setMaxDate(maxCalender.getTimeInMillis());
    }
@Override
    public void showDialog() {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_dialog, null);

        TextView dialogTitle = dialogView.findViewById(R.id.dialog_title);
        TextView dialogMessage = dialogView.findViewById(R.id.dialog_message);
        Button positiveButton = dialogView.findViewById(R.id.dialog_positive_button);
        Button negativeButton = dialogView.findViewById(R.id.dialog_negative_button);

        dialogTitle.setText(R.string.you_must_login_to_use_this_feature);
        dialogMessage.setText(R.string.login);
        positiveButton.setText(R.string.ok);
        positiveButton.setBackgroundColor(getResources().getColor(R.color.primary_color,null));
        negativeButton.setBackgroundColor(getResources().getColor(R.color.background_color,null));
        negativeButton.setTextColor(getResources().getColor(R.color.black,null));
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        positiveButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireView());
            NavOptions navOptions= new NavOptions.Builder()
                    .setPopUpTo(R.id.mealDetailsFragment,true)
                    .build();
            navController.navigate(R.id.loginFragment,null,navOptions);
            dialog.dismiss();
        });

        negativeButton.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void hideLotti() {
        lotti.setVisibility(GONE);
    }

    private String getDayOfWeek(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
            default:
                return "Unknown";
        }
}
}