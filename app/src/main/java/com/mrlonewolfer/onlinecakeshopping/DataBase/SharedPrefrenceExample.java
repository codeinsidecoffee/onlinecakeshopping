package com.mrlonewolfer.onlinecakeshopping.DataBase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.PrefBean;
import com.mrlonewolfer.onlinecakeshopping.Model.UserConst;

public class SharedPrefrenceExample {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String PrefName;
    Context context;
    PrefBean prefBean;

    public SharedPrefrenceExample(String prefName, Context context) {
        PrefName = prefName;
        this.context = context;
        sharedPreferences=context.getSharedPreferences(prefName,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

    }

    public PrefBean getSharedPreferences() {
        prefBean=new PrefBean();
        prefBean.setEmail(sharedPreferences.getString(UserConst.EMAIL,null));
        prefBean.setPass(sharedPreferences.getString(UserConst.Pass,null));
        prefBean.setStatus(sharedPreferences.getString(DBConst.PREF_STATUS,null));
        prefBean.setUsertype(sharedPreferences.getString(UserConst.USER_TYPE,null));
        prefBean.setUserid(sharedPreferences.getString(UserConst.ID,null));

        return prefBean;
    }

    public void setSharedPreferences(PrefBean prefBean){
        editor.putString(UserConst.EMAIL,prefBean.getEmail());
        editor.putString(UserConst.Pass,prefBean.getPass());
        editor.putString(DBConst.PREF_STATUS,prefBean.getStatus());
        editor.putString(UserConst.USER_TYPE,prefBean.getUsertype());
        editor.putString(UserConst.ID,prefBean.getUserid());
        editor.commit();
    }

    public void logout(){
        sharedPreferences=context.getSharedPreferences(PrefName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(DBConst.PREF_STATUS,DBConst.Status_False);
        editor.commit();
    }
}
