package com.mrlonewolfer.onlinecakeshopping.adminmodule.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageOrderAdapter extends RecyclerView.Adapter<ManageOrderAdapter.OnOrderViewHolder> {
    Context context;
    List<OrderInfo.OrderDetail> listOrderDetails;
    public ManageOrderAdapter(Context context, List<OrderInfo.OrderDetail> listOrderDetails) {
        this.context=context;
        this.listOrderDetails=listOrderDetails;
    }

    @NonNull
    @Override
    public OnOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_order_row_tem,parent,false);
        OnOrderViewHolder viewHolder=new OnOrderViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OnOrderViewHolder holder, final int position) {
        OrderInfo.OrderDetail currentOrderDetails = listOrderDetails.get(position);
        holder.txtorderNo.setText(currentOrderDetails.getOrderId());
        holder.txtorderDate.setText(currentOrderDetails.getOrderDate());
        holder.txtotalproduct.setText(currentOrderDetails.getTotalProduct());
        holder.txtorderAmt.setText(currentOrderDetails.getOrderAmount());
        String cname=currentOrderDetails.getFname()+" "+currentOrderDetails.getLname();
        holder.txtCname.setText(cname);
        holder.txtemail.setText(currentOrderDetails.getEmail());
        holder.txtMobile.setText(currentOrderDetails.getMobile());
        String UImagePath=currentOrderDetails.getUserImage();
        String user_img_path= DBConst.IMAGE_URL+ "/images/userimages/"+UImagePath;
        Picasso.with(context).load(user_img_path).placeholder(R.mipmap.app_icon).into(holder.customerProfile);

        final String orderStatus=currentOrderDetails.getOrderStatus();
        holder.txtOrderStatus.setText(orderStatus);
        if(orderStatus.equals(DBConst.PENDING)){
            holder.btnManageOrder.setVisibility(View.VISIBLE);
            holder.txtOrderStatus.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.btnManageOrder.setText("Process Order");

            holder.btnManageOrder.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

        }else{
            if(orderStatus.equals(DBConst.COMPLETED)){
                holder.btnManageOrder.setVisibility(View.GONE);
                holder.txtOrderStatus.setTextColor(context.getResources().getColor(R.color.colorAccent));
            }
            if(orderStatus.equals(DBConst.CANCELED)){
                holder.btnManageOrder.setVisibility(View.GONE);
                holder.txtOrderStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }
            if(orderStatus.equals(DBConst.INPROCESS)){
                holder.btnManageOrder.setVisibility(View.VISIBLE);
                holder.btnManageOrder.setText("Complete Order");
                holder.btnManageOrder.setBackgroundColor(context.getResources().getColor(R.color.colorAdminAccent));
                holder.txtOrderStatus.setTextColor(context.getResources().getColor(R.color.colorAdminAccent));
            }

        }
        holder.btnManageOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  listener.OnUserProductClick(v,userProductDetails,position);
                if(orderStatus.equals(DBConst.INPROCESS)){
                    changeOrderStatus(v,DBConst.COMPLETED,position);

                }
                if(orderStatus.equals(DBConst.PENDING)){
                    changeOrderStatus(v,DBConst.INPROCESS,position);

                }


            }
        });
        List<OrderInfo.ProductDetail> listUserProduct = currentOrderDetails.getProductDetails();
        ManageProductOrdeAdapter adapter=new ManageProductOrdeAdapter(context,listUserProduct);
        holder.viewProductRecycler.setAdapter(adapter);
    }

    private void changeOrderStatus(View v, final String action, final int position) {
        final String currentorderId=listOrderDetails.get(position).getOrderId();
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Change Product Status");
        builder.setMessage("R You Sure? You Want to ");

        builder.setIcon(R.mipmap.app_icon);
        builder.setPositiveButton(action, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AdminMasterRetroFitService retroFitService= RetroFitClient.getAdminData();
                Call<String> call=retroFitService.updatecurrentOrderStatus(DBConst.CASE_19,
                                                                            action,
                                                                            currentorderId);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        listOrderDetails.get(position).setOrderStatus(action);
                        notifyDataSetChanged();
                        Toast.makeText(context, " Tanks for "+action, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AdminMasterRetroFitService retroFitService= RetroFitClient.getAdminData();
                Call<String> call=retroFitService.updatecurrentOrderStatus(DBConst.CASE_20,
                                                                        DBConst.CANCELED,
                                                                        currentorderId);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        listOrderDetails.get(position).setOrderStatus(DBConst.CANCELED);
                        notifyDataSetChanged();
                        Toast.makeText(context, " Tanks for Canceling Order", Toast.LENGTH_SHORT).show();
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
        return listOrderDetails.size();
    }

    public class OnOrderViewHolder extends RecyclerView.ViewHolder{
        Button btnManageOrder;
        TextView txtorderNo,txtorderDate,txtotalproduct,txtorderAmt,txtOrderStatus,txtCname,txtemail,txtMobile;
        RecyclerView viewProductRecycler;
        RecyclerView.LayoutManager layoutManager;
        ImageView customerProfile;
        public OnOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            btnManageOrder=itemView.findViewById(R.id.btnManageOrder);
            txtorderNo=itemView.findViewById(R.id.txtorderNo);
            txtorderDate=itemView.findViewById(R.id.txtorderDate);
            txtotalproduct=itemView.findViewById(R.id.txtotalproduct);
            txtorderAmt=itemView.findViewById(R.id.txtorderAmt);
            txtOrderStatus=itemView.findViewById(R.id.txtOrderStatus);
            customerProfile=itemView.findViewById(R.id.customerProfile);
            txtCname=itemView.findViewById(R.id.txtCname);
            txtemail=itemView.findViewById(R.id.txtemail);
            txtMobile=itemView.findViewById(R.id.txtMobile);

            viewProductRecycler=itemView.findViewById(R.id.viewProductRecycler);
            layoutManager=new LinearLayoutManager(context);
            viewProductRecycler.setLayoutManager(layoutManager);





        }
    }
}
