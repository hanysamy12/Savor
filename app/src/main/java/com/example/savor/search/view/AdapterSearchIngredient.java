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
import com.example.savor.database.MealsLocalDataSource;
import com.example.savor.remote.model.MealsRemoteDataSource;
import com.example.savor.remote.model.MealsRepositoryImp;
import com.example.savor.remote.model.pojo.IngredientItem;
import com.example.savor.search.presenter.SearchFragmentContract;
import com.example.savor.search.presenter.SearchPresenterImp;

import java.util.List;

public class AdapterSearchIngredient extends RecyclerView.Adapter<AdapterSearchIngredient.ViewHolder>{
Context context;
List<IngredientItem> ingredient;
SearchPresenterImp searchPresenterImp;
SearchFragmentContract searchFragmentContract;
    public AdapterSearchIngredient(Context context, List<IngredientItem> ingredient,SearchFragmentContract searchFragmentContract) {
        this.context = context;
        this.ingredient = ingredient;
        this.searchFragmentContract = searchFragmentContract;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_search,recyclerView,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtIngredientName.setText(ingredient.get(position).getStrIngredient());
        Glide.with(context).load("https://themealdb.com/images/ingredients/"+ingredient.get(position).getStrIngredient()+".png")
                .apply(new RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.ic_app)
                        .error(R.drawable.ic_app)).into(holder.imgIngredient);
        holder.imgIngredient.setOnClickListener(view -> {
            searchPresenterImp = new SearchPresenterImp(new MealsRepositoryImp(MealsRemoteDataSource.getInstance(), MealsLocalDataSource.getInstance(context)), searchFragmentContract);
            searchPresenterImp.getFilteredMealsByIngredient(ingredient.get(position).getStrIngredient());
        });
    }

    @Override
    public int getItemCount() {
        return ingredient.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtIngredientName;
        ImageView imgIngredient;
        ConstraintLayout constraintLayout;
         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             txtIngredientName = itemView.findViewById(R.id.txtItemListSearch);
             imgIngredient = itemView.findViewById(R.id.imageItemListSearch);
             constraintLayout = itemView.findViewById(R.id.listItemSearch);

         }
     }
}
