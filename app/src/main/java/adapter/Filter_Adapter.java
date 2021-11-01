package adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appprojectcuoikhoa.ChiTietSanPham;
import com.example.appprojectcuoikhoa.R;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import data.FavoriteDB;
import model.Shoe;

public class Filter_Adapter extends RecyclerView.Adapter<Filter_Adapter.FilterViewHolder> implements Filterable {
    private Context context;
    private List<Shoe> list;
    private List<Shoe> mListUserOld;
    private FavoriteDB favoriteDB;

    public Filter_Adapter(Context context, List<Shoe> list) {
        this.context = context;
        this.list = list;
        this.mListUserOld = list;
    }

    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        favoriteDB = new FavoriteDB(context);
        //create table on first
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        boolean firstStart = sharedPreferences.getBoolean("firstStart",true);
        if (firstStart){
            createTabelOnFirstStart();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.iteam_filter_view,parent,false);
        return new FilterViewHolder(view);
    }
    private void createTabelOnFirstStart() {
        favoriteDB.insertEmpty();
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();
    }
    @Override
    public void onBindViewHolder(FilterViewHolder holder, int position) {
        Shoe shoe = list.get(position);
        ReadCusorData(shoe,holder);
        Glide.with(context).load(list.get(position).getImgShoe()).into(holder.imgUser);
        holder.txtNameSearch.setText(list.get(position).getNameShoe());
        holder.txtGiaSearch.setText(new DecimalFormat("##.000").format(list.get(position).getMoneyShoe()) + " đ ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietSanPham.class);
                intent.putExtra("img",list.get(position).getImgShoe());
                intent.putExtra("imgFavorite",list.get(position).getImgFavoriteShoe());
                intent.putExtra("name",list.get(position).getNameShoe());
                intent.putExtra("dong",list.get(position).getDong());
                intent.putExtra("money",list.get(position).getMoneyShoe());
                intent.putExtra("favorite",list.get(position).getFavStatus());
                context.startActivity(intent);
            }
        });
    }

    private void ReadCusorData(Shoe shoe,FilterViewHolder holder) {
        Cursor cursor = favoriteDB.readAllData(shoe.getKey_id());
        SQLiteDatabase database = favoriteDB.getReadableDatabase();
        try{
            while (cursor.moveToNext()){
                String item_favStatus = cursor.getString(cursor.getColumnIndex(FavoriteDB.FAVORITE_STATUS));
                shoe.setFavStatus(item_favStatus);

                //check fav status
                if (item_favStatus !=null && item_favStatus.equals("1")){
                    holder.imgFavTiemKiem.setImageResource(R.drawable.ic_favorite_on);
                }else if (item_favStatus!=null && item_favStatus.equals("0")){
                    holder.imgFavTiemKiem.setImageResource(R.drawable.ic_favorite_off);
                }
            }
        }finally {
            if (cursor!=null && cursor.isClosed())
                cursor.close();
            database.close();
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if (search.isEmpty()){
                    list = mListUserOld;
                }else {
                    List<Shoe> listUser = new ArrayList<>();
                    for (Shoe shoe : mListUserOld){
                        if (shoe.getNameShoe().toLowerCase().contains(search.toLowerCase())){
                            listUser.add(shoe);
                        }
                    }
                    list = listUser;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<Shoe>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder{
        ImageView imgUser;
        TextView txtNameSearch , txtGiaSearch;
        ImageView imgFavTiemKiem;
        public FilterViewHolder(View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.imgUser);
            txtNameSearch = itemView.findViewById(R.id.txtNameSearch);
            txtGiaSearch = itemView.findViewById(R.id.txtGiaSearch);
            imgFavTiemKiem = itemView.findViewById(R.id.imgFavTimKiem);

            imgFavTiemKiem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Shoe shoe = list.get(position);
                    if (shoe.getFavStatus().equals("0")){
                        shoe.setFavStatus("1");
                        favoriteDB.insertIntoTheDatabase(shoe.getKey_id(),shoe.getFavStatus(),shoe.getImgLogoSale(),shoe.getImgShoe(),shoe.getImgFavoriteShoe(),shoe.getNameShoe(),shoe.getDong(),shoe.getMoneyShoe(),shoe.getDongSaleBandau(),shoe.getMoneySaleBandau());
                        imgFavTiemKiem.setImageResource(R.drawable.ic_favorite_on);
                    }else {
                        shoe.setFavStatus("0");
                        favoriteDB.removeFavorite(shoe.getKey_id());
                        imgFavTiemKiem.setImageResource(R.drawable.ic_favorite_off);
                    }
                }
            });
        }
    }
}
