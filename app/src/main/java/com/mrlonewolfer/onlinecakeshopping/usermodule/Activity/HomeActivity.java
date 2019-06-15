package com.mrlonewolfer.onlinecakeshopping.usermodule.Activity;

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
import com.mrlonewolfer.onlinecakeshopping.usermodule.Fragment.AddToCartFragment;
import com.mrlonewolfer.onlinecakeshopping.usermodule.Fragment.CategoryFragment;
import com.mrlonewolfer.onlinecakeshopping.usermodule.Fragment.FavouriteFragment;
import com.mrlonewolfer.onlinecakeshopping.usermodule.Fragment.HomeFragment;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.PrefBean;
import com.mrlonewolfer.onlinecakeshopping.Model.UserInfo;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.mrlonewolfer.onlinecakeshopping.usermodule.Fragment.ViewOrderFragment;
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

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String email,pass,fname,lname,address,country,city,id,pincode,user_image,state,usertype;
    EditText edtFname,edtLname,edtaddress,edtCity,edtCountry,edtState,edtPincode;
    ImageView imgProfile;
    List<UserInfo.User> loginUserInfo;
    UserInfo.User currentUserInfo;
    Context context;
    PrefBean prefBean;
    View hView;
    private static final int REQ_GALLERY = 2;
    private static final int My_PERMISSION_REQUEST_FOR_WRITE_EXTERNAL =1 ;
    private Bitmap bitmap;
    private String encodedImage;
    SharedPrefrenceExample sharedPrefrenceExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        prefBean=new PrefBean();
        Intent intent=getIntent();
        prefBean=intent.getParcelableExtra("prefBean");
            email=prefBean.getEmail();
            pass=prefBean.getPass();
            usertype=prefBean.getUsertype();
            context=this;
        Log.e("Userprefrence", "Userprefrence: "+email+" "+pass+" "+" "+usertype );
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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

            Fragment fragment= new HomeFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.mainContainer,fragment);
            fragmentTransaction.commit();
            checkPermission();

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
                storeDataInSharedPrefrence();
                String prduct_image=currentUserInfo.getUserImage();
                String product_image_path= DBConst.IMAGE_URL+ "/images/userimages/"+prduct_image;


                Log.e("onResponse", "onResponse: "+fname+" "+lname+" "+email );
                ImageView adminImageView=(ImageView)hView.findViewById(R.id.userimageView);


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
                Toast.makeText(HomeActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeDataInSharedPrefrence() {
        sharedPrefrenceExample = new SharedPrefrenceExample(DBConst.FILE_NAME,context);
        sharedPrefrenceExample.setSharedPreferences(prefBean);
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
                            Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                        retriveUserInfo();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(HomeActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });

        dialog.show();


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

    private void pickImagesFromSource() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , REQ_GALLERY);
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
        getMenuInflater().inflate(R.menu.user_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_userlogout) {
            SharedPrefrenceExample sharedPrefrenceExample=new SharedPrefrenceExample(DBConst.FILE_NAME,context);
            sharedPrefrenceExample.logout();
            Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
            return true;
        }
        if(id==R.id.action_viewcart){
            Fragment fragment=new AddToCartFragment();
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainContainer,fragment).commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment=null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragment=new HomeFragment();

        } else if (id == R.id.nav_category) {
            fragment=new CategoryFragment();

        } else if (id == R.id.nav_favourite) {
            fragment=new FavouriteFragment();

        } else if (id == R.id.nav_order) {
            fragment=new ViewOrderFragment();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,fragment).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
