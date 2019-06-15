package com.mrlonewolfer.onlinecakeshopping.usermodule.Activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mrlonewolfer.onlinecakeshopping.DataBase.RetroFitClient;
import com.mrlonewolfer.onlinecakeshopping.DataBase.UserRetroFitService;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.DataBase.SharedPrefrenceExample;
import com.mrlonewolfer.onlinecakeshopping.Model.PrefBean;
import com.mrlonewolfer.onlinecakeshopping.R;

public class RegistartionActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSignUp;
    TextView txtLogin;
    EditText edtFname,edtLname,edtMobile,edtEmail,edtPass,edtRePass;

    String fname,lname,mobile,email,pass,repass;
    CheckBox chkAgree;

    SharedPrefrenceExample sharedPrefrenceExample;
    Context context;
    PrefBean prefBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registartion);
        context=this;
        edtFname=findViewById(R.id.edtFname);
        edtLname=findViewById(R.id.edtLname);
        edtMobile=findViewById(R.id.edtMobile);
        edtEmail=findViewById(R.id.edtEmail);
        edtPass=findViewById(R.id.edtPass);
        edtRePass=findViewById(R.id.edtRePass);
        txtLogin=findViewById(R.id.txtLogin);
        btnSignUp=findViewById(R.id.btnSignUp);
        chkAgree=findViewById(R.id.chkAgree);
        btnSignUp.setOnClickListener(this);
        txtLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnSignUp){
            prefBean =new PrefBean();
        fname=edtFname.getText().toString();
        lname=edtLname.getText().toString();
        mobile=edtMobile.getText().toString();
        email=edtEmail.getText().toString();
        pass=edtPass.getText().toString();
        repass=edtRePass.getText().toString();

        prefBean.setEmail(email);
        prefBean.setPass(pass);
        prefBean.setStatus(DBConst.Status_False);
        prefBean.setUserid("");
        
        if(fname.isEmpty() || lname.isEmpty() || mobile.isEmpty() || 
                email.isEmpty()|| pass.isEmpty()|| repass.isEmpty()){
            Toast.makeText(this, "Please Fill All Details CareFully.", Toast.LENGTH_SHORT).show();
        }else{
            if(pass.length()>6 && pass.length()<20){
                if(pass.equals(repass)){
                    if(pass.toLowerCase().equals(fname.toLowerCase())){
                        Toast.makeText(this, "you can not use your fname as a password", Toast.LENGTH_SHORT).show();
                        clearPassField();
                    }
                    else if(pass.toLowerCase().equals(lname.toLowerCase())){
                        Toast.makeText(this, "you can not use your lname as a password", Toast.LENGTH_SHORT).show();
                        clearPassField();
                    }
                    else if(pass.equals(mobile)){
                        Toast.makeText(this, "you can not use your mobile as a password", Toast.LENGTH_SHORT).show();
                        clearPassField();
                    }
                    else if(pass.toLowerCase().equals(email.toLowerCase())){
                        Toast.makeText(this, "you can not use your email as a password", Toast.LENGTH_SHORT).show();
                        clearPassField();
                    }
                    else if(pass.toLowerCase().equals(fname.toLowerCase()+lname.toLowerCase())){
                        Toast.makeText(this, "you can not use your FirstName and LastName as a password", Toast.LENGTH_SHORT).show();
                        clearPassField();
                    }
                    else {
                        if(chkAgree.isChecked()){

                         prefBean.setStatus(DBConst.Status_True);
                         prefBean.setUsertype("1");
                            makeUserRegistration();
                        clearAllField();
                        Intent intent=new Intent(RegistartionActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        }else{
                            Toast.makeText(this, "Please Click on I Agree", Toast.LENGTH_SHORT).show();
                        }
                    }
                }    
                else {
                    Toast.makeText(this, "Your Password and Confirm pass Does Not Match", Toast.LENGTH_SHORT).show();
                    clearPassField();
                }
                
            }else{
                Toast.makeText(this, "Password must between 6-20 character", Toast.LENGTH_SHORT).show();
                clearPassField();
            }
            
        }
        
        }
        if(v.getId()==R.id.txtLogin){
            Intent intent=new Intent(RegistartionActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void makeUserRegistration() {

        UserRetroFitService userRetroFitService= RetroFitClient.getClient();
        Call<String> call=userRetroFitService.insertRegUSer(DBConst.CASE_6,
                                                            fname,
                                                            lname,
                                                            mobile,
                                                            email,
                                                            pass);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String msg=response.body();
                Toast.makeText(RegistartionActivity.this, msg, Toast.LENGTH_SHORT).show();
                StoreDataInSharedPrefrence();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegistartionActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void StoreDataInSharedPrefrence() {
        sharedPrefrenceExample = new SharedPrefrenceExample(DBConst.FILE_NAME,context);
        sharedPrefrenceExample.setSharedPreferences(prefBean);

    }


    private void clearAllField() {
        edtPass.setText("");
        edtRePass.setText("");
        edtFname.setText("");
        edtMobile.setText("");
        edtLname.setText("");
        edtEmail.setText("");
    }

    private void clearPassField() {
        edtRePass.setText("");
        edtPass.setText("");
    }
}
