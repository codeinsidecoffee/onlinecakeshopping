package com.mrlonewolfer.onlinecakeshopping.usermodule.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mrlonewolfer.onlinecakeshopping.Model.OrderInfo;
import com.mrlonewolfer.onlinecakeshopping.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpinnerFinalCartAdapter extends RecyclerView.Adapter<SpinnerFinalCartAdapter.OnFinalCartViewHolder> {
    Context context;
    List<OrderInfo.OrderDetail> listOrderCart;
    OnFinalCartClickListenr listener;
    public SpinnerFinalCartAdapter(Context context, List<OrderInfo.OrderDetail> listOrderCart,OnFinalCartClickListenr listener) {
        this.context=context;
        this.listOrderCart=listOrderCart;
        this.listener=listener;
    }
    public  interface  OnFinalCartClickListenr{
        void  OnFinalCartClick(View v, List<OrderInfo.OrderDetail> listOrderCart, String action);
    }

    @NonNull
    @Override
    public OnFinalCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.total_order_recyclerview,parent,false);
        OnFinalCartViewHolder viewHolder=new OnFinalCartViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OnFinalCartViewHolder holder, int position) {
        final OrderInfo.OrderDetail orderDetails = listOrderCart.get(0);
        int totalproduct= orderDetails.getProductDetails().size();
        if(totalproduct==0){
            holder.txtTotalProduct.setVisibility(View.GONE);
            holder.txtTotalorderAmt.setVisibility(View.GONE);
            holder.txtTotalorderAmt.setVisibility(View.GONE);
            holder.txtTotalProduct.setVisibility(View.GONE);
            holder.rdgPayment.setVisibility(View.GONE);
            holder.btnplaceorder.setVisibility(View.GONE);
        }else{
            holder.txtTotalProduct.setText(orderDetails.getTotalProduct());
            holder.txtTotalorderAmt.setText(orderDetails.getOrderAmount()+ " Rs /-");
            holder.txtTotalorderAmt.setVisibility(View.VISIBLE);
            holder.txtTotalProduct.setVisibility(View.VISIBLE);
            holder.rdgPayment.setVisibility(View.VISIBLE);
        }

        holder.btnplaceorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnFinalCartClick(v,listOrderCart,"PlaceOrder");
            }
        });
        holder.btnshopcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnFinalCartClick(v,listOrderCart,"ShopContinue");
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class OnFinalCartViewHolder extends RecyclerView.ViewHolder {
        TextView txtTotalProduct,txtTotalorderAmt;
        RadioGroup rdgPayment;
        Button btnshopcontinue,btnplaceorder;

        public OnFinalCartViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTotalProduct=itemView.findViewById(R.id.txtTotalProduct);
            txtTotalorderAmt=itemView.findViewById(R.id.txtTotalorderAmt);
            rdgPayment=itemView.findViewById(R.id.rdgPayment);
            btnplaceorder=itemView.findViewById(R.id.btnplaceorder);
            btnshopcontinue=itemView.findViewById(R.id.btnshopcontinue);


        }
    }
}
