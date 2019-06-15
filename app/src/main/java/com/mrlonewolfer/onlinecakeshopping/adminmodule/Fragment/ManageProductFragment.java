package com.mrlonewolfer.onlinecakeshopping.adminmodule.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mrlonewolfer.onlinecakeshopping.DataBase.AdminMasterRetroFitService;
import com.mrlonewolfer.onlinecakeshopping.DataBase.CategoryRetroFitService;
import com.mrlonewolfer.onlinecakeshopping.DataBase.RetroFitClient;
import com.mrlonewolfer.onlinecakeshopping.Model.CategoryConst;
import com.mrlonewolfer.onlinecakeshopping.Model.CategoryInfo;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.ProductInfo;
import com.mrlonewolfer.onlinecakeshopping.Model.QtyTypeInfo;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.mrlonewolfer.onlinecakeshopping.adminmodule.Adapter.SpinnerCategoryAdapter;
import com.mrlonewolfer.onlinecakeshopping.adminmodule.Adapter.SpinnerProductAdapter;
import com.mrlonewolfer.onlinecakeshopping.adminmodule.Adapter.SpinnerQtyTypeAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageProductFragment extends Fragment implements View.OnClickListener {
    String total_user,total_category,total_product,total_order;
    Context context;
    TextView txtviewallproduct;
    CardView viewallproductcard,addproductcard,addprizecard;
    ImageView imgnewproduct;
    List<CategoryInfo.Category> listCategoryInfo;
    Spinner spinnerProcategory,spinnerproductprize,spinnerqtyTypeprize;
    private Bitmap bitmap;
    private String encodedImage;
    private static final int REQ_GALLERY = 2;
    ProductInfo.Product currentProductInfo;
    CategoryInfo.Category currentCategoryInfo;
    String currentProductTypeName;



    private boolean imageselected=false;
    private List<ProductInfo.Product> listProductInfo;
    private List<QtyTypeInfo.Qtytype> listqtyTypeInfo;
    private QtyTypeInfo.Qtytype currentQtyTypeInfo;

    public ManageProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_manage_product, container, false);
        context=getActivity();


        currentQtyTypeInfo=new QtyTypeInfo.Qtytype();

        txtviewallproduct=view.findViewById(R.id.txtviewallproduct);
        addproductcard=view.findViewById(R.id.addproductcard);
        viewallproductcard=view.findViewById(R.id.viewallproductcard);
        addprizecard=view.findViewById(R.id.addprizecard);

        fetchDashBoardData();
        fetchListOfCategory();
        addproductcard.setOnClickListener(this);
        viewallproductcard.setOnClickListener(this);
        addprizecard.setOnClickListener(this);
        return view;
    }

    private void fetchDashBoardData() {
        AdminMasterRetroFitService adminMasterRetroFitService= RetroFitClient.getAdminData();
        Call<List<String>> call=adminMasterRetroFitService.selectDashBoard(DBConst.CASE_1);

        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                List<String> dashBoardInfo=response.body();

                total_product=dashBoardInfo.get(2);
                txtviewallproduct.setText(total_product);

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.viewallproductcard){
            Fragment fragment=new ViewAllProductFragment();
            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.adminMainContainer,fragment).commit();
        }
        if(v.getId()==R.id.addproductcard){
          addnewProduct(v);

        }
        if(v.getId()==R.id.addprizecard){
            addProductPrize(v);

        }
    }

    private void addProductPrize(View v) {


        LayoutInflater layoutInflater=getLayoutInflater();
        View layoutView=layoutInflater.inflate(R.layout.add_prize_row_item, (ViewGroup) v.findViewById(R.id.addproductitem));
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Add New Product");
        builder.setMessage("Please Fill Up Detail CareFully");
        builder.setIcon(R.mipmap.app_icon);
        builder.setView(layoutView);
        final AlertDialog dialog=builder.create();
        RadioGroup radioproducteprizeType;
        Button btnAddPrize,btnCanclePrize;
        final EditText edtproductprize;

        spinnerProcategory=layoutView.findViewById(R.id.spinnercategoryprize);
        spinnerproductprize=layoutView.findViewById(R.id.spinnerproductprize);
        spinnerqtyTypeprize=layoutView.findViewById(R.id.spinnerqtyTypeprize);
        edtproductprize=layoutView.findViewById(R.id.edtproductprize);
        radioproducteprizeType=layoutView.findViewById(R.id.radioproductprizeType);
        btnAddPrize=layoutView.findViewById(R.id.btnAddPrize);
        btnCanclePrize=layoutView.findViewById(R.id.btnCanclePrize);
        currentProductTypeName=getString(R.string.egg);
        setUpCategoryForPrize();
        radioproducteprizeType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String product_type ="";
                if(checkedId==R.id.radio_eggprize){
                    product_type=getString(R.string.egg);
                }
                if(checkedId==R.id.radio_egglessprize){
                    product_type=getString(R.string.eggless);
                }
                currentProductTypeName=product_type;
                setUpCategoryForPrize();
            }
        });

        btnAddPrize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String productPrize=edtproductprize.getText().toString();
                 if(currentCategoryInfo.getCategoryName().equals(CategoryConst.SELECT_CATEGORY_TOAST)){
                    Toast.makeText(context, "\t Please \n Select Category", Toast.LENGTH_SHORT).show();
                }else if(currentQtyTypeInfo.getProductQtyType().equals(CategoryConst.SELECT_ProductQTY_TOAST)){
                     Toast.makeText(context, CategoryConst.SELECT_ProductQTY_TOAST, Toast.LENGTH_SHORT).show();
                 }else if(productPrize.equals("")){
                    Toast.makeText(context, "\t Please \n enter product prize", Toast.LENGTH_SHORT).show();
                }else{
                    sendAddPrizeToServer(productPrize,currentProductInfo,currentCategoryInfo,currentQtyTypeInfo);
                    dialog.dismiss();
                }

            }
        });
        btnCanclePrize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void sendAddPrizeToServer(String productPrize, ProductInfo.Product currentProductInfo, CategoryInfo.Category currentCategoryInfo, QtyTypeInfo.Qtytype currentQtyTypeInfo) {
        String cat_id=currentCategoryInfo.getCid();
        String pro_qty_id=currentQtyTypeInfo.getProductQtyId();
        String pro_id=currentProductInfo.getProductId();
        AdminMasterRetroFitService adminMasterRetroFitService=RetroFitClient.getAdminData();
        Call<String> call=adminMasterRetroFitService.insertProductPrize(DBConst.CASE_8,
                                                                        currentProductTypeName,
                                                                        cat_id,
                                                                        pro_qty_id,
                                                                        pro_id,
                                                                        productPrize);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String msg=response.body();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setUpCategoryForPrize() {
        AdminMasterRetroFitService adminMasterRetroFitService= RetroFitClient.getAdminData();
        Call<CategoryInfo> call=adminMasterRetroFitService.selectCategoryForPrize(DBConst.CASE_5,
                                            currentProductTypeName);

        call.enqueue(new Callback<CategoryInfo>() {
            @Override
            public void onResponse(Call<CategoryInfo> call, Response<CategoryInfo> response) {
                listCategoryInfo.clear();
                CategoryInfo categoryInfo=response.body();
                listCategoryInfo=categoryInfo.getCategory();
                setCategorydata("setUpCategoryForPrize");
            }

            @Override
            public void onFailure(Call<CategoryInfo> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addnewProduct(View v) {
        final ProductInfo.Product currentProductInfo=new ProductInfo.Product();
        LayoutInflater layoutInflater=getLayoutInflater();
        View layoutView=layoutInflater.inflate(R.layout.add_product_row_item, (ViewGroup) v.findViewById(R.id.addproductitem));
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Add New Product");
        builder.setMessage("Please Fill Up Detail CareFully");
        builder.setIcon(R.mipmap.app_icon);
        builder.setView(layoutView);

        final AlertDialog dialog=builder.create();
        RadioGroup radioproductType;
        final EditText edtproducttitle,edtproductdescription;
        Button btnSubmitproduct,btnCancleproduct;


        spinnerProcategory=layoutView.findViewById(R.id.spinnercategory);
        radioproductType=layoutView.findViewById(R.id.radioproductType);
        edtproducttitle=layoutView.findViewById(R.id.edtproducttitle);
        edtproductdescription=layoutView.findViewById(R.id.edtproductdescription);
        imgnewproduct=layoutView.findViewById(R.id.imgnewproduct);
        btnCancleproduct=layoutView.findViewById(R.id.btnCancleproduct);
        btnSubmitproduct=layoutView.findViewById(R.id.btnSubmitproduct);

        currentProductTypeName=getString(R.string.egg);
        radioproductType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radio_egg){
                    currentProductTypeName=getString(R.string.egg);
                }
                if(checkedId==R.id.radio_eggless){
                    currentProductTypeName=getString(R.string.eggless);
                }
            }
        });

        setCategorydata("addnewproduct");
        imgnewproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImagesFromSource();
            }
        });
        btnSubmitproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageselected==false){
                    Toast.makeText(context, "\t Please \n Select Product Image", Toast.LENGTH_SHORT).show();
                }else if(currentCategoryInfo.getCategoryName().equals(CategoryConst.SELECT_CATEGORY_TOAST)){
                    Toast.makeText(context, "\tPlease \n Select Product Category", Toast.LENGTH_SHORT).show();
                }else if(edtproducttitle.getText().toString().equals("")){
                    Toast.makeText(context, "\t Please \n Enter Product Name", Toast.LENGTH_SHORT).show();
                }else if(edtproductdescription.getText().toString().equals("")){
                    Toast.makeText(context, "\t Please \n Enter Product Description", Toast.LENGTH_SHORT).show();
                }else {
                    currentProductInfo.setProductTitle(edtproducttitle.getText().toString());
                    currentProductInfo.setProductDescription(edtproductdescription.getText().toString());
                    sendNewProductData(dialog, currentProductInfo,currentCategoryInfo);
                }
            }
        });
        btnCancleproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageselected=false;
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void sendNewProductData(final AlertDialog dialog, ProductInfo.Product currentProductInfo, CategoryInfo.Category currentCategoryInfo) {
        String pro_name=currentProductInfo.getProductTitle();
        String pro_image=currentProductInfo.getProductImage();

        String pro_desc=currentProductInfo.getProductDescription();
        String cat_id=currentCategoryInfo.getCid();


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos); //bm is the bitmap object
            byte[] byteArrayImage = baos.toByteArray();
            encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

            AdminMasterRetroFitService adminMasterRetroFitService= RetroFitClient.getAdminData();
            Call<String> call=adminMasterRetroFitService.addNewProduct(DBConst.CASE_4,
                                                                        cat_id,
                                                                        pro_name,
                                                                        pro_image,
                                                                        currentProductTypeName,
                                                                        pro_desc,
                                                                        encodedImage);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String msg=response.body();
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    imageselected=false;
                    fetchDashBoardData();
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                    imageselected=false;
                    dialog.dismiss();
                }
            });
        }




    private void setCategorydata(final String action) {

        SpinnerCategoryAdapter adapter=new SpinnerCategoryAdapter(context,listCategoryInfo);
        spinnerProcategory.setAdapter(adapter);
        spinnerProcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String cat_id=listCategoryInfo.get(position).getCid();
                String cat_name=listCategoryInfo.get(position).getCategoryName();

                if(cat_name.equals(CategoryConst.SELECT_CATEGORY_TOAST)){
                    Toast.makeText(context, "\t Please \n select Product Category", Toast.LENGTH_SHORT).show();
                }else{
                    currentCategoryInfo.setCid(cat_id);
                    currentCategoryInfo.setCategoryName(cat_name);
                    if(action.equals("setUpCategoryForPrize")){
                        Toast.makeText(context, cat_name, Toast.LENGTH_SHORT).show();
                        fetchProductForPrize();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void fetchProductForPrize() {
        currentProductInfo=new ProductInfo.Product();
        Log.e("currentProductTypeName", "currentProductTypeName: "+currentProductTypeName );
        AdminMasterRetroFitService adminMasterRetroFitService= RetroFitClient.getAdminData();
        Call<ProductInfo> call=adminMasterRetroFitService.selectProductForPrize(DBConst.CASE_6,
                currentCategoryInfo.getCid(),
                currentProductTypeName);

        call.enqueue(new Callback<ProductInfo>() {
            @Override
            public void onResponse(Call<ProductInfo> call, Response<ProductInfo> response) {
                ProductInfo productInfo=response.body();
                listProductInfo=productInfo.getProduct();
              //  Log.e("listProductInfo", "listProductInfo: "+listProductInfo.size() );
                    setUpProductForPrize("setupPrizeForQTY");
            }

            @Override
            public void onFailure(Call<ProductInfo> call, Throwable t) {

            }
        });


    }

    private void setUpProductForPrize(final String secondAction) {
        SpinnerProductAdapter adapter=new SpinnerProductAdapter(context,listProductInfo);
        spinnerproductprize.setAdapter(adapter);
        spinnerproductprize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String productId=listProductInfo.get(position).getProductId();
                String productname=listProductInfo.get(position).getProductTitle();
                if(secondAction.equals("setupPrizeForQTY")){
                    currentProductInfo.setProductId(productId);
                    currentProductInfo.setProductTitle(productname);
                    selectQtyTypeForPrize();
                }
              
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void selectQtyTypeForPrize() {
        AdminMasterRetroFitService adminMasterRetroFitService=RetroFitClient.getAdminData();
        Call<QtyTypeInfo> call=adminMasterRetroFitService.selectPQTYForPrize(DBConst.CASE_7);

        call.enqueue(new Callback<QtyTypeInfo>() {
            @Override
            public void onResponse(Call<QtyTypeInfo> call, Response<QtyTypeInfo> response) {
                QtyTypeInfo qtyTypeInfo=response.body();
                listqtyTypeInfo=qtyTypeInfo.getQtytype();
                setUpQtyTypeData("selectQtyTypeForPrize");
            }

            @Override
            public void onFailure(Call<QtyTypeInfo> call, Throwable t) {

            }
        });
    }

    private void setUpQtyTypeData(String selectQtyTypeForPrize) {
        SpinnerQtyTypeAdapter adapter=new SpinnerQtyTypeAdapter(context,listqtyTypeInfo);
        spinnerqtyTypeprize.setAdapter(adapter);
        spinnerqtyTypeprize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String qtyid=listqtyTypeInfo.get(position).getProductQtyId();
                String qtyType=listqtyTypeInfo.get(position).getProductQtyType();

                currentQtyTypeInfo.setProductQtyId(qtyid);
                currentQtyTypeInfo.setProductQtyType(qtyType);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void fetchListOfCategory() {
        currentCategoryInfo=new CategoryInfo.Category();
        CategoryRetroFitService categoryRetroFitService= RetroFitClient.getCategory();
        Call<CategoryInfo> call=categoryRetroFitService.selectCategory(DBConst.CASE_1);
        call.enqueue(new Callback<CategoryInfo>() {
            @Override
            public void onResponse(Call<CategoryInfo> call, Response<CategoryInfo> response) {
                CategoryInfo categoryInfo=response.body();
                listCategoryInfo=categoryInfo.getCategory();
                Log.e("size", "category size: "+listCategoryInfo.size() );
            }

            @Override
            public void onFailure(Call<CategoryInfo> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
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
                imgnewproduct.setImageURI(selectedImage);

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
}
