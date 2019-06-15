package com.mrlonewolfer.onlinecakeshopping.usermodule.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mrlonewolfer.onlinecakeshopping.DataBase.AdminMasterRetroFitService;
import com.mrlonewolfer.onlinecakeshopping.DataBase.RetroFitClient;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.OrderInfo;
import com.mrlonewolfer.onlinecakeshopping.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewUserOrderAdapter extends RecyclerView.Adapter<ViewUserOrderAdapter.OnUserOrderViewHolder>  {

    Context context;
    List<OrderInfo.OrderDetail> listUserOrder;
    OnUserOrderClickListener listener;
    public ViewUserOrderAdapter(Context context, List<OrderInfo.OrderDetail> listUserOrder) {
    this.context=context;
    this.listUserOrder=listUserOrder;

    }
public  interface  OnUserOrderClickListener{
        void OnUserOrderClick();
}
    @NonNull
    @Override
    public OnUserOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_order_row_item,parent,false);
        OnUserOrderViewHolder viewHolder=new OnUserOrderViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OnUserOrderViewHolder holder, final int position) {
        OrderInfo.OrderDetail userOrderDetails = listUserOrder.get(position);
        holder.txtorderNo.setText(userOrderDetails.getOrderId());
        holder.txtorderDate.setText(userOrderDetails.getOrderDate());
        holder.txtotalproduct.setText(userOrderDetails.getTotalProduct());
        final String orderStatus=userOrderDetails.getOrderStatus();
        holder.txtOrderStatus.setText(orderStatus);
        holder.txtorderAmt.setText(userOrderDetails.getOrderAmount());
        if(orderStatus.equals("Pending")){
            holder.btnCancleOrder.setVisibility(View.VISIBLE);
            holder.txtOrderStatus.setTextColor(context.getResources().getColor(R.color.colorPrimary));

        }else{
            if(orderStatus.equals("Completed")){
                holder.txtOrderStatus.setTextColor(context.getResources().getColor(R.color.colorAccent));
            }
            if(orderStatus.equals("In Process")){
                holder.txtOrderStatus.setTextColor(context.getResources().getColor(R.color.colorAdminAccent));
            }
            holder.btnCancleOrder.setVisibility(View.GONE);
        }
        holder.btnCancleOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listUserOrder.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.btnCancleOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  listener.OnUserProductClick(v,userProductDetails,position);

                    changeOrderStatus(position);

            }
        });
        List<OrderInfo.ProductDetail> listUserProduct = userOrderDetails.getProductDetails();
        int productsize=listUserProduct.size();
        Log.e("VIew Useer Adapter", "\n productsize "+productsize );
//        for(int i=0;i<productsize;i++){
//            Log.e("VIew Useer Adapter", "\n ProductTitle:  "+i+" "+listUserProduct.get(i).getProductTitle() );
//        }

            ViewUserProductAdapter userProductAdapter=new ViewUserProductAdapter(context,listUserProduct);
            holder.viewProductRecycler.setAdapter(userProductAdapter);






    }

    private void changeOrderStatus(final int position) {
        final String currentorderId=listUserOrder.get(position).getOrderId();
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Change Product Status");
        builder.setMessage("R You Sure? You Want to ");

        builder.setIcon(R.mipmap.app_icon);
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
                        listUserOrder.get(position).setOrderStatus(DBConst.CANCELED);
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
        return listUserOrder.size();
    }

    public class OnUserOrderViewHolder extends RecyclerView.ViewHolder{
        TextView txtorderNo,txtorderDate,txtotalproduct,txtorderAmt,txtOrderStatus;
        RecyclerView viewProductRecycler;
        RecyclerView.LayoutManager layoutManager;
        Button btnCancleOrder;
        public OnUserOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            txtorderNo=itemView.findViewById(R.id.txtorderNo);
            txtorderDate=itemView.findViewById(R.id.txtorderDate);
            txtotalproduct=itemView.findViewById(R.id.txtotalproduct);
            txtorderAmt=itemView.findViewById(R.id.txtorderAmt);
            txtOrderStatus=itemView.findViewById(R.id.txtOrderStatus);
            viewProductRecycler=itemView.findViewById(R.id.viewProductRecycler);
            layoutManager=new LinearLayoutManager(context);
            viewProductRecycler.setLayoutManager(layoutManager);
            btnCancleOrder=itemView.findViewById(R.id.btnCancleOrder);
        }
    }
}
