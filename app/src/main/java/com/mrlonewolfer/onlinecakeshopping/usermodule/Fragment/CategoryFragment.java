package com.mrlonewolfer.onlinecakeshopping.usermodule.Fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrlonewolfer.onlinecakeshopping.usermodule.Adapter.CategoryListRecyclerViewAapter;
import com.mrlonewolfer.onlinecakeshopping.DataBase.CategoryRetroFitService;
import com.mrlonewolfer.onlinecakeshopping.DataBase.RetroFitClient;
import com.mrlonewolfer.onlinecakeshopping.Model.CategoryInfo;
import com.mrlonewolfer.onlinecakeshopping.Model.DBConst;
import com.mrlonewolfer.onlinecakeshopping.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements CategoryListRecyclerViewAapter.OnCategoryClickListener {

RecyclerView recyclerView;
RecyclerView.LayoutManager layoutManager;
Context context;
List<CategoryInfo.Category> listCategoryInfo;
CategoryInfo.Category currentCategoryInfo;
String cat_image,cat_name,cat_id;


    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_category, container, false);
        context=getActivity();
        recyclerView=view.findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchListOfCategory();
    }

    private void fetchListOfCategory() {
        CategoryRetroFitService categoryRetroFitService= RetroFitClient.getCategory();
        Call<CategoryInfo> call=categoryRetroFitService.selectCategory(DBConst.CASE_1);
        call.enqueue(new Callback<CategoryInfo>() {
            @Override
            public void onResponse(Call<CategoryInfo> call, Response<CategoryInfo> response) {
                CategoryInfo categoryInfo=response.body();
                listCategoryInfo=categoryInfo.getCategory();
                setUpCategoryAdapter();

            }

            @Override
            public void onFailure(Call<CategoryInfo> call, Throwable t) {

            }
        });

    }

    private void setUpCategoryAdapter() {
        listCategoryInfo.remove(0);
        CategoryListRecyclerViewAapter adapter=new CategoryListRecyclerViewAapter(context,listCategoryInfo,this);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onCategoryClick(CategoryInfo.Category mycategory) {
        Bundle bundle=new Bundle();
        bundle.putParcelable("category",mycategory);
        Fragment fragment= new HomeFragment();

        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainContainer,fragment).commit();

    }


}
