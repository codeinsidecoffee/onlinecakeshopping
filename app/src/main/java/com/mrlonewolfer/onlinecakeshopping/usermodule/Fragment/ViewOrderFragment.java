package com.mrlonewolfer.onlinecakeshopping.usermodule.Fragment;


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

import com.mrlonewolfer.onlinecakeshopping.DataBase.OrderRetroFitService;
import com.mrlonewolfer.onlinecakeshopping.DataBase.RetroFitClient;
import com.mrlonewolfer.onlinecakeshopping.DataBase.SharedPrefrenceExample;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.OrderInfo;
import com.mrlonewolfer.onlinecakeshopping.Model.PrefBean;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.mrlonewolfer.onlinecakeshopping.usermodule.Adapter.ViewUserOrderAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewOrderFragment extends Fragment  {


    private List<OrderInfo.OrderDetail> listUserOrder;
    Context context;
    RecyclerView userOrderRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    PrefBean prefBean;
    SharedPrefrenceExample sharedPrefrenceExample;
    public ViewOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_order, container, false);
        context=getActivity();
        userOrderRecyclerView=view.findViewById(R.id.userOrderRecyclerView);
        layoutManager=new LinearLayoutManager(context);
        userOrderRecyclerView.setLayoutManager(layoutManager);
        sharedPrefrenceExample = new SharedPrefrenceExample(DBConst.FILE_NAME,context);
        prefBean=sharedPrefrenceExample.getSharedPreferences();
        fetchUserOrderData();

        return view;
    }

    private void fetchUserOrderData() {
        OrderRetroFitService retroFitService= RetroFitClient.getOrderData();
        Call<OrderInfo> call=retroFitService.fetchUserOrder(DBConst.CASE_2,
                                                        prefBean.getUserid());

        call.enqueue(new Callback<OrderInfo>() {
            @Override
            public void onResponse(Call<OrderInfo> call, Response<OrderInfo> response) {
                OrderInfo orderDetails = response.body();
                listUserOrder=orderDetails.getOrderDetails();
                setUpUserOrderData();
            }

            @Override
            public void onFailure(Call<OrderInfo> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setUpUserOrderData() {
        ViewUserOrderAdapter userOrderAdapter=new ViewUserOrderAdapter(context,listUserOrder);
        userOrderRecyclerView.setAdapter(userOrderAdapter);
    }

}
