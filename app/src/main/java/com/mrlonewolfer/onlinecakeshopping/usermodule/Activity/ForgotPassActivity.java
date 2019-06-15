package com.mrlonewolfer.onlinecakeshopping.usermodule.Activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mrlonewolfer.onlinecakeshopping.DataBase.RetroFitClient;
import com.mrlonewolfer.onlinecakeshopping.DataBase.UserRetroFitService;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.UserInfo;
import com.mrlonewolfer.onlinecakeshopping.R;

public class ForgotPassActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSubmit;
    EditText edtEmail,edtMobile;
    String email,mobile;
    UserInfo.User myuserInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        edtEmail=findViewById(R.id.edtEmail);
        edtMobile=findViewById(R.id.edtMobile);
        btnSubmit=findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnSubmit){
            email=edtEmail.getText().toString();
            mobile=edtMobile.getText().toString();

            if(email.isEmpty() || mobile.isEmpty()){
                Toast.makeText(this, "Please Fill All Details To \n " +
                        "Reset Your Password", Toast.LENGTH_SHORT).show();
            }else{
                validateUserInfo();
            }

        }
    }

    private void validateUserInfo() {
        UserRetroFitService userRetroFitService= RetroFitClient.getClient();
        Call<String> call=userRetroFitService.forgotPassCheck(DBConst.CASE_5,
                                                                email,
                                                                mobile);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String msg=response.body();
                if(msg.equals(DBConst.validUser)){
                    myuserInfo=new UserInfo.User();
                    myuserInfo.setEmail(email);
                    myuserInfo.setMobile(mobile);

                    Intent intent=new Intent(ForgotPassActivity.this,ResetPassActivity.class);
                    intent.putExtra("myUserInfo",myuserInfo);
                    startActivity(intent);
                    finish();
                }else if(msg.equals(DBConst.inValidUser)){
                    Toast.makeText(ForgotPassActivity.this, "User Not Found \n" +
                            "Please Enter Correct Details", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForgotPassActivity.this, "Something Went Wrong \n" +
                            "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
