package com.mrlonewolfer.onlinecakeshopping.usermodule.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.OrderInfo;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import static com.mrlonewolfer.onlinecakeshopping.usermodule.Fragment.HomeFragment.listOrderCart;


public class SpinnerCartAdapter extends RecyclerView.Adapter<SpinnerCartAdapter.OnCartViewHolder> {

    Context context;
    List<OrderInfo.ProductDetail> listProductCart;
    OnCartClickListner listner;
    double total_order_amt=0;

    public SpinnerCartAdapter(Context context, List<OrderInfo.ProductDetail> listProductCart, OnCartClickListner listner) {
        this.context=context;
        this.listProductCart=listProductCart;
        this.listner=listner;

    }
    public interface OnCartClickListner{
        void onCartClick(View v, OrderInfo.ProductDetail productDetail, String action);
    }


    @NonNull
    @Override
    public OnCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row_item,parent,false);
        OnCartViewHolder viewHolder=new OnCartViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OnCartViewHolder holder, int position) {

        final OrderInfo.ProductDetail productDetail = listProductCart.get(position);
        String img_path=productDetail.getProductImage();
        String product_image_path= DBConst.IMAGE_URL+ "/images/product/"+img_path;
        Picasso.with(context).load(product_image_path).placeholder(R.mipmap.app_icon).into(holder.imgProduct);
        holder.txtProductName.setText(productDetail.getProductTitle());
        holder.txtCategory.setText(productDetail.getCategoryName());
        holder.txtProductType.setText(productDetail.getProductTypeName());
        holder.txtProductQtyType.setText(productDetail.getProductQtyType());
        holder.txtProductPrice.setText(productDetail.getPurchaseAmount()+" RS /-");
        holder.txtTotalUnit.setText(productDetail.getTotalUnit());
        holder.txtUnitRate.setText(productDetail.getUnitRate());
        holder.txtDeliveryDate.setText(productDetail.getReceiveDate());
        holder.txtdeliveryTime.setText(productDetail.getReceiveTime());
        //Log.e("SpinnerCartAdapter", "\n total_order_amt:Before "+total_order_amt+"\n purchse amount"+Double.parseDouble(productDetail.getPurchaseAmount()));

        total_order_amt=total_order_amt+Double.parseDouble(productDetail.getPurchaseAmount());
        listOrderCart.get(0).setOrderAmount(total_order_amt+"");
        Log.e("SpinnerCartAdapter", "\n \n total_order_amt:After "+total_order_amt+"\n purchse amount"+Double.parseDouble(productDetail.getPurchaseAmount()));
        holder.imgeditcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            listner.onCartClick(v,productDetail,"EditCart");
            }
        });
        holder.imgdeletecart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onCartClick(v,productDetail,"DeleteCart");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProductCart.size();
    }

    public class OnCartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct,imgeditcart,imgdeletecart;
        TextView txtProductName,txtCategory,txtProductType,txtProductQtyType,txtProductPrice,txtTotalUnit,txtUnitRate,txtDeliveryDate,txtdeliveryTime;
        public OnCartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.imgProduct);
            txtProductName=itemView.findViewById(R.id.txtProductName);
            txtCategory=itemView.findViewById(R.id.txtCategory);
            txtProductType=itemView.findViewById(R.id.txtProductType);
            txtProductQtyType=itemView.findViewById(R.id.txtProductQtyType);
            txtProductPrice=itemView.findViewById(R.id.txtProductPrice);
            txtTotalUnit=itemView.findViewById(R.id.txtTotalUnit);
            txtUnitRate=itemView.findViewById(R.id.txtUnitRate);
            txtDeliveryDate=itemView.findViewById(R.id.txtDeliveryDate);
            txtdeliveryTime=itemView.findViewById(R.id.txtdeliveryTime);
            imgeditcart=itemView.findViewById(R.id.imgeditcart);
            imgdeletecart=itemView.findViewById(R.id.imgdeletecart);



        }
    }
}
