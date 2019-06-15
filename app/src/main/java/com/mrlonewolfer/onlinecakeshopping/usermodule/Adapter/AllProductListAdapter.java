package com.mrlonewolfer.onlinecakeshopping.usermodule.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.UserProduct;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllProductListAdapter extends RecyclerView.Adapter<AllProductListAdapter.ProductViewHolder> {

    Context context;
    List<UserProduct.ProductList> listUserProduct;

    AllProductListAdapter.OnProductClickListner listner;
    public interface OnProductClickListner{


        void OnProductClick(View v,UserProduct.ProductList currentproductlist,String action );
    }

    public AllProductListAdapter(Context context, List<UserProduct.ProductList> listUserProduct, AllProductListAdapter.OnProductClickListner listner) {

        this.context = context;
        this.listUserProduct = listUserProduct;
        this.listner=listner;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_row_item,viewGroup,false);
        AllProductListAdapter.ProductViewHolder productViewHolder=new AllProductListAdapter.ProductViewHolder(view);

        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        final UserProduct.ProductList currentproductlist=listUserProduct.get(position);
        String pTtitle=currentproductlist.getProductTitle();
        String pStatus=currentproductlist.getProductStatus();
        if(pStatus.equals("1")){
            holder.txtProductStatus.setText("Available");
            holder.txtProductStatus.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }else{
            holder.txtProductStatus.setText("Not Available");
            holder.txtProductStatus.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }

        String product_image_path= DBConst.IMAGE_URL+ "/images/product/"+currentproductlist.getProductImage();
        holder.txtProductName.setText(pTtitle);
        holder.txtProductPrice.setText(currentproductlist.getDetailPrice().get(0).getPrate()+" Rs /-");
        Picasso.with(context).load(product_image_path).placeholder(R.mipmap.app_icon).into(holder.imgProduct);
        holder.txtProductView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.OnProductClick(v,currentproductlist,"QuickView");
            }
        });
        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.OnProductClick(v,currentproductlist,"imgProduct");
            }
        });
        holder.btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.OnProductClick(v,currentproductlist,"addtocart");
            }
        });

    }

    @Override
    public int getItemCount() {
        return listUserProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView txtProductName,txtProductView,txtProductPrice,txtProductStatus;
        Button btnAddCart;
        ImageView imgProduct;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.imgProduct);
            btnAddCart=itemView.findViewById(R.id.btnAddCart);
            txtProductName=itemView.findViewById(R.id.txtProductName);
            txtProductView=itemView.findViewById(R.id.txtProductView);
            txtProductStatus=itemView.findViewById(R.id.txtProductStatus);
            txtProductPrice=itemView.findViewById(R.id.txtProductPrice);

        }
    }
}
