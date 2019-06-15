package com.mrlonewolfer.onlinecakeshopping.usermodule.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mrlonewolfer.onlinecakeshopping.DataBase.AdminMasterRetroFitService;
import com.mrlonewolfer.onlinecakeshopping.DataBase.RetroFitClient;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.OrderInfo;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewUserProductAdapter extends RecyclerView.Adapter<ViewUserProductAdapter.OnUserProductViewHolder> {
    Context context;
    List<OrderInfo.ProductDetail> listUserProduct;
    OnUserProductClickListener listener;
    public ViewUserProductAdapter(Context context, List<OrderInfo.ProductDetail> listUserProduct) {
        this.context=context;
        this.listUserProduct=listUserProduct;

    }
    public  interface OnUserProductClickListener{
        void OnUserProductClick(View v, OrderInfo.ProductDetail userProductDetails, int position);
    }

    @NonNull
    @Override
    public OnUserProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_order_product_row_item,parent,false);
        OnUserProductViewHolder viewHolder=new OnUserProductViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OnUserProductViewHolder holder, final int position) {
        final OrderInfo.ProductDetail userProductDetails = listUserProduct.get(position);
        Log.e("VIew Product Adapter", "\n productdetails "+userProductDetails.getProductTitle() );
        String pImagePath=userProductDetails.getProductImage();
        String product_image_path= DBConst.IMAGE_URL+ "/images/product/"+pImagePath;
        Picasso.with(context).load(product_image_path).placeholder(R.mipmap.app_icon).into(holder.imgProduct);
        holder.txtProductName.setText(userProductDetails.getProductTitle());
        holder.txtCategory.setText(userProductDetails.getCategoryName());
        holder.txtProductType.setText(userProductDetails.getProductTypeName());
        holder.txtProductPrice.setText(userProductDetails.getPurchaseAmount());
        holder.txtTotalUnit.setText(userProductDetails.getTotalUnit());
        holder.txtProductQtyType.setText(userProductDetails.getProductQtyType());
        holder.txtUnitRate.setText(userProductDetails.getUnitRate());
        holder.txtDeliveryDate.setText(userProductDetails.getReceiveDate());
        holder.txtdeliveryTime.setText(userProductDetails.getReceiveTime());
        final String productStatus=userProductDetails.getProductStatus();
        holder.txtProductStatus.setText(productStatus);
        if(productStatus.equals("Pending")){
            holder.btnCancleproduct.setVisibility(View.VISIBLE);
            holder.txtProductStatus.setTextColor(context.getResources().getColor(R.color.colorPrimary));

        }else{
            if(productStatus.equals("Completed")){
                holder.txtProductStatus.setTextColor(context.getResources().getColor(R.color.colorAccent));
            }
            if(productStatus.equals("In Process")){
                holder.txtProductStatus.setTextColor(context.getResources().getColor(R.color.colorAdminAccent));
            }
            holder.btnCancleproduct.setVisibility(View.GONE);
        }

        holder.btnCancleproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  listener.OnUserProductClick(v,userProductDetails,position);
                    String order_detailid=userProductDetails.getOrderDetailsId();
                    changeProductStatus(position,order_detailid);

            }
        });



    }

    private void changeProductStatus(final int position, final String order_detailid) {


        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Change Product Status");
        builder.setMessage("R You Sure? You Want to ");

        Log.e("changeProductStatus", "changeProductStatus: "+order_detailid );
        builder.setIcon(R.mipmap.app_icon);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AdminMasterRetroFitService retroFitService= RetroFitClient.getAdminData();
                Call<String> call=retroFitService.cancelUserProductStatus(DBConst.CASE_21,
                                                                order_detailid);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String msg=response.body();
                        listUserProduct.get(position).setProductStatus(DBConst.CANCELED);
                        notifyDataSetChanged();
                        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        builder.setNeutralButton("Go Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dailog=builder.create();
        dailog.show();
    }

    @Override
    public int getItemCount() {
        return listUserProduct.size();
    }

    public class OnUserProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtProductName,txtCategory,txtProductType,txtProductPrice,txtTotalUnit,txtProductQtyType,txtUnitRate,txtDeliveryDate,txtdeliveryTime,txtProductStatus;
        Button btnCancleproduct;
        public OnUserProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.imgProduct);
            txtProductName=itemView.findViewById(R.id.txtProductName);
            txtCategory=itemView.findViewById(R.id.txtCategory);
            txtProductType=itemView.findViewById(R.id.txtProductType);
            txtProductPrice=itemView.findViewById(R.id.txtProductPrice);
            txtTotalUnit=itemView.findViewById(R.id.txtTotalUnit);
            txtProductQtyType=itemView.findViewById(R.id.txtProductQtyType);
            txtUnitRate=itemView.findViewById(R.id.txtUnitRate);
            txtDeliveryDate=itemView.findViewById(R.id.txtDeliveryDate);
            txtdeliveryTime=itemView.findViewById(R.id.txtdeliveryTime);
            txtProductStatus=itemView.findViewById(R.id.txtProductStatus);
            btnCancleproduct=itemView.findViewById(R.id.btnCancleproduct);

        }
    }
}
