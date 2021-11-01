    package com.example.appprojectcuoikhoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appprojectcuoikhoa.databinding.ActivityChiTietSanPhamBinding;

import java.text.DecimalFormat;
import java.util.List;

import adapter.Adidas_Adapter;
import adapter.Shoe_Adapter;
import model.Adidas;
import model.GioHang;
import model.Gucci;
import model.Nike;
import model.Puma;
import model.Sale;
import model.Shoe;
import model.Vans;

public class ChiTietSanPham extends AppCompatActivity {
    ActivityChiTietSanPhamBinding binding;
    String nameChiTiet ,imgChiTiet , imgFavoriteChiTiet, dongChiTiet , dongChiTietBD , imgLogoCChiTiet;
    Double moneyChiTiet , moneyCiTietBD;
    private int numberOder = 1;
    private int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_chi_tiet_san_pham);
        Intent intent = getIntent();
        imgChiTiet = intent.getStringExtra("img");
        imgFavoriteChiTiet = intent.getStringExtra("favorite");
        nameChiTiet = intent.getStringExtra("name");
        dongChiTiet = intent.getStringExtra("dong");
        moneyChiTiet = intent.getDoubleExtra("money",0);
        imgLogoCChiTiet = intent.getStringExtra("logo");


            Glide.with(this).load(imgChiTiet).into(binding.imgSanPhamChiTiet);
            Glide.with(this).load(imgFavoriteChiTiet).into(binding.imgFavoriteChiTietOn);
            Glide.with(this).load(imgLogoCChiTiet).into(binding.imgLogoChiTiet);
            binding.txtNameChiTiet.setText(nameChiTiet);
            binding.txtdongChiTiet.setText(dongChiTiet);
            binding.txtMoneyChitiet.setText(new DecimalFormat("##.000").format(moneyChiTiet));

            if (imgFavoriteChiTiet.equals("1")){
                binding.imgFavoriveChiTietOff.setImageResource(R.drawable.ic_favorite_on);
            }else {
                binding.imgFavoriteChiTietOn.setVisibility(View.INVISIBLE);
                binding.imgFavoriveChiTietOff.setVisibility(View.VISIBLE);
            }



        binding.txtSoLuongSanPham.setText(String.valueOf(numberOder));
        binding.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOder>1){
                    numberOder =numberOder-1;
                }
                binding.txtSoLuongSanPham.setText(String.valueOf(numberOder));
            }
        });
        binding.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOder = numberOder+1;
                binding.txtSoLuongSanPham.setText(String.valueOf(numberOder));
            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChiTietSanPham.this,HomeActivity.class));
            }
        });
        ThemVaoGioHang();
        String txtSize42 = binding.txtSize42.getText().toString();
        binding.txtSize42.setBackgroundResource(R.drawable.custom_size);
        binding.txtSizeChoose.setText(txtSize42);
        OnClickSize();
    }

    private void ThemVaoGioHang() {
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeActivity.gioHangArrayList.size()>0){
                    int sl = Integer.parseInt(binding.txtSoLuongSanPham.getText().toString().trim());
                    int sizeChoose = Integer.parseInt(binding.txtSizeChoose.getText().toString().trim());
                    boolean exists = false;
                    for (int i=0; i<HomeActivity.gioHangArrayList.size() ; i++){
                        if (HomeActivity.gioHangArrayList.get(i).getTenSp().equals(nameChiTiet) && HomeActivity.gioHangArrayList.get(i).getSize()==sizeChoose){
                            HomeActivity.gioHangArrayList.get(i).setSoLuongSp(HomeActivity.gioHangArrayList.get(i).getSoLuongSp()+ sl);
                            HomeActivity.gioHangArrayList.get(i).setTongGiaSp(moneyChiTiet*HomeActivity.gioHangArrayList.get(i).getSoLuongSp());
                            exists = true;
                        }
                    }
                    if (exists==false){
                        int soluong = Integer.parseInt(binding.txtSoLuongSanPham.getText().toString().trim());
                        size = Integer.parseInt(binding.txtSizeChoose.getText().toString().trim());
                        double giaMoi = soluong*moneyChiTiet;
                        HomeActivity.gioHangArrayList.add(new GioHang(imgChiTiet,nameChiTiet,moneyChiTiet,giaMoi,soluong,size));

                    }
                }else {
                    int soluong = Integer.parseInt(binding.txtSoLuongSanPham.getText().toString().trim());
                    size = Integer.parseInt(binding.txtSizeChoose.getText().toString().trim());
                    double giaMoi = soluong*moneyChiTiet;
                    HomeActivity.gioHangArrayList.add(new GioHang(imgChiTiet,nameChiTiet,moneyChiTiet,giaMoi,soluong,size));
                }
                Intent intent = new Intent(getBaseContext(),GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void OnClickSize() {
        binding.txtSize38.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtSize38 = binding.txtSize38.getText().toString();
                binding.txtSize38.setBackgroundResource(R.drawable.custom_size);
                binding.txtSize39.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize40.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize41.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize42.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize43.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSizeChoose.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSizeChoose.setText(txtSize38);

            }
        });
        binding.txtSize39.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtSize39 = binding.txtSize39.getText().toString();
                binding.txtSize39.setBackgroundResource(R.drawable.custom_size);
                binding.txtSize38.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize40.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize41.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize42.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize43.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSizeChoose.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSizeChoose.setText(txtSize39);

            }
        });
        binding.txtSize40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtSize40 = binding.txtSize40.getText().toString();
                binding.txtSize40.setBackgroundResource(R.drawable.custom_size);
                binding.txtSize41.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize42.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize43.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize38.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize39.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSizeChoose.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSizeChoose.setText(txtSize40);
            }
        });
        binding.txtSize41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtSize41 = binding.txtSize41.getText().toString();
                binding.txtSize41.setBackgroundResource(R.drawable.custom_size);
                binding.txtSize42.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize43.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize38.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize39.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize40.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSizeChoose.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSizeChoose.setText(txtSize41);
            }
        });
        binding.txtSize42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtSize42 = binding.txtSize42.getText().toString();
                binding.txtSize42.setBackgroundResource(R.drawable.custom_size);
                binding.txtSize43.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize38.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize39.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize40.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize41.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSizeChoose.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSizeChoose.setText(txtSize42);
            }
        });
        binding.txtSize43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtSize43 = binding.txtSize43.getText().toString();
                binding.txtSize43.setBackgroundResource(R.drawable.custom_size);
                binding.txtSize42.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize38.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize39.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize40.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSize41.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSizeChoose.setBackgroundResource(R.drawable.custom_btn_add);
                binding.txtSizeChoose.setText(txtSize43);
            }
        });
    }

}