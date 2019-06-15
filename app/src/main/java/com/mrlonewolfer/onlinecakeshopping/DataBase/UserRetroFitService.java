package com.mrlonewolfer.onlinecakeshopping.DataBase;

import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.UserConst;
import com.mrlonewolfer.onlinecakeshopping.Model.UserInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserRetroFitService {

    @FormUrlEncoded
    @POST(DBConst.USER_PHP_FILENAME)
    Call<UserInfo> selectUser(@Field(DBConst.Flag) String flag,
                              @Field(UserConst.EMAIL) String email,
                              @Field(UserConst.Pass)String pass,
                              @Field(UserConst.USER_TYPE) String usertype);

    @FormUrlEncoded
    @POST(DBConst.USER_PHP_FILENAME)
    Call<String> checkUser(@Field(DBConst.Flag) String flag,
                           @Field(UserConst.EMAIL)String email,
                           @Field(UserConst.Pass)String pass);
    @FormUrlEncoded
    @POST(DBConst.USER_PHP_FILENAME)
    Call<String> forgotPassCheck(@Field(DBConst.Flag) String flag,
                                 @Field(UserConst.EMAIL)String email,
                                 @Field(UserConst.MOBILE)String mobile);

    @FormUrlEncoded
    @POST(DBConst.USER_PHP_FILENAME)
    Call<String> resetUserPass(@Field(DBConst.Flag) String flag,
                                 @Field(UserConst.EMAIL) String email,
                                 @Field(UserConst.MOBILE)String mobile,
                                 @Field(UserConst.Pass)String pass);



    @FormUrlEncoded
    @POST(DBConst.USER_PHP_FILENAME)
    Call<String> insertRegUSer(@Field(DBConst.Flag) String flag,
                               @Field(UserConst.FIRST_NAME) String fname,
                               @Field(UserConst.LAST_NAME) String lname,
                               @Field(UserConst.MOBILE) String mobile,
                               @Field(UserConst.EMAIL) String email,
                               @Field(UserConst.Pass)String pass);


    @FormUrlEncoded
    @POST(DBConst.USER_PHP_FILENAME)
    Call<String> updateUserinfo(@Field(DBConst.Flag) String flag,
                                @Field(UserConst.ID) String id,
                               @Field(UserConst.FIRST_NAME) String fname,
                               @Field(UserConst.LAST_NAME) String lname,
                               @Field(UserConst.ADDRESS) String address,
                               @Field(UserConst.COUNTRY) String country,
                               @Field(UserConst.STATE)String state,
                                @Field(UserConst.CITY)String city,
                                @Field(UserConst.PINCODE)String pincode,
                                @Field(UserConst.USER_IMAGE)String userimage,
                                @Field(UserConst.ENCODEDIMAGE) String encodedImage);

}
