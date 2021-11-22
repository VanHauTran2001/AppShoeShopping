package com.example.appprojectcuoikhoa;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appprojectcuoikhoa.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Utils.Utils;
import fragment.ChangePaswordFragment;
import fragment.ChatFragment;
import fragment.DiaChiCuaHangFragment;
import fragment.HomeFragment;
import fragment.FavoriteFragment;
import fragment.TaiKhoanFragment;
import fragment.ThongBaoFragment;
import model.GioHang;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_CHAT = 2;
    private static final int FRAGMENT_YEUTHICH = 3;
    private static final int FRAGMENT_THONGBAO = 4;
    private static final int FRAGMENT_TAI_KHOAN = 5;
    private static final int FRAGMENT_DIA_CHI_CUA_HANG = 7;
    private static final int FRAGMENT_CHANGE_PASSWORD = 6;
    public static final int MY_REQUEST_CODE = 10;
    final private TaiKhoanFragment taiKhoanFragment = new TaiKhoanFragment();
    ImageView imgAvata;
    TextView txtName , txtEmail , txtPhoneNumber , txtDiaChi;
    private int mCurrentFragment = FRAGMENT_HOME;
    public static List<GioHang> gioHangArrayList;
    final private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            , new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK){
                Intent intent = result.getData();
                if (intent==null){
                    return;
                }
                Uri uri = intent.getData();
                taiKhoanFragment.setUri(uri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    taiKhoanFragment.setBitmapImageView(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        ToolbarGioHang();
        setSupportActionBar(binding.toolbar);


        //getSupportActionBar().setDisplayShowTitleEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,binding.dramerlayout,binding.toolbar,
                R.string.navi_Dramer_Open,R.string.navi_Dramer_Close);
        binding.dramerlayout.addDrawerListener(toggle);
        toggle.syncState();
        replaceFragment(new HomeFragment());
        binding.navicationView.getMenu().findItem(R.id.naviHome).setChecked(true);
        setTitleToolbar();
        anhxa();
        showUserInformation();

        binding.bottomNavigation.getMenu().findItem(R.id.bottomHome).setChecked(true);
        binding.navicationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()){
                    case R.id.naviHome:
                    openHomeFragment();
                    binding.bottomNavigation.getMenu().findItem(R.id.bottomHome).setChecked(true);
                    break;
                    case R.id.naviYeuThich:
                        openYeuThichFragment();
                        binding.bottomNavigation.getMenu().findItem(R.id.bottomYeuThich).setChecked(true);
                        break;
                    case R.id.naviChat:
//                        openChatFragment();
//                        binding.bottomNavigation.getMenu().findItem(R.id.bottomChat).setChecked(true);
                       break;
                    case R.id.naviThongBao:
                        openThongBaoFragment();
                        binding.bottomNavigation.getMenu().findItem(R.id.bottomThongBao).setChecked(true);
                        break;
                    case R.id.naviLocal:
                        openDiachiFragment();
                        break;
                    case R.id.naviTaiKhoan:
                        openTaiKhoanFragment();
                        binding.bottomNavigation.getMenu().findItem(R.id.bottomTaiKhoan).setChecked(true);
                        break;
                    case R.id.naviDangXuat:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(HomeActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                        break;
                    case R.id.naviPassword:
                        openChangePasswordFragment();
                        break;
                }
                setTitleToolbar();
                binding.dramerlayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottomHome:

                        openHomeFragment();
                        binding.navicationView.getMenu().findItem(R.id.naviHome).setChecked(true);
                        break;
                    case R.id.bottomChat:
                        openChatFragment();
                        binding.navicationView.getMenu().findItem(R.id.naviChat).setChecked(true);
                        break;
                    case R.id.bottomYeuThich:
                        openYeuThichFragment();
                        binding.navicationView.getMenu().findItem(R.id.naviYeuThich).setChecked(true);
                        break;
                    case R.id.bottomThongBao:
                        openThongBaoFragment();
                        binding.navicationView.getMenu().findItem(R.id.naviThongBao).setChecked(true);
                        break;
                    case R.id.bottomTaiKhoan:
                        openTaiKhoanFragment();
                        binding.navicationView.getMenu().findItem(R.id.naviTaiKhoan).setChecked(true);
                        break;
                }
                setTitleToolbar();
                return true;
            }
        });

    }

    private void ToolbarGioHang() {
        binding.imgGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeActivity.gioHangArrayList == null){

                }else {
                    startActivity(new Intent(getBaseContext(),GioHangActivity.class));
                }
            }
        });
    }


    private void setTitleToolbar(){
        String tilte = "";
        switch (mCurrentFragment){
            case FRAGMENT_HOME:
                tilte = getString(R.string.naviHome);
                binding.txtTitle.setText(tilte);
                break;
            case FRAGMENT_YEUTHICH:
                tilte = getString(R.string.naviYeuThich);
                binding.txtTitle.setText(tilte);
                break;
            case FRAGMENT_CHAT:
                tilte = getString(R.string.naviTinnhan);
                binding.txtTitle.setText(tilte);
                break;
            case FRAGMENT_CHANGE_PASSWORD:
                tilte = getString(R.string.naviPassword);
                binding.txtTitle.setText(tilte);
                break;
            case FRAGMENT_THONGBAO:
                tilte = getString(R.string.naviThongbao);
                binding.txtTitle.setText(tilte);
                break;
            case FRAGMENT_TAI_KHOAN:
                tilte = getString(R.string.naviTaiKhoan);
                binding.txtTitle.setText(tilte);
                break;
            case FRAGMENT_DIA_CHI_CUA_HANG:
                tilte = getString(R.string.naviLocal);
                binding.txtTitle.setText(tilte);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        if (binding.dramerlayout.isDrawerOpen(GravityCompat.START)){
            binding.dramerlayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
    private void openHomeFragment(){
        if (mCurrentFragment != FRAGMENT_HOME){
            replaceFragment(new HomeFragment());
            mCurrentFragment = FRAGMENT_HOME;
        }
    }
    private void openChatFragment(){
        if (mCurrentFragment != FRAGMENT_CHAT){
            replaceFragment(new ChatFragment());
            mCurrentFragment = FRAGMENT_CHAT;
        }
//        Fragment fragment = new ChatFragment();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.contentFrame,fragment,fragment.getClass().getSimpleName()).addToBackStack(null).commit();


    }
    private void openThongBaoFragment(){
        if (mCurrentFragment != FRAGMENT_THONGBAO){
            replaceFragment(new ThongBaoFragment());
            mCurrentFragment = FRAGMENT_THONGBAO;
        }
    }
    private void openYeuThichFragment(){
        if (mCurrentFragment != FRAGMENT_YEUTHICH){
            replaceFragment(new FavoriteFragment());
            mCurrentFragment = FRAGMENT_YEUTHICH;
        }
    }
    private void openDiachiFragment(){
        if (mCurrentFragment != FRAGMENT_DIA_CHI_CUA_HANG){
            replaceFragment(new DiaChiCuaHangFragment());
            mCurrentFragment = FRAGMENT_DIA_CHI_CUA_HANG;
        }
    }
    private void openTaiKhoanFragment(){
        if (mCurrentFragment != FRAGMENT_TAI_KHOAN){
            replaceFragment(taiKhoanFragment);
            mCurrentFragment = FRAGMENT_TAI_KHOAN;
        }
    }
    private void openChangePasswordFragment(){
        if (mCurrentFragment != FRAGMENT_CHANGE_PASSWORD){
            replaceFragment(new ChangePaswordFragment());
            mCurrentFragment = FRAGMENT_CHANGE_PASSWORD;
        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentFrame,fragment);
        transaction.commit();
    }
    private void anhxa(){
        imgAvata = binding.navicationView.getHeaderView(0).findViewById(R.id.imgAvata);
        txtName = binding.navicationView.getHeaderView(0).findViewById(R.id.txtName);
        txtEmail = binding.navicationView.getHeaderView(0).findViewById(R.id.txtEmail);
        txtPhoneNumber = binding.navicationView.getHeaderView(0).findViewById(R.id.txtPhoneNumber);
        txtDiaChi = binding.navicationView.getHeaderView(0).findViewById(R.id.txtDiaChi);
        int totalItem = 0;


        if (gioHangArrayList!=null){
            for (int i = 0; i<gioHangArrayList.size(); i++){
                totalItem = totalItem+ gioHangArrayList.get(i).getSoLuongSp();
            }
            binding.txtSoLuongDonHang.setText(totalItem + "");

        }else {
            gioHangArrayList = new ArrayList<>();
        }
    }
    public void showUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null){
            return;
        }

        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();
        if (name == null){
            txtName.setVisibility(View.GONE);
        }else {
            txtName.setVisibility(View.VISIBLE);
            txtName.setText(name);
        }
        txtEmail.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.ic_baseline_account_circle_24).into(imgAvata);
        String phone = Utils.PHONE;
        String diachi = Utils.DIACHI;
        txtPhoneNumber.setText(phone);
        txtDiaChi.setText(diachi);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode ==MY_REQUEST_CODE){
            if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }

    public void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent,"Select Picture"));

    }

}