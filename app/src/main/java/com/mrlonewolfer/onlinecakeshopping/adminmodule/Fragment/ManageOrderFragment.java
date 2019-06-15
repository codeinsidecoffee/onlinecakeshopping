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
import android.widget.TextView;
import android.widget.Toast;

import com.mrlonewolfer.onlinecakeshopping.DataBase.AdminMasterRetroFitService;
import com.mrlonewolfer.onlinecakeshopping.DataBase.RetroFitClient;
import com.mrlonewolfer.onlinecakeshopping.DataBase.SharedPrefrenceExample;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.PrefBean;
import com.mrlonewolfer.onlinecakeshopping.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageOrderFragment extends Fragment implements View.OnClickListener {

    Context context;
    PrefBean prefBean;
    SharedPrefrenceExample sharedPrefrenceExample;
    CardView viewallordercard,viewPendingOrder,inprocessordercard,cancelordercard,completedordercard;
    TextView txtviewallorder,txtviewpendingorder,txtviewinprocessorder,txtviewcancelorder,txtviewcompletedorder;
    String action = null;
    public ManageOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_manage_order, container, false);
        context=getActivity();
        sharedPrefrenceExample = new SharedPrefrenceExample(DBConst.FILE_NAME,context);
        prefBean=sharedPrefrenceExample.getSharedPreferences();

        viewallordercard=view.findViewById(R.id.viewallordercard);
        viewPendingOrder=view.findViewById(R.id.viewPendingOrder);
        inprocessordercard=view.findViewById(R.id.inprocessordercard);
        cancelordercard=view.findViewById(R.id.cancelordercard);
        completedordercard=view.findViewById(R.id.completedordercard);

        txtviewallorder=view.findViewById(R.id.txtviewallorder);
        txtviewpendingorder=view.findViewById(R.id.txtviewpendingorder);
        txtviewinprocessorder=view.findViewById(R.id.txtviewinprocessorder);
        txtviewcancelorder=view.findViewById(R.id.txtviewcancelorder);
        txtviewcompletedorder=view.findViewById(R.id.txtviewcompletedorder);

        viewallordercard.setOnClickListener(this);
        viewPendingOrder.setOnClickListener(this);
        inprocessordercard.setOnClickListener(this);
        cancelordercard.setOnClickListener(this);
        completedordercard.setOnClickListener(this);
        fetchtOrderDashBoard();

        return  view;
    }

    private void fetchtOrderDashBoard() {
        AdminMasterRetroFitService retroFitService= RetroFitClient.getAdminData();
        Call<List<String>> call=retroFitService.selectOrderDashBoard(DBConst.CASE_16);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                List<String> orderdashboard = response.body();
                txtviewallorder.setText(orderdashboard.get(0));
                txtviewpendingorder.setText(orderdashboard.get(1));
                txtviewinprocessorder.setText(orderdashboard.get(2));
                txtviewcancelorder.setText(orderdashboard.get(3));
                txtviewcompletedorder.setText(orderdashboard.get(4));
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {

            if(v.getId()==R.id.viewallordercard){
                action="ViewAllOrder";
            }
             if(v.getId()==R.id.viewPendingOrder){
                 action="PendingOrder";
            }
            if(v.getId()==R.id.inprocessordercard){
                action="InProcessOrder";
            }
            if(v.getId()==R.id.cancelordercard){
                action="CancelOrder";

            }if(v.getId()==R.id.completedordercard){
                action="CompletedOrder";
            }
            Bundle bundle=new Bundle();
            bundle.putString("myaction",action);
            Fragment fragment=new HandelOrderRequestFragment();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.adminMainContainer,fragment).commit();
    }
}
