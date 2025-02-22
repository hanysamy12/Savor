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
import com.example.savor.remote.model.pojo.CategoriesItem;
import com.example.savor.search.presenter.SearchFragmentContract;
import com.example.savor.search.presenter.SearchPresenterImp;

import java.util.List;

public class AdapterSearchCategories extends RecyclerView.Adapter<AdapterSearchCategories.ViewHolder>{
Context context;
List<CategoriesItem> categories;
SearchPresenterImp searchPresenterImp;
SearchFragmentContract searchFragmentContract;
    public AdapterSearchCategories(Context context, List<CategoriesItem> categories,SearchFragmentContract searchFragmentContract) {
        this.context = context;
        this.categories = categories;
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
        holder.txtCategoryName.setText(categories.get(position).getStrCategory());
        Glide.with(context).load(categories.get(position).getStrCategoryThumb())
                .apply(new RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.ic_app)
                        .error(R.drawable.ic_app)).into(holder.imgCategory);
        holder.imgCategory.setOnClickListener(view -> {
            searchPresenterImp = new SearchPresenterImp(new MealsRepositoryImp(MealsRemoteDataSource.getInstance(), MealsLocalDataSource.getInstance(context)),searchFragmentContract);
            searchPresenterImp.getFilteredMealsByCategory(categories.get(position).getStrCategory());
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtCategoryName;
        ImageView imgCategory;
        ConstraintLayout constraintLayout;
         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             txtCategoryName = itemView.findViewById(R.id.txtItemListSearch);
             imgCategory = itemView.findViewById(R.id.imageItemListSearch);
             constraintLayout = itemView.findViewById(R.id.listItemSearch);

         }
     }
}
