package com.mrlonewolfer.onlinecakeshopping.adminmodule.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrlonewolfer.onlinecakeshopping.Model.CategoryInfo;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SpinnerCategoryAdapter extends BaseAdapter {
    List<CategoryInfo.Category> listCategoryInfo;
    Context context;
    public SpinnerCategoryAdapter(Context context, List<CategoryInfo.Category> listCategoryInfo) {
        this.listCategoryInfo=listCategoryInfo;
        this.context=context;
    }

    @Override
    public int getCount() {
        return listCategoryInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView spinnercatname;
        ImageView spinnercatimage;
        if(convertView==null){
            LayoutInflater inflater= LayoutInflater.from(parent.getContext());
            convertView=inflater.inflate(R.layout.spinner_category_item,parent,false);
        }
        CategoryInfo.Category currentcategory=listCategoryInfo.get(position);
        spinnercatname=convertView.findViewById(R.id.spinnercatname);
        spinnercatimage=convertView.findViewById(R.id.spinnercatimage);
        String cat_image=currentcategory.getCategoryImage();
        String cat_image_path= DBConst.IMAGE_URL+ "/images/category/thumbs/"+cat_image;

        Picasso.with(context).load(cat_image_path).placeholder(R.mipmap.app_icon).into(spinnercatimage);

        spinnercatname.setText(currentcategory.getCategoryName());

        return convertView;
    }
}
