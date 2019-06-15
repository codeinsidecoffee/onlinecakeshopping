package com.mrlonewolfer.onlinecakeshopping.DataBase;

import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitClient {

    public static UserRetroFitService getClient(){

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(DBConst.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserRetroFitService retroFitService= retrofit.create(UserRetroFitService.class);


        return retroFitService;
    }

    public static CategoryRetroFitService getCategory(){

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(DBConst.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CategoryRetroFitService retroFitService=retrofit.create(CategoryRetroFitService.class);

        return retroFitService;
    }
    public static ProductRetroFitService getProduct(){

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(DBConst.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductRetroFitService retroFitService=retrofit.create(ProductRetroFitService.class);

        return retroFitService;
    }
    public static AdminMasterRetroFitService getAdminData(){

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(DBConst.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AdminMasterRetroFitService retroFitService=retrofit.create(AdminMasterRetroFitService.class);

        return retroFitService;
    }

    public static OrderRetroFitService getOrderData(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(DBConst.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrderRetroFitService retroFitService=retrofit.create(OrderRetroFitService.class);
        return retroFitService;
    }
}
