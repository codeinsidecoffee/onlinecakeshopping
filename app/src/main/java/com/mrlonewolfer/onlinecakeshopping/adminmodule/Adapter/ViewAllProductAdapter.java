package com.mrlonewolfer.onlinecakeshopping.adminmodule.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.ProductInfo;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewAllProductAdapter extends RecyclerView.Adapter<ViewAllProductAdapter.ProductViewHolder> {

    Context context;
    List<ProductInfo.Product> listProductInfo;
    OnProductClickListner listner;

    public ViewAllProductAdapter(Context context, List<ProductInfo.Product> listProductInfo, OnProductClickListner listner) {
        this.context = context;
        this.listProductInfo = listProductInfo;
        this.listner = listner;
    }

    public interface OnProductClickListner{
        void OnProductClick(View v, ProductInfo.Product product, String action);
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_product_admin,parent,false);
        ProductViewHolder productViewHolder=new ProductViewHolder(view);

        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {
        final ProductInfo.Product product=listProductInfo.get(position);
        String product_image=product.getProductImage();
        String product_title=product.getProductTitle();
        String product_status=product.getProductStatus();
        String product_image_path= DBConst.IMAGE_URL+ "/images/product/"+product_image;
        Picasso.with(context).load(product_image_path).placeholder(R.mipmap.app_icon).into(holder.imgProduct);
        holder.txtProductName.setText(product_title);
        if(product_status.equals("1")){
            holder.switchproduct.setChecked(true);
            holder.txtProStatus.setText("Available");
            holder.txtProStatus.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }else{
            holder.switchproduct.setChecked(false);
            holder.txtProStatus.setText("Not Available");
            holder.txtProStatus.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.OnProductClick(v,product,"Productimage");
            }
        });
        holder.txtManageDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.OnProductClick(v,product,"ManageDetails");
            }
        });
        holder.imgdeletePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.OnProductClick(v,product,"DeleteProduct");
            }
        });
        holder.switchproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product_status=product.getProductStatus();

                if(product_status.equals("1")){
                    listner.OnProductClick(v,product,"ProductStatus");
                    holder.txtProStatus.setText("Not Available");
                    holder.txtProStatus.setTextColor(context.getResources().getColor(R.color.colorPrimary));

                }else{
                    listner.OnProductClick(v,product,"ProductStatus");
                    holder.txtProStatus.setText("Available");
                    holder.txtProStatus.setTextColor(context.getResources().getColor(R.color.colorAccent));
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return listProductInfo.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct,imgdeletePro;
        TextView txtProductName,txtManageDetails,txtProStatus;
        Switch switchproduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.imgProduct);
            txtProductName=itemView.findViewById(R.id.txtProductName);
            txtManageDetails=itemView.findViewById(R.id.txtManageDetails);
            imgdeletePro=itemView.findViewById(R.id.imgdeletePro);
            txtProStatus=itemView.findViewById(R.id.txtProStatus);
            switchproduct=itemView.findViewById(R.id.switchproduct);
        }
    }
}
