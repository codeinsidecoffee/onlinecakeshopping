package com.mrlonewolfer.onlinecakeshopping.adminmodule.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.mrlonewolfer.onlinecakeshopping.DataBase.RetroFitClient;
import com.mrlonewolfer.onlinecakeshopping.DataBase.SharedPrefrenceExample;
import com.mrlonewolfer.onlinecakeshopping.DataBase.UserRetroFitService;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.PrefBean;
import com.mrlonewolfer.onlinecakeshopping.Model.UserInfo;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.mrlonewolfer.onlinecakeshopping.adminmodule.Fragment.DashboardFragment;
import com.mrlonewolfer.onlinecakeshopping.adminmodule.Fragment.ManageCategoryFragment;
import com.mrlonewolfer.onlinecakeshopping.adminmodule.Fragment.ManageOrderFragment;
import com.mrlonewolfer.onlinecakeshopping.adminmodule.Fragment.ManageProductFragment;
import com.mrlonewolfer.onlinecakeshopping.adminmodule.Fragment.ManageUserFragment;
import com.mrlonewolfer.onlinecakeshopping.usermodule.Activity.HomeActivity;
import com.mrlonewolfer.onlinecakeshopping.usermodule.Activity.LoginActivity;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int My_PERMISSION_REQUEST_FOR_WRITE_EXTERNAL =1 ;
    String email,pass,fname,lname,address,country,city,id,pincode,user_image,state,usertype;
    List<UserInfo.User> loginUserInfo;
    UserInfo.User currentUserInfo;
    PrefBean prefBean;
    Context context;
    View hView;
    EditText edtFname,edtLname,edtaddress,edtCity,edtCountry,edtState,edtPincode;
    ImageView imgProfile;
    private Bitmap bitmap;
    private String encodedImage;
    private static final int REQ_GALLERY = 2;
    public static Context contextOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        prefBean=new PrefBean();
        Intent intent=getIntent();
        prefBean=intent.getParcelableExtra("prefBean");
        email=prefBean.getEmail();
        pass=prefBean.getPass();
        usertype=prefBean.getUsertype();
        context=this;

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_admin_view);
        hView=  navigationView.getHeaderView(0);


        retriveUserInfo();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment= new DashboardFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.adminMainContainer,fragment);
        fragmentTransaction.commit();
        checkPermission();
    }

    private void retriveUserInfo() {
        UserRetroFitService userRetroFitService= RetroFitClient.getClient();
        Call<UserInfo> call= userRetroFitService.selectUser(DBConst.CASE_1,
                email,
                pass,
                usertype);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                UserInfo userInfo=response.body();
                loginUserInfo=userInfo.getUsers();
                currentUserInfo=loginUserInfo.get(0);
                fname=currentUserInfo.getFname();
                lname=currentUserInfo.getLname();
                email=currentUserInfo.getEmail();
                prefBean.setUserid(currentUserInfo.getId());

                String prduct_image=currentUserInfo.getUserImage();
                String product_image_path= DBConst.IMAGE_URL+ "/images/userimages/"+prduct_image;


                Log.e("onResponse", "onResponse: "+fname+" "+lname+" "+email );
                ImageView adminImageView=(ImageView)hView.findViewById(R.id.adminImageView);

                TextView txtProfile=(TextView) hView.findViewById(R.id.txtProfile);
                TextView txtNavHeading = (TextView)hView.findViewById(R.id.txtNavHeading);
                txtNavHeading.setText(fname+" "+lname);
                TextView txtNavSubHeading=hView.findViewById(R.id.txtNavSubHeading);
                txtNavSubHeading.setText(email);
                Picasso.with(context).load(product_image_path).error(R.mipmap.app_icon).into(adminImageView);
                txtProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateProfile();

                    }
                });
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Toast.makeText(AdminHomeActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_GALLERY){
            if(resultCode==RESULT_OK){
                Uri selectedImage = data.getData();
                imgProfile.setImageURI(selectedImage);

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    // Log.d(TAG, String.valueOf(bitmap));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void updateProfile() {
        LayoutInflater layoutInflater=getLayoutInflater();
        View layoutView=layoutInflater.inflate(R.layout.custom_profile_update,
                (ViewGroup) findViewById(R.id.manageProfile));

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Update Your Profile");
        builder.setMessage("Submit Detail Carefully");
        builder.setIcon(R.mipmap.app_icon);

        builder.setView(layoutView);



        Button btnCancel,btnSubmit;

        edtFname=layoutView.findViewById(R.id.etFName);
        edtLname=layoutView.findViewById(R.id.etLname);
        edtaddress=layoutView.findViewById(R.id.etaddress);
        edtCountry=layoutView.findViewById(R.id.etCountry);
        edtState=layoutView.findViewById(R.id.etState);
        edtCity=layoutView.findViewById(R.id.etCity);
        edtPincode=layoutView.findViewById(R.id.etPincode);
        imgProfile=layoutView.findViewById(R.id.imgProfile);
        btnCancel=layoutView.findViewById(R.id.btnCancle);
        btnSubmit=layoutView.findViewById(R.id.btnSubmit);

        edtFname.setText(currentUserInfo.getFname());
        edtLname.setText(currentUserInfo.getLname());
        edtaddress.setText(currentUserInfo.getAddress());
        edtCountry.setText(currentUserInfo.getCountry());
        edtState.setText(currentUserInfo.getState());
        edtCity.setText(currentUserInfo.getCity());
        edtPincode.setText(currentUserInfo.getPincode());

        final AlertDialog dialog=builder.create();

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImagesFromSource();
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname=edtFname.getText().toString();
                lname=edtLname.getText().toString();
                address=edtaddress.getText().toString();
                country=edtCountry.getText().toString();
                state=edtState.getText().toString();
                city=edtCity.getText().toString();
                pincode=edtPincode.getText().toString();
                id=currentUserInfo.getId();
                user_image=fname+lname+"_profile.jpg";

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos); //bm is the bitmap object
                byte[] byteArrayImage = baos.toByteArray();
                encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

                // Log.i("IMAGE",encodedImage);


                UserRetroFitService userRetroFitService= RetroFitClient.getClient();
                Call<String> call=userRetroFitService.updateUserinfo(DBConst.CASE_2,
                        id,
                        fname,
                        lname,
                        address,
                        country,
                        state,
                        city,
                        pincode,
                        user_image,
                        encodedImage);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String msg=response.body();
                        Toast.makeText(AdminHomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                        retriveUserInfo();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(AdminHomeActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void pickImagesFromSource() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , REQ_GALLERY);
    }

    private void checkPermission() {
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]
                    {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE

                    }, My_PERMISSION_REQUEST_FOR_WRITE_EXTERNAL);

        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_adminlogout) {
            SharedPrefrenceExample sharedPrefrenceExample=new SharedPrefrenceExample(DBConst.FILE_NAME,context);
            sharedPrefrenceExample.logout();
            Intent intent=new Intent(AdminHomeActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }if(id==R.id.action_pendingOrder){
            Fragment fragment=new ManageOrderFragment();
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.adminMainContainer,fragment).commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment=null;

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.admin_nav_dashboard) {

            fragment=new DashboardFragment();

        } else if (id == R.id.admin_nav_user) {

            fragment=new ManageUserFragment();
        }else if (id == R.id.admin_nav_category) {

            fragment=new ManageCategoryFragment();

        }
        else if (id == R.id.admin_nav_product) {

            fragment=new ManageProductFragment();

        } else if (id == R.id.admin_nav_order) {

            fragment=new ManageOrderFragment();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.adminMainContainer,fragment).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
