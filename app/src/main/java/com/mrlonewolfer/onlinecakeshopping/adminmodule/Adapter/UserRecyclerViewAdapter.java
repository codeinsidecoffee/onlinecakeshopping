package com.mrlonewolfer.onlinecakeshopping.adminmodule.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.UserInfo;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder> {
    List<UserInfo.User> allUserInfo;
    Context context;
    OnUserManageClickListener listener;
    public UserRecyclerViewAdapter(Context context, List<UserInfo.User> allUserInfo,OnUserManageClickListener listener) {
       this.context=context;
       this.allUserInfo=allUserInfo;
       this.listener=listener;

    }



    public  interface OnUserManageClickListener{
        void onUserManageClick(UserInfo.User userinfo,String action,View view);
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manage_user_row_item,viewGroup,false);
        UserViewHolder userViewHolder=new UserViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int position) {
            final UserInfo.User currentUserInfo=allUserInfo.get(position);
            String name=currentUserInfo.getFname()+" "+currentUserInfo.getLname();
            String lastvisit=currentUserInfo.getLastVisit();
            String user_image=currentUserInfo.getUserImage();
           final String user_status= currentUserInfo.getStatus();
        String user_image_path= DBConst.IMAGE_URL+ "/images/userimages/"+user_image;
        Log.e("userimagepath", "onBindViewHolder: "+user_image_path);
        userViewHolder.txtusername.setText(name);
        userViewHolder.txtlastvisit.setText(lastvisit);
        Picasso.with(context).load(user_image_path).placeholder(R.mipmap.app_icon).into(userViewHolder.imguserprofile);
        if(user_status.equals("1")){
            userViewHolder.switchuser.setChecked(true);
        }else{
            userViewHolder.switchuser.setChecked(false);
        }

        userViewHolder.txtuserview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUserManageClick(currentUserInfo,"viewuserorderlist",v);
            }
        });
        userViewHolder.switchuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status=currentUserInfo.getStatus();
                if(status.equals("1")){
                    currentUserInfo.setStatus("0");
                    listener.onUserManageClick(currentUserInfo,"blockUser",v);


                }else{
                    currentUserInfo.setStatus("1");
                    listener.onUserManageClick(currentUserInfo,"blockUser",v);

                }


            }
        });
        userViewHolder.imguserprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUserManageClick(currentUserInfo,"viewuserprofile",v);
            }
        });

    }

    @Override
    public int getItemCount() {
        return allUserInfo.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        ImageView imguserprofile;
        TextView txtusername,txtuserview,txtlastvisit;
        Switch switchuser;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imguserprofile=itemView.findViewById(R.id.imguserprofile);
            txtusername=itemView.findViewById(R.id.txtusername);
            txtuserview=itemView.findViewById(R.id.txtuserview);
            txtlastvisit=itemView.findViewById(R.id.txtlastvisit);
            switchuser=itemView.findViewById(R.id.switchuser);
        }
    }
}
