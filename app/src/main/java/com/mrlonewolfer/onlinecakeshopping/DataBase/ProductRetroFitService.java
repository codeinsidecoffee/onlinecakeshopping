package com.mrlonewolfer.onlinecakeshopping.DataBase;

import com.mrlonewolfer.onlinecakeshopping.Model.CategoryConst;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.ProductConst;
import com.mrlonewolfer.onlinecakeshopping.Model.ProductInfo;
import com.mrlonewolfer.onlinecakeshopping.Model.SpinnerWeight;
import com.mrlonewolfer.onlinecakeshopping.Model.UserProduct;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ProductRetroFitService {

    @FormUrlEncoded
    @POST(DBConst.PRODUCT_PHP_FILENAME)
    Call<ProductInfo> selectAllProduct(@Field(DBConst.Flag) String flag);

    @FormUrlEncoded
    @POST(DBConst.PRODUCT_PHP_FILENAME)
    Call<ProductInfo> selectCategoryProduct(@Field(DBConst.Flag) String flag,
                                            @Field(CategoryConst.CID) String cid);


    @FormUrlEncoded
    @POST(DBConst.PRODUCT_PHP_FILENAME)
    Call<UserProduct> selectAllProductList(@Field(DBConst.Flag) String flag);


}
