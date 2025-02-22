package com.example.savor.homepage.view;

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
import com.example.savor.remote.model.pojo.MealsItem;
import com.example.savor.search.presenter.OnClickMealListener;

import java.util.List;

public class AdapterHomeList extends RecyclerView.Adapter<AdapterHomeList.ViewHolder> {
    Context context;
    List<MealsItem> meals;
    OnClickMealListener listener;
    private static final String TAG = "AdapterHomeList";

    public AdapterHomeList(Context context, List<MealsItem> meals,OnClickMealListener listener) {
        Log.i(TAG, "AdapterHomeList: ");
        this.context = context;
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View view = inflater.inflate(R.layout.item_list_home, recyclerView, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        holder.txtMealListHome.setText(meals.get(position).getStrMeal());

        // holder.constraintLayout.findViewById(R.id.recyclerViewHome);
        Glide.with(context).load(meals.get(position).getStrMealThumb())
                .apply(new RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.ic_app)
                        .error(R.drawable.ic_app)).into(holder.imgMealListHome);
        holder.imgMealListHome.setOnClickListener(view -> {
            listener.onClickListener(meals.get(position).getIdMeal());
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ViewHolder";
        TextView txtMealListHome;
        ImageView imgMealListHome;
        View view;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.i(TAG, "ViewHolder: ");
            view = itemView;
            txtMealListHome = view.findViewById(R.id.txtItemListHome);
            imgMealListHome = view.findViewById(R.id.imageItemListHome);
            constraintLayout = view.findViewById(R.id.listItem);
        }
    }
}
