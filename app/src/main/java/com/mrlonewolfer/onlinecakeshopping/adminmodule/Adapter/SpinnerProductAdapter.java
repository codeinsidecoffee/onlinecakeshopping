package com.mrlonewolfer.onlinecakeshopping.adminmodule.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.ProductInfo;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SpinnerProductAdapter extends BaseAdapter {

    Context context;
    List<ProductInfo.Product> listProductInfo;

    public SpinnerProductAdapter(Context context, List<ProductInfo.Product> listProductInfo) {
        this.context = context;
        this.listProductInfo = listProductInfo;
    }

    @Override
    public int getCount() {
        return listProductInfo.size();
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
        ProductInfo.Product currentProduct=listProductInfo.get(position);
        spinnercatname=convertView.findViewById(R.id.spinnercatname);
        spinnercatimage=convertView.findViewById(R.id.spinnercatimage);
        String cat_image=currentProduct.getProductImage();
        String cat_image_path= DBConst.IMAGE_URL+ "/images/product/"+cat_image;

        Picasso.with(context).load(cat_image_path).placeholder(R.mipmap.app_icon).into(spinnercatimage);

        spinnercatname.setText(currentProduct.getProductTitle());

        return convertView;
    }
}
