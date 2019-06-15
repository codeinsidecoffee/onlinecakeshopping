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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mrlonewolfer.onlinecakeshopping.DataBase.OrderRetroFitService;
import com.mrlonewolfer.onlinecakeshopping.DataBase.RetroFitClient;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.Model.OrderInfo;
import com.mrlonewolfer.onlinecakeshopping.R;
import com.mrlonewolfer.onlinecakeshopping.usermodule.Adapter.SpinnerCartAdapter;
import com.mrlonewolfer.onlinecakeshopping.usermodule.Adapter.SpinnerFinalCartAdapter;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import static com.mrlonewolfer.onlinecakeshopping.usermodule.Fragment.HomeFragment.listOrderCart;
import static com.mrlonewolfer.onlinecakeshopping.usermodule.Fragment.HomeFragment.listProductCart;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddToCartFragment extends Fragment implements SpinnerCartAdapter.OnCartClickListner,SpinnerFinalCartAdapter.OnFinalCartClickListenr {



    RecyclerView cartRecyclerView,totlaOrderRecyclerView;
    RecyclerView.LayoutManager layoutManager,finalLayoutManger;
    Context context;
    TextView txtreciveDate,txtreciveTime;
    Button btnshopcontinue;



    public AddToCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_to_cart, container, false);
        context=getActivity();
        cartRecyclerView=view.findViewById(R.id.cartRecyclerView);
        totlaOrderRecyclerView=view.findViewById(R.id.totlaOrderRecyclerView);
        btnshopcontinue=view.findViewById(R.id.btnshopcontinue);
        layoutManager=new LinearLayoutManager(context);
        finalLayoutManger=new LinearLayoutManager(context);
        cartRecyclerView.setLayoutManager(layoutManager);
        totlaOrderRecyclerView.setLayoutManager(finalLayoutManger);
        btnshopcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainContainer, fragment).commit();
            }
        });

        setUpCartData();


        return view;
    }



    private void PlaceOrderOfSelectedProduct() {
        listOrderCart.get(0).setTotalProduct(listProductCart.size()+"");
//        Toast.makeText(context, "Total product is: "+listOrderCart.get(0).getProductDetails().toString()+
//                "\n Total Purchase is: "+listOrderCart.get(0).getOrderAmount(), Toast.LENGTH_SHORT).show();
        Gson gson = new Gson();
        String json = gson.toJson(listOrderCart.get(0));
        Log.e("addtocart", "PlaceOrderOfSelectedProduct: "+json );
        OrderRetroFitService retroFitService=RetroFitClient.getOrderData();
        Call<String> call=retroFitService.placeNewOrder(DBConst.CASE_3,json);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String msg=response.body();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                listOrderCart.clear();

                Fragment fragment=new AddToCartFragment();
                getFragmentManager().beginTransaction().replace(R.id.mainContainer,fragment).commit();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
              Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setUpCartData() {

        if(listOrderCart.size()>0) {
            btnshopcontinue.setVisibility(View.GONE);
            cartRecyclerView.setVisibility(View.VISIBLE);
            totlaOrderRecyclerView.setVisibility(View.VISIBLE);
            SpinnerCartAdapter adapter = new SpinnerCartAdapter(context, listProductCart, this);
            cartRecyclerView.setAdapter(adapter);
            listOrderCart.get(0).setTotalProduct(listProductCart.size()+"");

            SpinnerFinalCartAdapter finalCartAdapter=new SpinnerFinalCartAdapter(context,listOrderCart,this);
            totlaOrderRecyclerView.setAdapter(finalCartAdapter);
        }else{
            cartRecyclerView.setVisibility(View.GONE);
            totlaOrderRecyclerView.setVisibility(View.GONE);
        }


    }

    @Override
    public void onCartClick(View v, OrderInfo.ProductDetail productDetail, String action) {
        if(action.equals("EditCart")){
            editCurrentCartProduct(v,productDetail);
            setUpCartData();
        }
        if(action.equals("DeleteCart")){
            listProductCart.remove(productDetail);
            setUpCartData();
        }
    }

    private void editCurrentCartProduct(View v, final OrderInfo.ProductDetail productDetail) {
        final LayoutInflater layoutInflater=getLayoutInflater();

        View layoutView=layoutInflater.inflate(R.layout.edit_cart_item,
                (ViewGroup) v.findViewById(R.id.editsingleproduct));

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setView(layoutView);

        final AlertDialog dialog=builder.create();

        Button btnGoBack,btnOrder;
        final TextView txtRate,txtProductTitle,txtProductDesc,txtCatName,txtCakeType,txtproductweight;
        ImageView imgProduct;
        final EditText edtProductQty;

        String pTitle=productDetail.getProductTitle();
        String pImagePath=productDetail.getProductImage();
        String pType=productDetail.getProductTypeName();
        String cName=productDetail.getCategoryName();
        String prate=productDetail.getPurchaseAmount();
        String pweight=productDetail.getProductQtyType();
        btnGoBack=layoutView.findViewById(R.id.btnGoBack);
        String pid=productDetail.getProductId();
        txtreciveDate=layoutView.findViewById(R.id.txtreciveDate);
        txtreciveTime=layoutView.findViewById(R.id.txtreciveTime);
        edtProductQty=layoutView.findViewById(R.id.edtProductQty);
        txtproductweight=layoutView.findViewById(R.id.txtproductweight);
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
        txtProductDesc.setVisibility(View.GONE);
        txtCatName.setText(cName);
        Log.e("Addtocart", "editCurrentCartProduct: "+productDetail.getProductQtyId() );
        txtRate.setText(productDetail.getPurchaseAmount());
        edtProductQty.setText(productDetail.getTotalUnit());
        txtreciveDate.setText(productDetail.getReceiveDate());
        txtreciveTime.setText(productDetail.getReceiveTime());


        txtproductweight.setText(pweight);

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

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                double total_unit= Double.parseDouble(edtProductQty.getText().toString());
                double unit_rate= Double.parseDouble(productDetail.getUnitRate());
                Double purchase_amount=total_unit*unit_rate;
                String receive_date=txtreciveDate.getText().toString();
                String receive_time=txtreciveTime.getText().toString();

                txtRate.setText(purchase_amount+" RS /- ");
                productDetail.setPurchaseAmount(purchase_amount+"");
                productDetail.setTotalUnit(total_unit+"");
                productDetail.setReceiveTime(receive_time);
                productDetail.setReceiveDate(receive_date);
                setUpCartData();
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
    public void OnFinalCartClick(View v, List<OrderInfo.OrderDetail> listOrderCart, String action) {
        if(action.equals("PlaceOrder")){
      //      Toast.makeText(context, "You click on Placeorder", Toast.LENGTH_SHORT).show();
            PlaceOrderOfSelectedProduct();
        }
        if(action.equals("ShopContinue")){
       //     Toast.makeText(context, "You click on Shop COntinue", Toast.LENGTH_SHORT).show();
            Fragment fragment=new HomeFragment();
            getFragmentManager().beginTransaction().replace(R.id.mainContainer,fragment).commit();
        }
    }
}
