package com.example.savor.favorite.view.view;

import android.content.Context;
import android.util.Log;
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
import com.example.savor.favorite.view.presenter.OnClickListener;
import com.example.savor.remote.model.pojo.MealsItem;

import java.util.List;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.ViewHolder> {
    Context context;
    List<MealsItem> mealsFavoriteList;
    OnClickListener listener;
    private static final String TAG = "AdapterFavorite";
    public AdapterFavorite(Context context, List<MealsItem> mealsFavoriteList,OnClickListener listener) {
        this.context = context;
        this.mealsFavoriteList = mealsFavoriteList;
        this.listener = listener;
    }
    public void setMealsFavoriteList(List<MealsItem> updatedMealList) {
        this.mealsFavoriteList = updatedMealList;
        Log.i(TAG, "setMealsFavoriteList: "+mealsFavoriteList.size()+" "+updatedMealList.size());
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
        holder.txtMealName.setText(mealsFavoriteList.get(position).getStrMeal());
        Glide.with(context).load(mealsFavoriteList.get(position).getStrMealThumb())
                .apply(new RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.ic_app)
                        .error(R.drawable.ic_app)).into(holder.imgMeal);
        Log.i(TAG, "onBindViewHolder: ");
        holder.imgDelete.setOnClickListener(view -> {
            listener.onFavoriteClicked(mealsFavoriteList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: "+mealsFavoriteList.size());
        return mealsFavoriteList.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMealName;
        ImageView imgMeal;
        ImageView imgDelete;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMealName = itemView.findViewById(R.id.txtMealNameFavorite);
            imgMeal = itemView.findViewById(R.id.imgMealFavorite);
            imgDelete = itemView.findViewById(R.id.imgDeleteFavorite);
            constraintLayout = itemView.findViewById(R.id.listItemFavorite);
            Log.i(TAG, "ViewHolder: ");

        }
    }
}
