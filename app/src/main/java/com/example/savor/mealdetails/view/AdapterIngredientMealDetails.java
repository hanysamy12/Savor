package com.example.savor.mealdetails.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.savor.R;
import com.example.savor.mealdetails.presenter.MealDetailsFragmentContract;

import java.util.List;

public class AdapterIngredientMealDetails extends RecyclerView.Adapter<AdapterIngredientMealDetails.ViewHolder>{
    Context context;
    List<String> ingredient;
    List<String> measure;
    MealDetailsFragmentContract mealDetailsFragmentContract;
    public AdapterIngredientMealDetails(Context context, List<String> ingredient,List<String>measure,MealDetailsFragmentContract mealDetailsFragmentContract) {
        this.context = context;
        this.ingredient = ingredient;
        this.measure = measure;
        this.mealDetailsFragmentContract = mealDetailsFragmentContract;
    }

    @NonNull
    @Override
    public AdapterIngredientMealDetails.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.intem_list_ingredient_meal_details,recyclerView,false);
        return new AdapterIngredientMealDetails.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterIngredientMealDetails.ViewHolder holder, int position) {
        holder.txtIngredientName.setText(ingredient.get(position));
        holder.txtMeasure.setText(measure.get(position));
        Glide.with(context).load("https://themealdb.com/images/ingredients/"+ingredient.get(position)+".png")
                .apply(new RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.ic_app)
                        .error(R.drawable.ic_app)).into(holder.imgIngredient);
    }

    @Override
    public int getItemCount() {
        return ingredient.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtIngredientName;
        ImageView imgIngredient;
        TextView txtMeasure;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIngredientName = itemView.findViewById(R.id.txtIngNameMealDetails);
            txtMeasure = itemView.findViewById(R.id.txtMeasureMealDetails);
            imgIngredient = itemView.findViewById(R.id.imgIngredientMealDetails);
            constraintLayout = itemView.findViewById(R.id.listIngredientMealDetails);

        }
    }

}
