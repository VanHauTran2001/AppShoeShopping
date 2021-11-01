package fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appprojectcuoikhoa.R;
import com.example.appprojectcuoikhoa.databinding.FragmentFavoriteBinding;

import java.util.ArrayList;
import java.util.List;

import adapter.FavoriteSale_Adapter;
import data.FavoriteDB;
import model.FavoriteSale;

public class FavoriteFragment extends Fragment {
    FragmentFavoriteBinding binding;
    FavoriteDB favoriteDB;
    List<FavoriteSale> favoriteSaleList = new ArrayList<>();
    FavoriteSale_Adapter favoriteSaleAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_favorite,container,false);
        favoriteDB = new FavoriteDB(getActivity());
        binding.recylerFavorite.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        binding.recylerFavorite.setLayoutManager(layoutManager);
        LoadDataFavoriteSale();
        return binding.getRoot();
    }

    private void LoadDataFavoriteSale() {
        if (favoriteSaleList != null){
            favoriteSaleList.clear();
        }
        SQLiteDatabase database = favoriteDB.getReadableDatabase();
        Cursor cursor = favoriteDB.selectAllFavoriteList();
        try {
            while (cursor.moveToNext()){
                String idFavSale = cursor.getString(cursor.getColumnIndex(FavoriteDB.KEY_ID));
                String imageFavSale = cursor.getString(cursor.getColumnIndex(FavoriteDB.IMG_SHOE));
                String nameFavSale = cursor.getString(cursor.getColumnIndex(FavoriteDB.NAME_SHOE));
                String tempMonney= cursor.getString(cursor.getColumnIndex(FavoriteDB.MONEY_SHOE));
                Double moneyFavSale;
                if (tempMonney != null){
                     moneyFavSale = Double.parseDouble(tempMonney);
                }else {
                     moneyFavSale =0.0;
                }
                String tempFav = cursor.getString(cursor.getColumnIndex(FavoriteDB.MONEY_SALE_BD));
                Double moneyFavSaleBD;

                if (tempFav != null){
                    moneyFavSaleBD = Double.parseDouble(tempFav);
                }else {
                    moneyFavSaleBD =0.0;
                }


                FavoriteSale favoriteSale = new FavoriteSale(idFavSale,imageFavSale,nameFavSale,moneyFavSale,moneyFavSaleBD);
                favoriteSaleList.add(favoriteSale);
            }
        }finally {
            if (cursor!=null && cursor.isClosed())
                cursor.close();
            database.close();
        }
        favoriteSaleAdapter = new FavoriteSale_Adapter(getActivity(),favoriteSaleList);
        binding.recylerFavorite.setAdapter(favoriteSaleAdapter);
    }


}
