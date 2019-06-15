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
import com.mrlonewolfer.onlinecakeshopping.Model.QtyTypeInfo;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SpinnerQtyTypeAdapter extends BaseAdapter {
    Context context;
    List<QtyTypeInfo.Qtytype> listqtyTypeInfo;
    public SpinnerQtyTypeAdapter(Context context, List<QtyTypeInfo.Qtytype> listqtyTypeInfo) {
        this.context=context;
        this.listqtyTypeInfo=listqtyTypeInfo;
    }

    @Override
    public int getCount() {
        return listqtyTypeInfo.size();
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
            LayoutInflater inflater=LayoutInflater.from(parent.getContext());
            convertView=inflater.inflate(R.layout.spinner_category_item,parent,false);
        }
        QtyTypeInfo.Qtytype currentQtyType=listqtyTypeInfo.get(position);
        spinnercatname=convertView.findViewById(R.id.spinnercatname);
        spinnercatimage=convertView.findViewById(R.id.spinnercatimage);
        spinnercatimage.setVisibility(View.GONE);
        spinnercatname.setText(currentQtyType.getProductQtyType());

        return convertView;
    }
}
