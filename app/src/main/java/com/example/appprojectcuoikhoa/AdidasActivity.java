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
import com.example.appprojectcuoikhoa.databinding.ActivityAdidasBinding;

import java.util.ArrayList;
import java.util.List;

import adapter.Adidas_Adapter;
import adapter.Nike_Adapter;
import model.Adidas;
import model.DataShop;
import model.Nike;
import retrofit.ApiInterface;
import retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdidasActivity extends AppCompatActivity {
    ActivityAdidasBinding binding;
    ApiInterface apiInterface;
    List<DataShop> dataShopArrayList;
    List<Adidas> adidasList;
    Adidas_Adapter adidasAdapter;
    String nameAdidas , imgAdidas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_adidas);
        adidasList = new ArrayList<>();
        dataShopArrayList = new ArrayList<>();
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<DataShop>> call = apiInterface.getAllData();
        call.enqueue(new Callback<List<DataShop>>() {
            @Override
            public void onResponse(Call<List<DataShop>> call, Response<List<DataShop>> response) {
                dataShopArrayList = response.body();
                getAdidasData(dataShopArrayList.get(0).getAdidasList());
            }

            @Override
            public void onFailure(Call<List<DataShop>> call, Throwable t) {
                Toast.makeText(AdidasActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.imgAdidasBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdidasActivity.this,HomeActivity.class));
            }
        });
        Intent intent = getIntent();
        nameAdidas = intent.getStringExtra("nameAdidas");
        imgAdidas  = intent.getStringExtra("imgAdidas");
        Glide.with(this).load(imgAdidas).into(binding.imgTitleAdidas);
        binding.txtTileAdidas.setText(nameAdidas);
    }
    private void getAdidasData(List<Adidas> adidasList){
        adidasAdapter = new Adidas_Adapter(this,adidasList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.recylerAdidas.setLayoutManager(layoutManager);
        binding.recylerAdidas.setAdapter(adidasAdapter);
        adidasAdapter.notifyDataSetChanged();
    }
}