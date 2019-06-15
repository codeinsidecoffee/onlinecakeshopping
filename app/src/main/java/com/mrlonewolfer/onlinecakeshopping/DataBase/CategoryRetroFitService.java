package com.mrlonewolfer.onlinecakeshopping.DataBase;

import com.mrlonewolfer.onlinecakeshopping.Model.CategoryConst;
import com.mrlonewolfer.onlinecakeshopping.Model.CategoryInfo;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.ProductConst;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CategoryRetroFitService {

    @FormUrlEncoded
    @POST(DBConst.CATEGORY_PHP_FILENAME)
    Call<CategoryInfo> selectCategory(@Field(DBConst.Flag) String flag);


}
