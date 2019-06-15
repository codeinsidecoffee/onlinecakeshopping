package com.mrlonewolfer.onlinecakeshopping.adminmodule.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mrlonewolfer.onlinecakeshopping.DataBase.AdminMasterRetroFitService;
import com.mrlonewolfer.onlinecakeshopping.DataBase.CategoryRetroFitService;
import com.mrlonewolfer.onlinecakeshopping.DataBase.RetroFitClient;
import com.mrlonewolfer.onlinecakeshopping.Model.CategoryInfo;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.ProductInfo;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.mrlonewolfer.onlinecakeshopping.adminmodule.Adapter.ManageCategoryAdapter;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageCategoryFragment extends Fragment implements ManageCategoryAdapter.OnManageCategoryClickListener {

    private static final int REQ_GALLERY = 2;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<CategoryInfo.Category> listCategoryInfo;
    Context context;
    CardView addcategory;
    ImageView imgnewcategory;
    Bitmap bitmap;
    private boolean imageselected=false;
    private String encodedImage;
    private String c_cat_id="",c_cat_name="",c_cat_img="";

    public ManageCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_manage_category, container, false);
        context=getActivity();
        recyclerView=view.findViewById(R.id.recyclerView);
        addcategory=view.findViewById(R.id.addcategory);
        layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        fetchListofCategory();
        addcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCategoryData(v);

            }
        });
        return  view;
    }

    private void addCategoryData(View v) {
        final CategoryInfo.Category currentCategoryInfo=new CategoryInfo.Category();
        LayoutInflater layoutInflater=getLayoutInflater();
        View layoutView=layoutInflater.inflate(R.layout.add_category_row_item, (ViewGroup) v.findViewById(R.id.addcategoryitem));
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Add New Category");
        builder.setMessage("Please Fill Up Detail CareFully");
        builder.setIcon(R.mipmap.app_icon);
        builder.setView(layoutView);

        final AlertDialog dialog=builder.create();

        final EditText edtcategoryname;

        Button btnSubmitcategory,btnCanclecategory;

        edtcategoryname=layoutView.findViewById(R.id.edtcategoryname);

        imgnewcategory=layoutView.findViewById(R.id.imgnewcategory);
        btnCanclecategory=layoutView.findViewById(R.id.btnCanclecategory);
        btnSubmitcategory=layoutView.findViewById(R.id.btnSubmitcategory);
        imgnewcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImagesFromSource();
            }
        });

        btnSubmitcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageselected==false){
                    Toast.makeText(context, "\t Please \n Select Category Image", Toast.LENGTH_SHORT).show();
                }else if(edtcategoryname.getText().toString().equals("")){
                    Toast.makeText(context, "\t Please \n Enter Category Name", Toast.LENGTH_SHORT).show();
                }else {
                    currentCategoryInfo.setCategoryName(edtcategoryname.getText().toString());

                        sendNewCategoryData(dialog, currentCategoryInfo);


                }

            }
        });
        btnCanclecategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageselected=false;
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void sendNewCategoryData(final AlertDialog dialog, CategoryInfo.Category currentCategoryInfo) {
        String cat_name=currentCategoryInfo.getCategoryName();
        String cat_image=currentCategoryInfo.getCategoryImage();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos); //bm is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();
        encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

        AdminMasterRetroFitService retroFitService=RetroFitClient.getAdminData();
        Call<String> call=retroFitService.addNewCategory(DBConst.CASE_9,
                                                    cat_name,
                                                    cat_image,
                                                    encodedImage);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String msg=response.body();
                    imageselected=false;
                    fetchListofCategory();
                    dialog.dismiss();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
                imageselected=false;
                dialog.dismiss();
            }
        });
    }

    private void pickImagesFromSource() {

        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , REQ_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_GALLERY){
            if(resultCode==RESULT_OK){
                Uri selectedImage = data.getData();
                imgnewcategory.setImageURI(selectedImage);

                try {

                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    // Log.d(TAG, String.valueOf(bitmap));
                    imageselected=true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void fetchListofCategory() {

        CategoryRetroFitService retroFitService= RetroFitClient.getCategory();
        Call<CategoryInfo> call=retroFitService.selectCategory(DBConst.CASE_1);
        call.enqueue(new Callback<CategoryInfo>() {
            @Override
            public void onResponse(Call<CategoryInfo> call, Response<CategoryInfo> response) {
                CategoryInfo categoryInfo=response.body();
                listCategoryInfo=categoryInfo.getCategory();
                setUpCategoryData();
            }

            @Override
            public void onFailure(Call<CategoryInfo> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setUpCategoryData() {
        ManageCategoryAdapter adapter=new ManageCategoryAdapter(context,listCategoryInfo,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnManageCategoryClick(final CategoryInfo.Category category, String action, View v) {
        if(action.equals("DELETE")){
            String cat_id=category.getCid();
            deleteCategoryData(cat_id);
        }
        if(action.equals("EDIT")){

            LayoutInflater layoutInflater=getLayoutInflater();
            View layoutView=layoutInflater.inflate(R.layout.add_category_row_item, (ViewGroup) v.findViewById(R.id.addcategoryitem));
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("Update  Category");
            builder.setMessage("Please Fill Up Detail CareFully");
            builder.setIcon(R.mipmap.app_icon);
            builder.setView(layoutView);

            final AlertDialog dialog=builder.create();

            final EditText edtcategoryname;

            Button btnSubmitcategory,btnCanclecategory;

            edtcategoryname=layoutView.findViewById(R.id.edtcategoryname);

            imgnewcategory=layoutView.findViewById(R.id.imgnewcategory);
            btnCanclecategory=layoutView.findViewById(R.id.btnCanclecategory);
            btnSubmitcategory=layoutView.findViewById(R.id.btnSubmitcategory);

            String cat_name = category.getCategoryName();
            String cat_image = category.getCategoryImage();
            String cat_image_path = DBConst.IMAGE_URL + "/images/category/thumbs/" + cat_image;
            edtcategoryname.setText(cat_name);
            category.setCategoryImage(cat_image);
            Picasso.with(context).load(cat_image_path).placeholder(R.mipmap.app_icon).into(imgnewcategory);

//            try {
//                URL url = new URL(cat_image_path);
//               bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
//                byte[] byteArrayImage = baos.toByteArray();
//                encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
//
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            imgnewcategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pickImagesFromSource();
                }
            });
            btnSubmitcategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(edtcategoryname.getText().toString().equals("")){
                        Toast.makeText(context, "\t Please \n Enter Category Name", Toast.LENGTH_SHORT).show();
                    }else {
                        category.setCategoryName(edtcategoryname.getText().toString());

                        updateCategoryData(dialog,category);

                    }

                }
            });
            btnCanclecategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageselected=false;
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    private void updateCategoryData(final AlertDialog dialog, CategoryInfo.Category category) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos); //bm is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();
        encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

       AdminMasterRetroFitService retroFitService=RetroFitClient.getAdminData();

       Call<String> call=retroFitService.updateCategory(DBConst.CASE_11,
               category.getCid(),
               category.getCategoryName(),
               category.getCategoryImage(),
               encodedImage);

       call.enqueue(new Callback<String>() {
           @Override
           public void onResponse(Call<String> call, Response<String> response) {
               String msg=response.body();
               imageselected=false;
               fetchListofCategory();
               dialog.dismiss();

           }

           @Override
           public void onFailure(Call<String> call, Throwable t) {
               Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
               imageselected=false;
               dialog.dismiss();
           }
       });


    }

    private void deleteCategoryData(String cat_id) {
        AdminMasterRetroFitService retroFitService=RetroFitClient.getAdminData();
        Call<String> call=retroFitService.deleteCategory(DBConst.CASE_10,
                                                            cat_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String msg=response.body();
                if(msg.equals("Category Delete successfully")){
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    fetchListofCategory();
                }else{
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
