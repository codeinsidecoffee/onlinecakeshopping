package com.mrlonewolfer.onlinecakeshopping.usermodule.Fragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mrlonewolfer.onlinecakeshopping.DataBase.SharedPrefrenceExample;
import com.mrlonewolfer.onlinecakeshopping.Model.MasterProduct;
import com.mrlonewolfer.onlinecakeshopping.Model.OrderInfo;
import com.mrlonewolfer.onlinecakeshopping.Model.PrefBean;
import com.mrlonewolfer.onlinecakeshopping.Model.ProductInfo;
import com.mrlonewolfer.onlinecakeshopping.Model.UserProduct;
import com.mrlonewolfer.onlinecakeshopping.adminmodule.Adapter.SpinnerWeightAdapter;
import com.mrlonewolfer.onlinecakeshopping.usermodule.Adapter.AllProductListAdapter;
import com.mrlonewolfer.onlinecakeshopping.DataBase.ProductRetroFitService;
import com.mrlonewolfer.onlinecakeshopping.DataBase.RetroFitClient;
import com.mrlonewolfer.onlinecakeshopping.Model.CategoryInfo;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements AllProductListAdapter.OnProductClickListner {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    List<ProductInfo.Product> listProductInfo;
    ProductInfo.Product currentProductInfo;
    CategoryInfo.Category category;
    List<UserProduct.ProductList> listUserProduct;
    List<UserProduct.DetailPrice> listpriceInfo;
    SharedPrefrenceExample sharedPrefrenceExample;
    TextView txtreciveDate,txtreciveTime;
    PrefBean prefBean;
    public static List<OrderInfo.OrderDetail> listOrderCart =new ArrayList<>();
    public static List<OrderInfo.ProductDetail> listProductCart=new ArrayList<>();
    OrderInfo orderInfo;

    public HomeFragment() {
        // Required empty public
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        context=getActivity();
             recyclerView = view.findViewById(R.id.homeRecyclerView);
            layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);


        Bundle bundle=getArguments();
        if(bundle!=null){
            category=bundle.getParcelable("category");
            fetchCategoryProduct();
        }
        else{
            fetchAllProduct();
        }

        sharedPrefrenceExample = new SharedPrefrenceExample(DBConst.FILE_NAME,context);
        prefBean=sharedPrefrenceExample.getSharedPreferences();
        return view;
    }

    private void fetchCategoryProduct() {
        ProductRetroFitService productRetroFitService= RetroFitClient.getProduct();
        Call<ProductInfo> call=productRetroFitService.selectCategoryProduct(DBConst.CASE_1,
                category.getCid());
        call.enqueue(new Callback<ProductInfo>() {
            @Override
            public void onResponse(Call<ProductInfo> call, Response<ProductInfo> response) {
                ProductInfo productInfo=response.body();
                listProductInfo=productInfo.getProduct();
                Log.e("sdf", "onResponse: "+listProductInfo.size() );
                setupProductAdapter();
            }

            @Override
            public void onFailure(Call<ProductInfo> call, Throwable t) {

            }
        });
    }

    private void fetchAllProduct() {
        ProductRetroFitService productRetroFitService= RetroFitClient.getProduct();
        Call<UserProduct> call=productRetroFitService.selectAllProductList(DBConst.CASE_3);
        call.enqueue(new Callback<UserProduct>() {
            @Override
            public void onResponse(Call<UserProduct> call, Response<UserProduct> response) {
                UserProduct userProduct=response.body();
                listUserProduct=userProduct.getProductList();

                setupProductAdapter();
            }

            @Override
            public void onFailure(Call<UserProduct> call, Throwable t) {

            }
        });
    }

    private void setupProductAdapter() {
        AllProductListAdapter adapter=new AllProductListAdapter(context,listUserProduct,this);
        recyclerView.setAdapter(adapter);

    }




    private void ShowDetailView(View v, final UserProduct.ProductList currentproductlist) {
        final LayoutInflater layoutInflater=getLayoutInflater();

        View layoutView=layoutInflater.inflate(R.layout.detail_product_item,
                (ViewGroup) v.findViewById(R.id.viewsingleproduct));

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setView(layoutView);

        final AlertDialog dialog=builder.create();

        Button btnGoBack,btnOrder;
        final TextView txtRate,txtProductTitle,txtProductDesc,txtCatName,txtCakeType;
        ImageView imgProduct;
        final EditText edtProductQty;
       final List<MasterProduct.Cakerate> listCakeRate = currentproductlist.getDetailPrice();

       String pTitle=currentproductlist.getProductTitle();
       String pImagePath=currentproductlist.getProductImage();
       String pDesc=currentproductlist.getProductDescription();
       String pType=currentproductlist.getProductTypeName();
       String cName=currentproductlist.getCategoryName();

        btnGoBack=layoutView.findViewById(R.id.btnGoBack);

        final Spinner spinnerprotype=layoutView.findViewById(R.id.spinnerprotype);
        txtreciveDate=layoutView.findViewById(R.id.txtreciveDate);
        txtreciveTime=layoutView.findViewById(R.id.txtreciveTime);
        edtProductQty=layoutView.findViewById(R.id.edtProductQty);
        txtRate=layoutView.findViewById(R.id.txtRate);
        txtCakeType=layoutView.findViewById(R.id.txtCakeType);
        txtCatName=layoutView.findViewById(R.id.txtCatName);
        txtProductTitle=layoutView.findViewById(R.id.txtProductName);
        txtProductDesc=layoutView.findViewById(R.id.txtProductDesc);
        btnOrder=layoutView.findViewById(R.id.btnOrder);
        imgProduct=layoutView.findViewById(R.id.imgnewproduct);
        String product_image_path= DBConst.IMAGE_URL+ "/images/product/"+pImagePath;
        Picasso.with(context).load(product_image_path).placeholder(R.mipmap.app_icon).into(imgProduct);
        txtCakeType.setText(pType);
        txtProductTitle.setText(pTitle);
        txtProductDesc.setText(pDesc);
        txtCatName.setText(cName);

        txtreciveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectReceiveDate("SelectDate");
            }
        });
        txtreciveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectReceiveDate("SelectTime");
            }
        });
        final String[] currentweight = new String[1];
        final double[] currentprice = new double[1];

        final SpinnerWeightAdapter adapter = new SpinnerWeightAdapter(context, listCakeRate);
        spinnerprotype.setAdapter(adapter);
        spinnerprotype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                double cprice = Double.parseDouble(listCakeRate.get(position).getPrate());
                currentweight[0] =listCakeRate.get(position).getPweight();
                currentprice[0] =cprice;

                txtRate.setText(cprice+" Rs /-");

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
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String product_qty_type= currentweight[0];

                        OrderInfo.OrderDetail currentorderDetail = new OrderInfo.OrderDetail();
                        OrderInfo.ProductDetail currentProductDetail = new OrderInfo.ProductDetail();
                        double total_unit = Double.parseDouble(edtProductQty.getText().toString());
                        double unit_rate = currentprice[0];

                        double total_product = 1;
                        String pro_title = txtProductTitle.getText().toString();

                        Double purchase_amount = total_unit * unit_rate;
                        String receive_date = txtreciveDate.getText().toString();
                        String receive_time = txtreciveTime.getText().toString();
                        double order_amount = total_product * total_unit * unit_rate;

                        currentProductDetail.setCategoryName(currentproductlist.getCategoryName());
                        currentProductDetail.setCid(currentproductlist.getCid());
                        currentProductDetail.setProductId(currentproductlist.getProductId());
                        currentProductDetail.setProductImage(currentproductlist.getProductImage());
                        currentProductDetail.setProductQtyType(product_qty_type);
                        currentProductDetail.setUnitRate(unit_rate + "");
                        currentProductDetail.setTotalUnit(total_unit + "");
                        currentProductDetail.setPurchaseAmount(purchase_amount + "");
                        currentProductDetail.setReceiveDate(receive_date);
                        currentProductDetail.setReceiveTime(receive_time);
                        currentProductDetail.setProductTitle(pro_title);
                        currentProductDetail.setProductTypeName(currentproductlist.getProductTypeName());


                        currentorderDetail.setOrderAmount(order_amount + "");
                        currentorderDetail.setTotalProduct(total_product + "");
                Log.e("prefbean", "onClick: "+prefBean.getUserid() );
                        currentorderDetail.setUserId(prefBean.getUserid());
                        listProductCart.add(currentProductDetail);

                        currentorderDetail.setProductDetails(listProductCart);
                        listOrderCart.add(currentorderDetail);




                    Fragment fragment = new AddToCartFragment();
                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainContainer, fragment).commit();
                    dialog.dismiss();

                }

        });

        dialog.show();
    }

    private void selectReceiveDate(String action) {
        Calendar calendar=Calendar.getInstance();
        final int cYear=calendar.get(Calendar.YEAR);
        final int cMonth=calendar.get(Calendar.MONTH);
        final int cDay=calendar.get(Calendar.DAY_OF_MONTH);
        final int cHour=calendar.get(Calendar.HOUR);
        final int cMinute=calendar.get(Calendar.MINUTE);


        if(action.equals("SelectDate")){
            DatePickerDialog datePickerDialog=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    txtreciveDate.setText(year+"-"+month+"-"+dayOfMonth);
                }
            },cYear,cMonth+1,cDay);
            datePickerDialog.show();
        }
        if(action.equals("SelectTime")){
            TimePickerDialog timePickerDialog=new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    txtreciveTime.setText(hourOfDay+":"+minute);
                }
            },cHour,cMinute,false);
            timePickerDialog.show();
        }
    }


    @Override
    public void OnProductClick(View v, UserProduct.ProductList currentproductlist, String action) {
        if(action.equals("imgProduct")){
            Toast.makeText(context, " You Will See Product \n Review & Comment Here", Toast.LENGTH_SHORT).show();

        }
        if(action.equals("QuickView")){
            ShowDetailView(v,currentproductlist);
        }
        if(action.equals("addtocart")){
            String pstatus=currentproductlist.getProductStatus();
            if(pstatus.equals("1")){
                ShowDetailView(v,currentproductlist);
            }else{
                Toast.makeText(context, "Sorry Product is UnAvaliable Right Now", Toast.LENGTH_SHORT).show();
            }

        }
    }


}
