package com.mrlonewolfer.onlinecakeshopping.usermodule.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.DataBase.SharedPrefrenceExample;
import com.mrlonewolfer.onlinecakeshopping.Model.PrefBean;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.mrlonewolfer.onlinecakeshopping.adminmodule.Activity.AdminHomeActivity;

public class SplashActivity extends AppCompatActivity {
    private boolean mIsBackButtonPressed;
    private static final int SPLASH_DURATION = 1000;
    SharedPrefrenceExample sharedPrefrenceExample;
    Context context;
    PrefBean prefBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        //getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_splash);
        context=this;



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!mIsBackButtonPressed) {

                    sharedPrefrenceExample = new SharedPrefrenceExample(DBConst.FILE_NAME,context);
                    prefBean=sharedPrefrenceExample.getSharedPreferences();
                    if(prefBean.getEmail()!=null){
                        Log.e("Userprefrence", "Userprefrence "+prefBean.getEmail()+"\n"+
                                prefBean.getPass()+"\n"+prefBean.getStatus()+
                                "\n"+prefBean.getUsertype()+
                                "\n"+prefBean.getUserid());

                        if(prefBean.getStatus().equals(DBConst.Status_True)){
                            if(prefBean.getUsertype().equals("1")){
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                intent.putExtra("prefBean",prefBean);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                            if(prefBean.getUsertype().equals("0")){
                                Intent intent = new Intent(getApplicationContext(), AdminHomeActivity.class);
                                intent.putExtra("prefBean",prefBean);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }

                        }
                        else{
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }

                    }else{

                        Intent intent = new Intent(getApplicationContext(),RegistartionActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    }

                }
            }
        },SPLASH_DURATION);
    }

    public void onBackPressed() {
        // set the flag to true so the next activity won't start up
        mIsBackButtonPressed = true;
        super.onBackPressed();

    }
}




