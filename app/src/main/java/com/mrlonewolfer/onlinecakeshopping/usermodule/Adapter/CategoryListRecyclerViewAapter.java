package com.mrlonewolfer.onlinecakeshopping.usermodule.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrlonewolfer.onlinecakeshopping.Model.CategoryInfo;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryListRecyclerViewAapter extends RecyclerView.Adapter<CategoryListRecyclerViewAapter.CategoryViewHolder> {
    List<CategoryInfo.Category> listCategoryInfo;

    Context context;
    OnCategoryClickListener listener;
    public  interface OnCategoryClickListener{
        void onCategoryClick(CategoryInfo.Category category);
    }
    public CategoryListRecyclerViewAapter(Context context,List<CategoryInfo.Category> listCategoryInfo,OnCategoryClickListener listener) {
        this.listCategoryInfo=listCategoryInfo;
        this.context=context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_list_row_item,viewGroup,false);
        CategoryViewHolder viewHolder=new CategoryViewHolder(view);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, final int position) {
        final CategoryInfo.Category category=listCategoryInfo.get(position);

        String cat_name=category.getCategoryName();
        String cat_image=category.getCategoryImage();
        String cat_id=category.getCid();

        String cat_image_path= DBConst.IMAGE_URL+ "/images/category/thumbs/"+cat_image;
        Log.e("imagepath", "onBindViewHolder: "+cat_image_path );
        categoryViewHolder.txtCategory.setText(cat_name);

        Picasso.with(context).load(cat_image_path).placeholder(R.mipmap.app_icon).into(categoryViewHolder.imgCategory);

        categoryViewHolder.txtCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCategoryClick(category);
            }
        });
        categoryViewHolder.imgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCategoryClick(category);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return listCategoryInfo.size();
    }

        public class CategoryViewHolder extends RecyclerView.ViewHolder{
            ImageView imgCategory;
            TextView txtCategory;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory=itemView.findViewById(R.id.imgCategory);
            txtCategory=itemView.findViewById(R.id.txtCategory);

        }
    }
}
