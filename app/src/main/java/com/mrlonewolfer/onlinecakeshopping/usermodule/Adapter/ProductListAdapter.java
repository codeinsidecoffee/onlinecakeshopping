package com.mrlonewolfer.onlinecakeshopping.usermodule.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.ProductInfo;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    Context context;
    List<ProductInfo.Product> listProductInfo;

    OnProductClickListner listner;
    public interface OnProductClickListner{
        void OnProductClick(ProductInfo.Product product, String action, View v);
    }

    public ProductListAdapter(Context context, List<ProductInfo.Product> listProductInfo,OnProductClickListner listner) {

        this.context=context;
        this.listProductInfo=listProductInfo;
        this.listner=listner;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_row_item,viewGroup,false);
        ProductViewHolder productViewHolder=new ProductViewHolder(view);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int position) {
        final ProductInfo.Product product=listProductInfo.get(position);
        String product_name=product.getProductTitle();
        String product_price=" update soon Rs/-";
        String prduct_image=product.getProductImage();

        String product_image_path= DBConst.IMAGE_URL+ "/images/thumbs/"+prduct_image;
        Log.e("imagepath", "onBindViewHolder: "+product_image_path);
        productViewHolder.txtProductName.setText(product_name);
        productViewHolder.txtProductPrice.setText(product_price);
        Picasso.with(context).load(product_image_path).placeholder(R.mipmap.app_icon).into(productViewHolder.imgProduct);
        productViewHolder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.OnProductClick(product,"DetailView",v);
            }
        });
        productViewHolder.txtProductView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.OnProductClick(product,"QuickView",v);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listProductInfo.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView txtProductName,txtProductView,txtProductPrice;
        Button btnAddCart;
        ImageView imgProduct;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.imgProduct);
            btnAddCart=itemView.findViewById(R.id.btnAddCart);
            txtProductName=itemView.findViewById(R.id.txtProductName);
            txtProductView=itemView.findViewById(R.id.txtProductView);
            txtProductPrice=itemView.findViewById(R.id.txtProductPrice);

        }
    }
}
