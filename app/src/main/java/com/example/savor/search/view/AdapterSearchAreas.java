package com.example.savor.search.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.savor.R;
import com.example.savor.remote.pojo.AreasItem;
import com.example.savor.search.presenter.OnClickListenerArea;
import com.example.savor.search.presenter.SearchFragmentContract;

import java.util.List;

public class AdapterSearchAreas extends RecyclerView.Adapter<AdapterSearchAreas.ViewHolder>{
Context context;
List<AreasItem> areas;
SearchFragmentContract searchFragmentContract;
OnClickListenerArea listener;
    public AdapterSearchAreas(Context context, List<AreasItem> areas , SearchFragmentContract searchFragmentContract,OnClickListenerArea listener) {
        this.context = context;
        this.areas = areas;
        this.searchFragmentContract = searchFragmentContract;
        this.listener = listener;
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
        String flagCode = getCountryCode(areas.get(position).getStrArea().toLowerCase());

        String flagUrl = "https://www.themealdb.com/images/icons/flags/big/64/"+flagCode+".png";
        Log.i("TAG", "onBindViewHolder: "+flagCode);

        Glide.with(context).load(flagUrl)
                .apply(new RequestOptions()
                        .circleCrop()
                        .placeholder(R.drawable.ic_app)
                        .error(R.drawable.ic_app)).into(holder.imgArea);
        holder.imgArea.setOnClickListener(view -> {
            listener.onClickAreaListener(areas.get(position).getStrArea());

        });
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.anim_list);
        holder.constraintLayout.setAnimation(animation);
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

    private String getCountryCode(String countryName) {
        if (countryName == null) return "null";

        countryName = countryName.toLowerCase();

        switch (countryName) {
            case "american": return "us";
            case "british": return "gb";
            case "canadian": return "ca";
            case "chinese": return "cn";
            case "croatian": return "hr";
            case "dutch": return "nl";
            case "egyptian": return "eg";
            case "french": return "fr";
            case "greek": return "gr";
            case "indian": return "in";
            case "irish": return "ie";
            case "italian": return "it";
            case "jamaican": return "jm";
            case "japanese": return "jp";
            case "kenyan": return "ke";
            case "malaysian": return "my";
            case "mexican": return "mx";
            case "moroccan": return "ma";
            case "polish": return "pl";
            case "portuguese": return "pt";
            case "russian": return "ru";
            case "spanish": return "es";
            case "thai": return "th";
            case "filipino": return "th";
            case "norwegian": return "pt";
            case "ukrainian": return "ke";
            case "uruguayan": return "jm";
            case "tunisian": return "tn";
            case "turkish": return "tr";
            case "vietnamese": return "vn";
            default: return "null";
        }
    }

}
