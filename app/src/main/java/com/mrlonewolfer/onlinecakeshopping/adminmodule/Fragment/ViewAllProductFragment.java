package com.mrlonewolfer.onlinecakeshopping.adminmodule.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
import com.mrlonewolfer.onlinecakeshopping.DataBase.ProductRetroFitService;
import com.mrlonewolfer.onlinecakeshopping.DataBase.RetroFitClient;
import com.mrlonewolfer.onlinecakeshopping.Model.CategoryInfo;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.MasterProduct;
import com.mrlonewolfer.onlinecakeshopping.Model.ProductInfo;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.mrlonewolfer.onlinecakeshopping.adminmodule.Adapter.SpinnerCategoryAdapter;
import com.mrlonewolfer.onlinecakeshopping.adminmodule.Adapter.SpinnerQtyTypeAdapter;
import com.mrlonewolfer.onlinecakeshopping.adminmodule.Adapter.SpinnerWeightAdapter;
import com.mrlonewolfer.onlinecakeshopping.adminmodule.Adapter.ViewAllProductAdapter;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewAllProductFragment extends Fragment implements ViewAllProductAdapter.OnProductClickListner {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    private List<ProductInfo.Product> listProductInfo;
    Spinner spinnerProcategory;
    ImageView imgnewproduct;
    HashMap<String, String> map;
    List<MasterProduct.Cakerate> listCakeRate;
    Spinner spinnerprotype,spinnerprocat;
     CategoryInfo.Category currentCategoryInfo;
     List<CategoryInfo.Category> listCategoryInfo;
     MasterProduct sendMasterProduct=new MasterProduct();
     MasterProduct.Cakerate sendCakeRate;


    public ViewAllProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_all_product, container, false);
        context=getActivity();
        fetchListofProduct();
        recyclerView=view.findViewById(R.id.recyclerproductview);
        layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);


        return view;
    }

    private void fetchListofProduct() {
        ProductRetroFitService productRetroFitService= RetroFitClient.getProduct();
        Call<ProductInfo> call=productRetroFitService.selectAllProduct(DBConst.CASE_2);
        call.enqueue(new Callback<ProductInfo>() {
            @Override
            public void onResponse(Call<ProductInfo> call, Response<ProductInfo> response) {
                ProductInfo productInfo=response.body();
                listProductInfo=productInfo.getProduct();
                setupViewProductAdapter();
            }

            @Override
            public void onFailure(Call<ProductInfo> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupViewProductAdapter() {
        ViewAllProductAdapter adapter=new ViewAllProductAdapter(context,listProductInfo,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnProductClick(View v, ProductInfo.Product product, String action) {
        if(action.equals("Productimage")){
            fetchmasterProductDetails(v,product);
        }
        if(action.equals("ProductStatus")){
            ChangeProductStatus(product);
        }
        if(action.equals("ManageDetails")){
            changeProductDetails(v,product);
        }
        if(action.equals("DeleteProduct")){
            DeleteCurrentProduct(v,product);
            fetchListofProduct();
        }


    }

    private void DeleteCurrentProduct(View v, ProductInfo.Product product) {

        AdminMasterRetroFitService retroFitService=RetroFitClient.getAdminData();
        Call<String> call=retroFitService.deleteCurrentProduct(DBConst.CASE_15,
                                                                product.getProductId());
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

    private void fetchmasterProductDetails(final View v, final ProductInfo.Product product) {

        AdminMasterRetroFitService retroFitService=RetroFitClient.getAdminData();
        Call<MasterProduct> call=retroFitService.selectSingleProductDetail(DBConst.CASE_13,
                                                                    product.getProductId());
        call.enqueue(new Callback<MasterProduct>() {
            @Override
            public void onResponse(Call<MasterProduct> call, Response<MasterProduct> response) {
                MasterProduct masterProduct=response.body();
                    preapareProductRate(masterProduct);

                LayoutInflater layoutInflater=getLayoutInflater();
                View layoutView=layoutInflater.inflate(R.layout.view_master_product_detail,
                        (ViewGroup) v.findViewById(R.id.viewsingleproduct));

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setView(layoutView);

                final AlertDialog dialog=builder.create();

                Button btnGoBack;
                final TextView txtRate,txtProductTitle,txtProductDesc,txtCatName,txtCakeType;
                ImageView imgProduct;


                btnGoBack=layoutView.findViewById(R.id.btnGoBack);

                spinnerprotype=layoutView.findViewById(R.id.spinnerprotype);
                txtRate=layoutView.findViewById(R.id.txtRate);
                txtCakeType=layoutView.findViewById(R.id.txtCakeType);
                txtCatName=layoutView.findViewById(R.id.txtCatName);
                txtProductTitle=layoutView.findViewById(R.id.txtProductName);
                txtProductDesc=layoutView.findViewById(R.id.txtProductDesc);
                imgProduct=layoutView.findViewById(R.id.imgnewproduct);
                String product_image_path= DBConst.IMAGE_URL+ "/images/product/"+masterProduct.getProductImage();
                Picasso.with(context).load(product_image_path).placeholder(R.mipmap.app_icon).into(imgProduct);
                txtCakeType.setText(masterProduct.getProductTypeName());
                txtProductTitle.setText(masterProduct.getProductTitle());
                txtProductDesc.setText(masterProduct.getProductDescription());
                txtCatName.setText(masterProduct.getCategoryName());
                SpinnerWeightAdapter adapter=new SpinnerWeightAdapter(context,listCakeRate);
                spinnerprotype.setAdapter(adapter);
                spinnerprotype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    txtRate.setText(listCakeRate.get(position).getPrate()+" Rs /-");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                btnGoBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // Toast.makeText(context, "You Click On Cancle Product", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });


                dialog.show();
            }

            @Override
            public void onFailure(Call<MasterProduct> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void preapareProductRate(MasterProduct masterProduct) {
            int mapSize = masterProduct.getCakerate().size();
            listCakeRate = masterProduct.getCakerate();
            map = new HashMap<>();
            for (int i = 0; i < mapSize; i++) {
                String pro_type = listCakeRate.get(i).getPweight();
                String pro_rate = listCakeRate.get(i).getPrate();
                Log.e("pro_rate", "pro_type " + pro_rate + "" + pro_type);
                map.put(pro_type, pro_rate);
            }
            Log.e("map", "preapareProductRate: " + map.size());


    }


    private void changeProductDetails(final View v, ProductInfo.Product product) {
        AdminMasterRetroFitService retroFitService=RetroFitClient.getAdminData();
        Call<MasterProduct> call=retroFitService.selectSingleProductDetail(DBConst.CASE_13,
                product.getProductId());

        call.enqueue(new Callback<MasterProduct>() {
            @Override
            public void onResponse(Call<MasterProduct> call, Response<MasterProduct> response) {
                final MasterProduct masterProduct=response.body();
         //       Log.e("listcakerateSize", "listcakerateSize: "+masterProduct.getCakerate().size() );
                    preapareProductRate(masterProduct);

                LayoutInflater layoutInflater=getLayoutInflater();
                View layoutView=layoutInflater.inflate(R.layout.manage_master_product_details,
                        (ViewGroup) v.findViewById(R.id.managesingleproduct));

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setView(layoutView);

                final AlertDialog dialog=builder.create();
                final EditText edtRate,edtProductName,edtProductDesc;
                ImageView imgnewproduct;
                RadioGroup radioproductType;
                Button btnCancle,btnSubmit;
                sendCakeRate=new MasterProduct.Cakerate();
                spinnerprotype=layoutView.findViewById(R.id.spinnerprotype);
                spinnerprocat=layoutView.findViewById(R.id.spinnerprocat);
                edtRate=layoutView.findViewById(R.id.edtRate);
                btnCancle=layoutView.findViewById(R.id.btnCancle);
                btnSubmit=layoutView.findViewById(R.id.btnSubmit);
                radioproductType=layoutView.findViewById(R.id.radioproductType);
                edtProductName=layoutView.findViewById(R.id.edtProductName);
                edtProductDesc=layoutView.findViewById(R.id.edtProductDesc);
                imgnewproduct=layoutView.findViewById(R.id.imgnewproduct);

                fetchListOfCategory(masterProduct);

                SpinnerWeightAdapter weight_adapter=new SpinnerWeightAdapter(context,listCakeRate);
                spinnerprotype.setAdapter(weight_adapter);

                edtProductDesc.setText(masterProduct.getProductDescription());
                edtProductName.setText(masterProduct.getProductTitle());
                String pro_image_path = DBConst.IMAGE_URL + "/images/product/" + masterProduct.getProductImage();
                Picasso.with(context).load(pro_image_path).placeholder(R.mipmap.app_icon).into(imgnewproduct);
                String caketype=masterProduct.getProductTypeName();
                sendMasterProduct.setProductTypeName(caketype);
                sendMasterProduct.setCid(masterProduct.getCid());
                sendMasterProduct.setCategoryName(masterProduct.getCategoryName());
                if(caketype.equals("Egg")){
                    radioproductType.check(R.id.radio_egg);
                }else{
                    radioproductType.check(R.id.radio_eggless);

                }
                spinnerprocat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                      String currentCategory= listCategoryInfo.get(position).getCategoryName();
                      String currentCid=listCategoryInfo.get(position).getCid();
                        sendMasterProduct.setCid(currentCid);
                        sendMasterProduct.setCategoryName(currentCategory);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                radioproductType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(checkedId==R.id.radio_egg){
                            sendMasterProduct.setProductTypeName(getString(R.string.egg));

                        }
                        if(checkedId==R.id.radio_eggless){
                            sendMasterProduct.setProductTypeName(getString(R.string.eggless));
                        }
                    }
                });
                spinnerprotype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        edtRate.setText(listCakeRate.get(position).getPrate());
                        sendCakeRate.setPweight(listCakeRate.get(position).getPweight());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String currentrate=edtRate.getText().toString();
                        String currentTitle=edtProductName.getText().toString();
                        String currentDesc=edtProductDesc.getText().toString();
                        String productId=masterProduct.getProductId();
                        sendMasterProduct.setProductId(productId);
                        sendMasterProduct.setProductTitle(currentTitle);
                        sendMasterProduct.setProductDescription(currentDesc);
                        sendCakeRate.setPrate(currentrate);

                        updateProductAllDetails();
                        Log.e("CakeRate", "CakeRate "+sendCakeRate );
                        dialog.dismiss();
                    }
                });
                btnCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }

            @Override
            public void onFailure(Call<MasterProduct> call, Throwable t) {

            }
        });
    }

    private void updateProductAllDetails() {
        AdminMasterRetroFitService retroFitService=RetroFitClient.getAdminData();
        Call<String> call=retroFitService.updateProductAllDetails(DBConst.CASE_14,
                                                        sendMasterProduct.getProductId(),
                                                        sendMasterProduct.getProductTitle(),
                                                        sendMasterProduct.getProductDescription(),
                                                        sendMasterProduct.getCid(),
                                                        sendCakeRate.getPrate(),
                                                        sendCakeRate.getPweight(),
                                                        sendMasterProduct.getProductTypeName());
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

    private void fetchListOfCategory(final MasterProduct masterProduct) {
        currentCategoryInfo=new CategoryInfo.Category();
        CategoryRetroFitService categoryRetroFitService= RetroFitClient.getCategory();
        Call<CategoryInfo> call=categoryRetroFitService.selectCategory(DBConst.CASE_1);
        call.enqueue(new Callback<CategoryInfo>() {
            @Override
            public void onResponse(Call<CategoryInfo> call, Response<CategoryInfo> response) {
                CategoryInfo categoryInfo=response.body();
                listCategoryInfo=categoryInfo.getCategory();
                Log.e("size", "category size: "+listCategoryInfo.size() );

                SpinnerCategoryAdapter cat_adapter=new SpinnerCategoryAdapter(context,listCategoryInfo);
                spinnerprocat.setAdapter(cat_adapter);
                int id=Integer.parseInt(masterProduct.getCid())-1;
                spinnerprocat.setSelection(id);

            }

            @Override
            public void onFailure(Call<CategoryInfo> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void ChangeProductStatus(ProductInfo.Product product) {
        String pro_status=product.getProductStatus();
        if(pro_status.equals("1")){
            product.setProductStatus("0");
            Toast.makeText(context, "Product Is No Longer Available", Toast.LENGTH_SHORT).show();
        }else{
            product.setProductStatus("1");
            Toast.makeText(context, "Product is Avaliable", Toast.LENGTH_SHORT).show();
        }

        AdminMasterRetroFitService retroFitService=RetroFitClient.getAdminData();
        Call<String> call=retroFitService.updateProductStatus(DBConst.CASE_12,
                product.getProductId(),
                product.getProductStatus());
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
}
