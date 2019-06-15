package com.mrlonewolfer.onlinecakeshopping.adminmodule.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrlonewolfer.onlinecakeshopping.Model.MasterProduct;
import com.mrlonewolfer.onlinecakeshopping.Model.QtyTypeInfo;
import com.mrlonewolfer.onlinecakeshopping.R;

import java.util.List;

public class SpinnerWeightAdapter extends BaseAdapter {
    Context context;
    List<MasterProduct.Cakerate> listCakeRate;

    public SpinnerWeightAdapter(Context context, List<MasterProduct.Cakerate> listCakeRate) {
        this.context = context;
        this.listCakeRate = listCakeRate;
    }

    @Override
    public int getCount() {
        return listCakeRate.size();
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
        TextView txtcatname;
        ImageView catimage;
        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(parent.getContext());
            convertView=inflater.inflate(R.layout.spinner_category_item,parent,false);
        }
        MasterProduct.Cakerate currentCakeRate=listCakeRate.get(position);
        txtcatname=convertView.findViewById(R.id.spinnercatname);
        catimage=convertView.findViewById(R.id.spinnercatimage);
        catimage.setVisibility(View.GONE);
        txtcatname.setText(currentCakeRate.getPweight());

        return convertView;
    }
}
