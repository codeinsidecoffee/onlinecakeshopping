package com.mrlonewolfer.onlinecakeshopping.DataBase;

import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.OrderConst;
import com.mrlonewolfer.onlinecakeshopping.Model.OrderInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OrderRetroFitService {


    @FormUrlEncoded
    @POST(DBConst.ORDER_PHP_FILENAME)
    Call<String> addNewOrder(@Field(DBConst.Flag) String flag,
                             @Field(OrderConst.ORDER_AMOUNT) double order_amount,
                             @Field(OrderConst.TOTAL_PRODUCT) double total_product,
                             @Field(OrderConst.USER_ID) String user_id,
                             @Field(OrderConst.PRODUCT_ID) String product_id,
                             @Field(OrderConst.TOTAL_UNIT) double total_unit,
                             @Field(OrderConst.UNIT_RATE) double unit_rate,
                             @Field(OrderConst.PURCHASE_AMOUNT) double purchase_amount,
                             @Field(OrderConst.CID) String cid,
                             @Field(OrderConst.PRODUCT_QTY_TYPE) String product_qty_type,
                             @Field(OrderConst.RECEIVE_DATE) String receive_date,
                             @Field(OrderConst.RECEIVE_TIME) String receive_time);

    @FormUrlEncoded
    @POST(DBConst.ORDER_PHP_FILENAME)
    Call<String> placeNewOrder(@Field(DBConst.Flag)String flag,
                               @Field(OrderConst.SEND_ORDER_LIST) String send_order_list);

    @FormUrlEncoded
    @POST(DBConst.ORDER_PHP_FILENAME)
    Call<OrderInfo> fetchUserOrder(@Field(DBConst.Flag)String flag,
                                   @Field(OrderConst.USER_ID) String user_id);
}
