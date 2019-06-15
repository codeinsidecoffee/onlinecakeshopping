package com.mrlonewolfer.onlinecakeshopping.adminmodule.Fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mrlonewolfer.onlinecakeshopping.DataBase.AdminMasterRetroFitService;
import com.mrlonewolfer.onlinecakeshopping.DataBase.RetroFitClient;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.DashboardInfo;
import com.mrlonewolfer.onlinecakeshopping.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements View.OnClickListener {


    CardView userCardView,categoryCardView,productCardView,orderCardView;
    Context context;
    Fragment fragment;
    String total_user,total_category,total_product,total_order;
    TextView useTextView,categoryTextView,productTextView,orderTextView;
    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dashboard, container, false);
        context=getActivity();
        fetchDashBoardData();
        userCardView=view.findViewById(R.id.usercardview);
        categoryCardView=view.findViewById(R.id.categorycardview);
        productCardView=view.findViewById(R.id.productcardview);
        orderCardView=view.findViewById(R.id.ordercardview);

        useTextView=view.findViewById(R.id.useTextView);
        productTextView=view.findViewById(R.id.productTextView);
        categoryTextView=view.findViewById(R.id.categoryTextView);
        orderTextView=view.findViewById(R.id.orderTextView);

        userCardView.setOnClickListener(this);
        categoryCardView.setOnClickListener(this);
        productCardView.setOnClickListener(this);
        orderCardView.setOnClickListener(this);


        return view;
    }

    private void fetchDashBoardData() {
        AdminMasterRetroFitService adminMasterRetroFitService= RetroFitClient.getAdminData();
        Call<List<String>> call=adminMasterRetroFitService.selectDashBoard(DBConst.CASE_1);

        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                List<String> dashBoardInfo=response.body();
                 total_user=dashBoardInfo.get(0);
                 total_category=dashBoardInfo.get(1);
                 total_product=dashBoardInfo.get(2);
                 total_order=dashBoardInfo.get(3);

                 useTextView.setText(total_user);
                 productTextView.setText(total_product);
                 categoryTextView.setText(total_category);
                 orderTextView.setText(total_order);



            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.usercardview){
            fragment=new ManageUserFragment();

        }
        if(v.getId()==R.id.categorycardview){
            fragment=new ManageCategoryFragment();
        }
        if(v.getId()==R.id.productcardview){
            fragment=new ManageProductFragment();
        }
        if(v.getId()==R.id.ordercardview){
            fragment=new ManageOrderFragment();
        }
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.adminMainContainer,fragment).commit();
    }
}
