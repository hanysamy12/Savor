package com.example.savor.search.view;

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
import com.example.savor.remote.model.pojo.MealsFilteredItem;

import java.util.List;

public class AdapterSearchMeals extends RecyclerView.Adapter<AdapterSearchMeals.ViewHolder>{
Context context;
List<MealsFilteredItem> mealsFilteredItems;

    public AdapterSearchMeals(Context context, List<MealsFilteredItem> mealsFilteredItems) {
        this.context = context;
        this.mealsFilteredItems = mealsFilteredItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_search_meal,recyclerView,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMealName.setText(mealsFilteredItems.get(position).getStrMeal());
        Glide.with(context).load(mealsFilteredItems.get(position).getStrMealThumb())
                .apply(new RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.ic_app)
                        .error(R.drawable.ic_app)).into(holder.imgMeal);

    }

    @Override
    public int getItemCount() {
        return mealsFilteredItems.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMealName;
        ImageView imgMeal;
        ConstraintLayout constraintLayout;
         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             txtMealName = itemView.findViewById(R.id.txtItemListSearchMeal);
             imgMeal = itemView.findViewById(R.id.imageItemListSearchMeal);
             constraintLayout = itemView.findViewById(R.id.listItemSearchMeal);

         }
     }
}
