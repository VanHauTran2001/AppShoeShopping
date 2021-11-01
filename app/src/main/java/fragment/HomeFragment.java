package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appprojectcuoikhoa.ChiTietSanPham;
import com.example.appprojectcuoikhoa.MainActivity;
import com.example.appprojectcuoikhoa.R;
import com.example.appprojectcuoikhoa.TimKiemActivity;
import com.example.appprojectcuoikhoa.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import adapter.Categoris_Adapter;
import adapter.Sale_Adapter;
import adapter.Shoe_Adapter;

import model.Categories;
import model.DataShop;
import model.Sale;
import model.Shoe;
import retrofit.ApiInterface;
import retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    ApiInterface apiInterface;
    FragmentHomeBinding binding;
    List<Categories> categoriesArrayList;
    List<Shoe> shoeArrayList;
    List<Sale> saleArrayList;
    List<DataShop> dataShopArrayList;
    Sale_Adapter saleAdapter;
    Shoe_Adapter shoeAdapter;
    Categoris_Adapter adapter;
    Shoe shoe = new Shoe();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        categoriesArrayList = new ArrayList<>();
        shoeArrayList = new ArrayList<>();
        saleArrayList = new ArrayList<>();
        dataShopArrayList = new ArrayList<>();
//        addCategory();
//        addShoe();
//        addSale();
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<DataShop>> call = apiInterface.getAllData();
        call.enqueue(new Callback<List<DataShop>>() {
            @Override
            public void onResponse(Call<List<DataShop>> call, Response<List<DataShop>> response) {
                dataShopArrayList = response.body();
                getCategoriesData(dataShopArrayList.get(0).getCategoriesList());

                getShoeData(dataShopArrayList.get(0).getShoeList());

                getSaleData(dataShopArrayList.get(0).getSaleList());
            }

            @Override
            public void onFailure(Call<List<DataShop>> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ActionViewFliper();
        binding.edtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),TimKiemActivity.class));
            }
        });

//        HienThiDataShoe();
        return binding.getRoot();
    }

    private void ActionViewFliper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://bboomersbar.com/dich-vu-in-poster/imager_62799.jpg");
        mangquangcao.add("https://cdn.vatgia.vn/pictures/fullsize/2016/04/22/bkw1461314197.jpg");
        mangquangcao.add("https://inanaz.com.vn/wp-content/uploads/2020/02/mau-poster-khuyen-mai-11.jpg");
        mangquangcao.add("https://mona.media/wp-content/uploads/2021/06/banner-giay.png");
        mangquangcao.add("https://inanaz.com.vn/wp-content/uploads/2020/02/mau-poster-khuyen-mai-15.jpg");
        mangquangcao.add("https://inanaz.com.vn/wp-content/uploads/2020/02/poster-flash-sale-2.jpg");
        mangquangcao.add("https://scontent.fhan3-2.fna.fbcdn.net/v/t1.6435-9/240347599_243497534305808_7742478771921997266_n.jpg?_nc_cat=103&ccb=1-5&_nc_sid=e3f864&_nc_ohc=AAWWcs466fQAX_HXzBV&_nc_ht=scontent.fhan3-2.fna&oh=6046edacbc44aa98bfa2050617a6b6d9&oe=6182BA38");
        mangquangcao.add("https://i.ytimg.com/vi/j41pOvp9UYk/maxresdefault.jpg");
        mangquangcao.add("https://i2.wp.com/marketingai.admicro.vn/wp-content/uploads/2020/12/Nike.jpg?resize=900%2C658&ssl=1");
        mangquangcao.add("https://theme.hstatic.net/1000092380/1000548312/14/ms_banner_img1.jpg?v=87");
        mangquangcao.add("https://inanaz.com.vn/wp-content/uploads/2020/02/mau-banner-quang-cao-3.jpg");
        for (int i=0;i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getActivity());
            Glide.with(getActivity()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            binding.viewfliper.addView(imageView);
        }
        binding.viewfliper.setFlipInterval(8000);
        binding.viewfliper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_in_flipper);
//        Animation animation_slide_out = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_out_flipper);
        binding.viewfliper.setInAnimation(animation_slide_in);
//        binding.viewfliper.setOutAnimation(animation_slide_out);
    }

    private void getCategoriesData(List<Categories> categoriesArrayList){
        adapter = new Categoris_Adapter(getActivity(),categoriesArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        binding.recylerCategories.setLayoutManager(layoutManager);
        binding.recylerCategories.setAdapter(adapter);
    }
    private void getShoeData(List<Shoe> shoeArrayList){
        shoeAdapter = new Shoe_Adapter(getActivity(), shoeArrayList);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        binding.recylerShoe.setLayoutManager(layoutManager2);
        binding.recylerShoe.setAdapter(shoeAdapter);
    }
    private void getSaleData(List<Sale> saleArrayList){
        saleAdapter = new Sale_Adapter(getActivity(),saleArrayList);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        binding.recylerSale.setLayoutManager(layoutManager3);
        binding.recylerSale.setAdapter(saleAdapter);
    }
}
