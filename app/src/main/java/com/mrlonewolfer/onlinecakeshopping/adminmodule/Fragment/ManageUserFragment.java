package com.mrlonewolfer.onlinecakeshopping.adminmodule.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mrlonewolfer.onlinecakeshopping.DataBase.AdminMasterRetroFitService;
import com.mrlonewolfer.onlinecakeshopping.DataBase.RetroFitClient;
import com.mrlonewolfer.onlinecakeshopping.DataBase.UserRetroFitService;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.UserInfo;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.mrlonewolfer.onlinecakeshopping.adminmodule.Adapter.UserRecyclerViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageUserFragment extends Fragment implements UserRecyclerViewAdapter.OnUserManageClickListener{

    RecyclerView manageUserView;
    RecyclerView.LayoutManager layoutManager;
    List<UserInfo.User> allUserInfo;

    Context context;

    public ManageUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_manage_user, container, false);
        context=getActivity();
        manageUserView=view.findViewById(R.id.manageuserview);
        layoutManager=new LinearLayoutManager(getContext());
        manageUserView.setLayoutManager(layoutManager);

        fetchAllUserData();

        return view;
    }

    private void fetchAllUserData() {
        AdminMasterRetroFitService adminMasterRetroFitService= RetroFitClient.getAdminData();
        Call<UserInfo> call=adminMasterRetroFitService.fetchAllUserInfo(DBConst.CASE_2);

        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                UserInfo userInfo=response.body();
                allUserInfo=userInfo.getUsers();
                setUserData();
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Toast.makeText(context,t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUserData() {
        UserRecyclerViewAdapter userRecyclerViewAdapter=new UserRecyclerViewAdapter(context,allUserInfo,this);
        manageUserView.setAdapter(userRecyclerViewAdapter);
    }

    @Override
    public void onUserManageClick(UserInfo.User userinfo, String action,View view) {
        if(action.equals("viewuserorderlist")){

            Toast.makeText(context, "Here,You Will See \n" +
                    "List of  All order" +
                    "\n place by User.", Toast.LENGTH_SHORT).show();
        }
        if(action.equals("blockUser")){

                changecurrentUserStatus(userinfo);

        }
        if(action.equals("viewuserprofile")){
            displaySignleUserInfo(userinfo,view);
        }
    }

    private void displaySignleUserInfo(UserInfo.User userinfo,View view) {
        LayoutInflater layoutInflater=getLayoutInflater();
        View layoutView=layoutInflater.inflate(R.layout.profile_quick_view,
                (ViewGroup) view.findViewById(R.id.viewSingleuserProfile));

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setView(layoutView);

        AlertDialog dialog=builder.create();
        ImageView currentUserProfile;
        TextView txtusername,txtuseremail,txtusermobile,txtuseraddress,
                txtusercountry,txtuserstate,txtusercity,txtuserpincode,txtuserlastvisit;
        currentUserProfile=layoutView.findViewById(R.id.currentUserProfile);
        txtusername=layoutView.findViewById(R.id.txtusername);
        txtuseremail=layoutView.findViewById(R.id.txtuseremail);
        txtusermobile=layoutView.findViewById(R.id.txtusermobile);
        txtuseraddress=layoutView.findViewById(R.id.txtuseraddress);
        txtusercountry=layoutView.findViewById(R.id.txtusercountry);
        txtuserstate=layoutView.findViewById(R.id.txtuserstate);
        txtusercity=layoutView.findViewById(R.id.txtusercity);
        txtuserpincode=layoutView.findViewById(R.id.txtuserpincode);
        txtuserlastvisit=layoutView.findViewById(R.id.txtuserlastvisit);

        String username=userinfo.getFname()+" "+userinfo.getLname();
        txtusername.setText(username);
        txtuseremail.setText(userinfo.getEmail());
        txtusermobile.setText(userinfo.getMobile());
        txtuseraddress.setText(userinfo.getAddress());
        txtusercountry.setText(userinfo.getCountry());
        txtuserstate.setText(userinfo.getState());
        txtusercity.setText(userinfo.getCity());
        txtuserpincode.setText(userinfo.getPincode());
        txtuserlastvisit.setText(userinfo.getLastVisit());

        String user_image_path= DBConst.IMAGE_URL+ "/images/userimages/"+userinfo.getUserImage();
        Picasso.with(context).load(user_image_path).placeholder(R.mipmap.app_icon).into(currentUserProfile);

        dialog.show();


    }

    private void changecurrentUserStatus(UserInfo.User userinfo) {
        String currentEmail=userinfo.getEmail();
        String currentMobile=userinfo.getMobile();
        final String currentStatus=userinfo.getStatus();

        AdminMasterRetroFitService adminMasterRetroFitService= RetroFitClient.getAdminData();
        Call<String> call=adminMasterRetroFitService.changeUserStatus(DBConst.CASE_3,
                                                                        currentEmail,
                                                                        currentMobile,
                                                                        currentStatus);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String msg=response.body();
                if(msg.equals("Status successfully Change")){
                    if(currentStatus.equals("1")){
                        Toast.makeText(context,"active", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(context,"userIsBlock", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context,t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
