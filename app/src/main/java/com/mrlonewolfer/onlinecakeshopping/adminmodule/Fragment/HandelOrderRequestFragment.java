package com.mrlonewolfer.onlinecakeshopping.adminmodule.Fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mrlonewolfer.onlinecakeshopping.DataBase.AdminMasterRetroFitService;
import com.mrlonewolfer.onlinecakeshopping.DataBase.RetroFitClient;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.OrderInfo;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.mrlonewolfer.onlinecakeshopping.adminmodule.Adapter.ManageOrderAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HandelOrderRequestFragment extends Fragment {


    private String action;
    Context context;
    RecyclerView handelorderrecyclerview;
    RecyclerView.LayoutManager layoutManager;
    List<OrderInfo.OrderDetail> listOrderDetails;
    public HandelOrderRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_handel_order_request, container, false);
        context=getActivity();
        Bundle bundle=getArguments();
        if(bundle!=null){
            action=bundle.getString("myaction");
            Toast.makeText(context, action, Toast.LENGTH_SHORT).show();
        }
        handelorderrecyclerview=view.findViewById(R.id.handelorderrecyclerview);
        layoutManager=new LinearLayoutManager(context);
        handelorderrecyclerview.setLayoutManager(layoutManager);

        setUPOrderDetail();


        return  view;
    }

    private void setUPOrderDetail() {
        String order_status = null;
        if(action.equals("ViewAllOrder")){
            order_status="";
        }
        if(action.equals("PendingOrder")){
            order_status=DBConst.PENDING;
        }
        if(action.equals("InProcessOrder")){
            order_status=DBConst.INPROCESS;
        }
        if(action.equals("CancelOrder")){
            order_status=DBConst.CANCELED;
        }
        if(action.equals("CompletedOrder")){
            order_status=DBConst.COMPLETED;
        }
        AdminMasterRetroFitService retroFitService= RetroFitClient.getAdminData();
        Call<OrderInfo> call=retroFitService.fetchAllOrderDetails(DBConst.CASE_17,
                                                                    order_status);
        call.enqueue(new Callback<OrderInfo>() {
            @Override
            public void onResponse(Call<OrderInfo> call, Response<OrderInfo> response) {
                OrderInfo orderInfo = response.body();
                listOrderDetails = orderInfo.getOrderDetails();
                PreparedDataAdapter();
            }

            @Override
            public void onFailure(Call<OrderInfo> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void PreparedDataAdapter() {
        ManageOrderAdapter adapter=new ManageOrderAdapter(context,listOrderDetails);
        handelorderrecyclerview.setAdapter(adapter);
    }

}
