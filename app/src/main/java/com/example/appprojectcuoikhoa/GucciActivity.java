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
import com.example.appprojectcuoikhoa.databinding.ActivityGucciBinding;

import java.util.ArrayList;
import java.util.List;

import adapter.Adidas_Adapter;
import adapter.Gucci_Adapter;
import model.Adidas;
import model.DataShop;
import model.Gucci;
import retrofit.ApiInterface;
import retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GucciActivity extends AppCompatActivity {
    ActivityGucciBinding binding;
    ApiInterface apiInterface;
    List<DataShop> dataShopArrayList;
    List<Gucci> gucciList;
    Gucci_Adapter gucciAdapter;
    String nameGucci , imgGucci;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_gucci);
        dataShopArrayList = new ArrayList<>();
        gucciList = new ArrayList<>();
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<DataShop>> call = apiInterface.getAllData();
        call.enqueue(new Callback<List<DataShop>>() {
            @Override
            public void onResponse(Call<List<DataShop>> call, Response<List<DataShop>> response) {
                dataShopArrayList = response.body();
                getGucciData(dataShopArrayList.get(0).getGucciList());
            }

            @Override
            public void onFailure(Call<List<DataShop>> call, Throwable t) {
                Toast.makeText(GucciActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.imgGucciBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GucciActivity.this,HomeActivity.class));
            }
        });
//        Intent intent = getIntent();
//        nameGucci = intent.getStringExtra("nameGucci");
//        imgGucci  = intent.getStringExtra("imgGucci");
//        Glide.with(this).load(imgGucci).into(binding.imgTitleGucci);
//        binding.txtTileGucci.setText(nameGucci);
    }
    private void getGucciData(List<Gucci> gucciList){
        gucciAdapter =  new Gucci_Adapter(this,gucciList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.recylerGucci.setLayoutManager(layoutManager);
        binding.recylerGucci.setAdapter(gucciAdapter);
    }
}