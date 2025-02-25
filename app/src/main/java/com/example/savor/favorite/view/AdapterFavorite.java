package com.example.savor.favorite.view;

import static android.view.View.GONE;

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
import com.example.savor.favorite.presenter.OnClickListener;
import com.example.savor.model.pojo.MealsItem;

import java.util.List;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.ViewHolder> {
    Context context;
    List<MealsItem> mealsIteList;
    OnClickListener listener;
    private static final String TAG = "AdapterFavorite";
    public AdapterFavorite(Context context, List<MealsItem> mealsFavoriteList,OnClickListener listener) {
        this.context = context;
        this.mealsIteList = mealsFavoriteList;
        this.listener = listener;
    }
    public void setMealsIteList(List<MealsItem> updatedMealList) {
        this.mealsIteList = updatedMealList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterFavorite.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_favorite, recyclerView, false);
        return new AdapterFavorite.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull AdapterFavorite.ViewHolder holder, int position) {
        holder.txtMealName.setText(mealsIteList.get(position).getStrMeal());
        Glide.with(context).load(mealsIteList.get(position).getStrMealThumb())
                .apply(new RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.ic_app)
                        .error(R.drawable.ic_app)).into(holder.imgMeal);
        holder.imgDelete.setOnClickListener(view -> {
            listener.onDeleteClicked(mealsIteList.get(position).getIdMeal());
        });
        if(mealsIteList.get(position).getDate()==null) {
            holder.txtDate.setVisibility(GONE);
        }else{
        holder.txtDate.setText(mealsIteList.get(position).getDate());
        }
        holder.constraintLayout.setOnClickListener(view -> {
            listener.onMealClicked(mealsIteList.get(position).getIdMeal());
        });
    }

    @Override
    public int getItemCount() {
        return mealsIteList.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMealName;
        TextView txtDate;
        ImageView imgMeal;
        ImageView imgDelete;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMealName = itemView.findViewById(R.id.txtMealNameFavorite);
            txtDate = itemView.findViewById(R.id.txtMealDate);
            imgMeal = itemView.findViewById(R.id.imgMealFavorite);
            imgDelete = itemView.findViewById(R.id.imgDeleteFavorite);
            constraintLayout = itemView.findViewById(R.id.listItemFavorite);
        }
    }
}
