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

import com.example.savor.R;
import com.example.savor.database.MealsLocalDataSource;
import com.example.savor.remote.model.MealsRemoteDataSource;
import com.example.savor.remote.model.MealsRepositoryImp;
import com.example.savor.remote.model.pojo.AreasItem;
import com.example.savor.search.presenter.SearchFragmentContract;
import com.example.savor.search.presenter.SearchPresenterImp;

import java.util.List;

public class AdapterSearchAreas extends RecyclerView.Adapter<AdapterSearchAreas.ViewHolder>{
Context context;
List<AreasItem> areas;
SearchPresenterImp searchPresenterImp;
SearchFragmentContract searchFragmentContract;
    public AdapterSearchAreas(Context context, List<AreasItem> areas , SearchFragmentContract searchFragmentContract) {
        this.context = context;
        this.areas = areas;
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
        holder.txtAreaName.setText(areas.get(position).getStrArea());
       /* Glide.with(context).load(areas.get(position))
                .apply(new RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.ic_app)
                        .error(R.drawable.ic_app)).into(holder.imgArea);*/
        holder.imgArea.setOnClickListener(view -> {
            searchPresenterImp = new SearchPresenterImp(new MealsRepositoryImp(MealsRemoteDataSource.getInstance(), MealsLocalDataSource.getInstance(context)),searchFragmentContract);
            searchPresenterImp.getFilteredMealsByCountry(areas.get(position).getStrArea());
        });
    }

    @Override
    public int getItemCount() {
        return areas.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtAreaName;
        ImageView imgArea;
        ConstraintLayout constraintLayout;
         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             txtAreaName = itemView.findViewById(R.id.txtItemListSearch);
             imgArea = itemView.findViewById(R.id.imageItemListSearch);
             constraintLayout = itemView.findViewById(R.id.listItemSearch);

         }
     }
}
