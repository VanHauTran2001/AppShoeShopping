package com.example.appprojectcuoikhoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appprojectcuoikhoa.databinding.ActivityVansBinding;

import java.util.ArrayList;
import java.util.List;

import adapter.Puma_Adapter;
import adapter.Vans_Adapter;
import model.DataShop;
import model.Puma;
import model.Vans;
import retrofit.ApiInterface;
import retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VansActivity extends AppCompatActivity {
    ActivityVansBinding binding;
    ApiInterface apiInterface;
    List<DataShop> dataShopArrayList;
    List<Vans> vansList;
    Vans_Adapter vansAdapter;
    String nameVans , imgVans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_vans);
        vansList = new ArrayList<>();
        dataShopArrayList = new ArrayList<>();
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<DataShop>> call = apiInterface.getAllData();
        call.enqueue(new Callback<List<DataShop>>() {
            @Override
            public void onResponse(Call<List<DataShop>> call, Response<List<DataShop>> response) {
                dataShopArrayList = response.body();
                getVansData(dataShopArrayList.get(0).getVansList());
            }

            @Override
            public void onFailure(Call<List<DataShop>> call, Throwable t) {
                Toast.makeText(VansActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.imgVansBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VansActivity.this,HomeActivity.class));
            }
        });
        Intent intent = getIntent();
        nameVans = intent.getStringExtra("nameVans");
        imgVans  = intent.getStringExtra("imgVans");
        Glide.with(this).load(imgVans).into(binding.imgTitleVans);
        binding.txtTileVans.setText(nameVans);
    }
    private void getVansData(List<Vans> vansList){
        vansAdapter = new Vans_Adapter(this,vansList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.recylerVans.setLayoutManager(layoutManager);
        binding.recylerVans.setAdapter(vansAdapter);
    }
}