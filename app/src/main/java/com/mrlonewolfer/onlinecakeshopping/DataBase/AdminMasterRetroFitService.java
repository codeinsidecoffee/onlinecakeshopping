package com.mrlonewolfer.onlinecakeshopping.DataBase;

import com.mrlonewolfer.onlinecakeshopping.Model.CategoryConst;
import com.mrlonewolfer.onlinecakeshopping.Model.CategoryInfo;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.MasterProduct;
import com.mrlonewolfer.onlinecakeshopping.Model.OrderConst;
import com.mrlonewolfer.onlinecakeshopping.Model.OrderInfo;
import com.mrlonewolfer.onlinecakeshopping.Model.ProductConst;
import com.mrlonewolfer.onlinecakeshopping.Model.ProductInfo;
import com.mrlonewolfer.onlinecakeshopping.Model.QtyTypeInfo;
import com.mrlonewolfer.onlinecakeshopping.Model.UserConst;
import com.mrlonewolfer.onlinecakeshopping.Model.UserInfo;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AdminMasterRetroFitService {

    @FormUrlEncoded
    @POST(DBConst.ADMINMASTER_PHP_FILENAME)
    Call<List<String>> selectDashBoard(@Field(DBConst.Flag) String flag);

    @FormUrlEncoded
    @POST(DBConst.ADMINMASTER_PHP_FILENAME)
    Call<UserInfo> fetchAllUserInfo(@Field(DBConst.Flag) String flag);

    @FormUrlEncoded
    @POST(DBConst.ADMINMASTER_PHP_FILENAME)
    Call<String> changeUserStatus(@Field(DBConst.Flag) String flag,
                                    @Field(UserConst.EMAIL) String email,
                                    @Field(UserConst.MOBILE)String mobile,
                                    @Field(UserConst.USER_STATUS)String status);
    @FormUrlEncoded
    @POST(DBConst.ADMINMASTER_PHP_FILENAME)
    Call<String> addNewProduct(@Field(DBConst.Flag) String flag,
                               @Field(ProductConst.Cat_ID) String cat_id,
                               @Field(ProductConst.PRODUCT_TITLE) String pro_title,
                               @Field(ProductConst.PRODUCT_IMAGE) String pro_image,
                               @Field(ProductConst.PRODUCT_TYPE) String pro_type,
                               @Field(ProductConst.PRODUCT_DESC) String pro_desc,
                                @Field(ProductConst.ENCODEDIMAGE) String encodedImage);

    @FormUrlEncoded
    @POST(DBConst.ADMINMASTER_PHP_FILENAME)
    Call<CategoryInfo> selectCategoryForPrize(@Field(DBConst.Flag) String flag,
                                              @Field(ProductConst.PRODUCT_TYPE) String pro_type);

    @FormUrlEncoded
    @POST(DBConst.ADMINMASTER_PHP_FILENAME)
    Call<ProductInfo> selectProductForPrize(@Field(DBConst.Flag) String flag,
                                            @Field(ProductConst.Cat_ID) String cat_id,
                                            @Field(ProductConst.PRODUCT_TYPE) String pro_type);

    @FormUrlEncoded
    @POST(DBConst.ADMINMASTER_PHP_FILENAME)
    Call<QtyTypeInfo> selectPQTYForPrize(@Field(DBConst.Flag) String flag);

    @FormUrlEncoded
    @POST(DBConst.ADMINMASTER_PHP_FILENAME)
    Call<String> insertProductPrize(@Field(DBConst.Flag) String flag,
                                    @Field(ProductConst.PRODUCT_TYPE) String pro_type,
                                    @Field(ProductConst.Cat_ID) String cat_id,
                                    @Field(ProductConst.PRODUCT_QTY_ID) String pro_qty_id,
                                    @Field(ProductConst.PRODUCT_ID) String pro_id,
                                    @Field(ProductConst.PRODUCT_RATE) String pro_rate);


    @FormUrlEncoded
    @POST(DBConst.ADMINMASTER_PHP_FILENAME)
    Call<String> addNewCategory(@Field(DBConst.Flag) String flag,
                               @Field(CategoryConst.CAT_NAME) String cat_name,
                               @Field(CategoryConst.CAT_IMAGE) String cat_image,
                               @Field(CategoryConst.ENCODEDIMAGE) String encodedImage);


    @FormUrlEncoded
    @POST(DBConst.ADMINMASTER_PHP_FILENAME)
    Call<String> deleteCategory(@Field(DBConst.Flag) String flag,
                                @Field(CategoryConst.CID) String cat_id);

    @FormUrlEncoded
    @POST(DBConst.ADMINMASTER_PHP_FILENAME)
    Call<String> updateCategory(@Field(DBConst.Flag) String flag,
                                @Field(CategoryConst.CID) String cat_id,
                                @Field(CategoryConst.CAT_NAME) String cat_name,
                                @Field(CategoryConst.CAT_IMAGE) String cat_image,
                                @Field(CategoryConst.ENCODEDIMAGE) String encodedImage);

    @FormUrlEncoded
    @POST(DBConst.ADMINMASTER_PHP_FILENAME)
    Call<String> updateProductStatus(@Field(DBConst.Flag) String flag,
                                @Field(ProductConst.PRODUCT_ID) String pro_id,
                                @Field(ProductConst.PRODUCT_STATUS) String pro_status);

    @FormUrlEncoded
    @POST(DBConst.ADMINMASTER_PHP_FILENAME)
    Call<MasterProduct> selectSingleProductDetail(@Field(DBConst.Flag) String flag,
                                         @Field(ProductConst.PRODUCT_ID) String pro_id);

    @FormUrlEncoded
    @POST(DBConst.ADMINMASTER_PHP_FILENAME)
    Call<String> updateProductAllDetails(@Field(DBConst.Flag) String flag,
                                         @Field(ProductConst.PRODUCT_ID) String pro_id,
                                         @Field(ProductConst.PRODUCT_TITLE) String pro_title,
                                         @Field(ProductConst.PRODUCT_DESC) String pro_desc,
                                         @Field(ProductConst.Cat_ID) String cat_id,
                                         @Field(ProductConst.PRODUCT_RATE) String pro_rate,
                                         @Field(ProductConst.PRODUCT_QTY_TYPE) String pro_qty_type,
                                         @Field(ProductConst.PRODUCT_TYPE) String pro_type_name);

    @FormUrlEncoded
    @POST(DBConst.ADMINMASTER_PHP_FILENAME)
    Call<String> deleteCurrentProduct(@Field(DBConst.Flag) String flag,
                                      @Field(ProductConst.PRODUCT_ID) String pro_id);

    @FormUrlEncoded
    @POST(DBConst.ADMINMASTER_PHP_FILENAME)
    Call<List<String>> selectOrderDashBoard(@Field(DBConst.Flag) String flag);

    @FormUrlEncoded
    @POST(DBConst.ADMINMASTER_PHP_FILENAME)
    Call<OrderInfo> fetchAllOrderDetails(@Field(DBConst.Flag)String flag,
                                         @Field(OrderConst.ORDER_STATUS)String order_status);

    @FormUrlEncoded
    @POST(DBConst.ADMINMASTER_PHP_FILENAME)
    Call<String> updateProductOrderStatus(@Field(DBConst.Flag) String flag,
                                          @Field(OrderConst.PRODUCT_STATUS)String product_status,
                                          @Field(OrderConst.ORDER_DETAILS_ID)String order_details_id);

    @FormUrlEncoded
    @POST(DBConst.ADMINMASTER_PHP_FILENAME)
    Call<String> updatecurrentOrderStatus(@Field(DBConst.Flag) String flag,
                                          @Field(OrderConst.ORDER_STATUS)String order_status,
                                          @Field(OrderConst.ORDER_ID)String order_id);

    @FormUrlEncoded
    @POST(DBConst.ADMINMASTER_PHP_FILENAME)
    Call<String> cancelUserProductStatus(@Field(DBConst.Flag) String flag,
                                          @Field(OrderConst.ORDER_DETAILS_ID)String order_details_id);

}
