package com.mrlonewolfer.onlinecakeshopping.usermodule.Activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mrlonewolfer.onlinecakeshopping.DataBase.SharedPrefrenceExample;
import com.mrlonewolfer.onlinecakeshopping.DataBase.RetroFitClient;
import com.mrlonewolfer.onlinecakeshopping.DataBase.UserRetroFitService;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.PrefBean;
import com.mrlonewolfer.onlinecakeshopping.Model.UserInfo;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.mrlonewolfer.onlinecakeshopping.adminmodule.Activity.AdminHomeActivity;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLoginIn;
    CheckBox chkAgree;
    TextView txtSignUp,txtForgotPass;
    EditText edtEmail,edtPass;
    private String email,pass;
    PrefBean prefBean;
    List<UserInfo.User> loginUserInfo;
    UserInfo.User currentUserInfo;
    SharedPrefrenceExample sharedPrefrenceExample;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context=this;
        edtEmail=findViewById(R.id.edtEmail);
        edtPass=findViewById(R.id.edtPass);
        btnLoginIn=findViewById(R.id.btnLogin);
        chkAgree=findViewById(R.id.chkAgree);
        txtSignUp=findViewById(R.id.txtSignUp);
        txtForgotPass=findViewById(R.id.txtForgotPass);

        btnLoginIn.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
        txtForgotPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnLogin){
            prefBean =new PrefBean();

            email=edtEmail.getText().toString();
            pass=edtPass.getText().toString();
//
//            prefBean.setEmail(email);
//            prefBean.setPass(pass);
//            prefBean.setStatus(DBConst.Status_True);

            if(email.isEmpty() || pass.isEmpty()){
                Toast.makeText(this, "Please eneter email and password for login", Toast.LENGTH_SHORT).show();
            }else{
                checkmyidentity();

            }


        }
        if(v.getId()==R.id.txtSignUp){
            Intent intent=new Intent(LoginActivity.this,RegistartionActivity.class);
            startActivity(intent);
            finish();
        }
        if(v.getId()==R.id.txtForgotPass){
            Intent intent=new Intent(LoginActivity.this,ForgotPassActivity.class);
            startActivity(intent);
        }
    }



    private void checkmyidentity() {
        UserRetroFitService userRetroFitService= RetroFitClient.getClient();
        Call<String> call=userRetroFitService.checkUser(DBConst.CASE_4,
                                                    email,
                                                    pass);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String msg=response.body();
                if(msg.equals(DBConst.successFullUserLogin)){

                    prefBean.setEmail(edtEmail.getText().toString());
                    prefBean.setPass(edtPass.getText().toString());
                    prefBean.setStatus(DBConst.Status_True);
                    prefBean.setUsertype("1");
                    prefBean.setUserid("");

                    storeDataInSharedPrefrence();


                    Toast.makeText(LoginActivity.this, "UserLogin Succefully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                    intent.putExtra("prefBean",prefBean);
                    startActivity(intent);
                    finish();
                }else if(msg.equals(DBConst.successFulladminLogin)){
                    prefBean.setUsertype("0");
                    prefBean.setEmail(edtEmail.getText().toString());
                    prefBean.setPass(edtPass.getText().toString());
                    prefBean.setStatus(DBConst.Status_True);
                    prefBean.setUserid("");
                    storeDataInSharedPrefrence();


                    Toast.makeText(LoginActivity.this, "AdminLogin Succefully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this, AdminHomeActivity.class);
                    intent.putExtra("prefBean",prefBean);
                    startActivity(intent);
                    finish();
                }
                else if(msg.equals(DBConst.unSuccessFullLogin)) {
                    Toast.makeText(LoginActivity.this, "Please Enter Valid UserName and Password", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Something Went Wrong Please \n Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }
    private void storeDataInSharedPrefrence() {
        sharedPrefrenceExample = new SharedPrefrenceExample(DBConst.FILE_NAME,context);
        sharedPrefrenceExample.setSharedPreferences(prefBean);


    }
}
