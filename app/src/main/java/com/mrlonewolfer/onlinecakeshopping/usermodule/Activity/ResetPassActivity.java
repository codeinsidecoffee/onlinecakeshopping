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

public class ResetPassActivity extends AppCompatActivity {
    Button btnReset;
    EditText edtPass,edtRePass;
    UserInfo.User myUserInfo;
    String email,mobile,pass,repass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        Intent intent=getIntent();
        myUserInfo=intent.getParcelableExtra("myUserInfo");
        edtPass=findViewById(R.id.edtPass);
        edtRePass=findViewById(R.id.edtRePass);
        btnReset=findViewById(R.id.btnReset);
        email=myUserInfo.getEmail();
        mobile=myUserInfo.getMobile();
        Toast.makeText(this,"Enter New Password for : \n " +
                " "+ myUserInfo.getEmail(), Toast.LENGTH_LONG).show();


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass=edtPass.getText().toString();
                repass=edtRePass.getText().toString();
                if(pass.isEmpty() || repass.isEmpty()){
                    Toast.makeText(ResetPassActivity.this, "Please Fille All Details \n " +
                            "To Change Your Password", Toast.LENGTH_SHORT).show();
                }else{
                    if(pass.equals(repass)){
                        resetMyPass();
                    }else{
                        Toast.makeText(ResetPassActivity.this, "Password and Confirm Password \n" +
                                "Not Match", Toast.LENGTH_SHORT).show();
                        resetMyField();
                    }
                }

            }
        });

    }

    private void resetMyField() {
        edtRePass.setText("");
        edtPass.setText("");
    }

    private void resetMyPass() {
        UserRetroFitService userRetroFitService= RetroFitClient.getClient();
        Call<String> call=userRetroFitService.resetUserPass(DBConst.CASE_3,
                                                                email,
                                                                mobile,
                                                                pass);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String msg=response.body();
                if(msg.equals(DBConst.successFullReset)){
                    Toast.makeText(ResetPassActivity.this, "You Successfully Change Password", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(ResetPassActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(ResetPassActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ResetPassActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
