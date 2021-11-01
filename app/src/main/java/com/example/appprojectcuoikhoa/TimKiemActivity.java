package com.example.appprojectcuoikhoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appprojectcuoikhoa.databinding.ActivityTimKiemBinding;

import java.util.ArrayList;
import java.util.List;

import adapter.Filter_Adapter;
import adapter.Shoe_Adapter;
import model.DataShop;
import model.Shoe;
import retrofit.ApiInterface;
import retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimKiemActivity extends AppCompatActivity {
    ActivityTimKiemBinding binding;
    Filter_Adapter filterAdapter;
    List<DataShop> dataShopArrayList;
    List<Shoe> listShoe;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_tim_kiem);
        listShoe = new ArrayList<>();
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<DataShop>> call = apiInterface.getAllData();
        call.enqueue(new Callback<List<DataShop>>() {
            @Override
            public void onResponse(Call<List<DataShop>> call, Response<List<DataShop>> response) {
                dataShopArrayList = response.body();
                getShoeData(dataShopArrayList.get(0).getShoeList());
            }

            @Override
            public void onFailure(Call<List<DataShop>> call, Throwable t) {
                Toast.makeText(TimKiemActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        OnCliclSearch();
        binding.txtCloseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),HomeActivity.class));
            }
        });

    }

    private void OnCliclSearch() {
//        binding.edtSearch.setImeActionLabel("Filter",1);
//        binding.edtSearch.setPrivateImeOptions("actionUnspecified");
//        binding.edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId==1 || actionId == EditorInfo.IME_NULL){
//                    filterAdapter.getFilter().filter(v.getText().toString());
//                    return true;
//                }else {
//                    Toast.makeText(TimKiemActivity.this, "Không tìm thấy sản phẩm !", Toast.LENGTH_SHORT).show();
//                }
//                return false;
//            }
//        });
        binding.edtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                filterAdapter.getFilter().filter(s.toString());
            }
        });
    }

    private void getShoeData(List<Shoe> listShoe){
        filterAdapter = new Filter_Adapter(this,listShoe);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recylerSearch.setLayoutManager(linearLayoutManager);
        binding.recylerSearch.setAdapter(filterAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        binding.recylerSearch.addItemDecoration(itemDecoration);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu_search,menu);
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchView = (SearchView) menu.findItem(R.id.action_search)
//                .getActionView();
//        searchView.setSearchableInfo(searchManager
//                .getSearchableInfo(getComponentName()));
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//        return true;
//    }
}