package com.mrlonewolfer.onlinecakeshopping.adminmodule.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mrlonewolfer.onlinecakeshopping.Model.CategoryInfo;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.mrlonewolfer.onlinecakeshopping.adminmodule.Fragment.ManageCategoryFragment;
import com.mrlonewolfer.onlinecakeshopping.usermodule.Adapter.CategoryListRecyclerViewAapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ManageCategoryAdapter extends RecyclerView.Adapter<ManageCategoryAdapter.CategoryViewHolder> {
    Context context;
    List<CategoryInfo.Category> listCategoryInfo;
    OnManageCategoryClickListener listener;
    View view;
    public interface OnManageCategoryClickListener{
        void OnManageCategoryClick(CategoryInfo.Category category,String action,View view);
    }
    public ManageCategoryAdapter(Context context, List<CategoryInfo.Category> listCategoryInfo, OnManageCategoryClickListener listener) {
        this.context=context;
        this.listCategoryInfo=listCategoryInfo;
        this.listener=listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manage_category_row_item,viewGroup,false);
        CategoryViewHolder viewHolder=new CategoryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder categoryViewHolder, int position) {
        final CategoryInfo.Category category=listCategoryInfo.get(position);

        if(position==0){
            categoryViewHolder.txtCategory.setVisibility(View.GONE);
            categoryViewHolder.imgedit.setVisibility(View.GONE);
            categoryViewHolder.imgdelete.setVisibility(View.GONE);
            categoryViewHolder.imgCategory.setVisibility(View.GONE);
        }else {
            String cat_name = category.getCategoryName();
            String cat_image = category.getCategoryImage();
            String cat_id = category.getCid();

            String cat_image_path = DBConst.IMAGE_URL + "/images/category/thumbs/" + cat_image;
            Log.e("imagepath", "onBindViewHolder: " + cat_image_path);
            categoryViewHolder.txtCategory.setText(cat_name);

            Picasso.with(context).load(cat_image_path).placeholder(R.mipmap.app_icon).into(categoryViewHolder.imgCategory);

            categoryViewHolder.imgdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnManageCategoryClick(category, "DELETE",view);

                }
            });
            categoryViewHolder.imgedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnManageCategoryClick(category, "EDIT",view);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listCategoryInfo.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        ImageView imgCategory,imgdelete,imgedit;
        TextView txtCategory;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory=itemView.findViewById(R.id.imgCategory);
            txtCategory=itemView.findViewById(R.id.txtCategory);
            imgedit=itemView.findViewById(R.id.imgedit);
            imgdelete=itemView.findViewById(R.id.imgdelete);
        }
    }

}
