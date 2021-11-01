package adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appprojectcuoikhoa.ChiTietSanPham;
import com.example.appprojectcuoikhoa.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import data.FavoriteDB;
import model.Shoe;

public class Shoe_Adapter extends RecyclerView.Adapter<Shoe_Adapter.ShoeViewHolder>{
    private Context context;
   private List<Shoe> shoeList;
   private FavoriteDB favoriteDB;

    public Shoe_Adapter(Context context, List<Shoe> shoeList) {
        this.context = context;
        this.shoeList = shoeList;
    }

    @Override
    public ShoeViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        favoriteDB = new FavoriteDB(context);
        //create table on first
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        boolean firstStart = sharedPreferences.getBoolean("firstStart",true);
        if (firstStart){
            createTabelOnFirstStart();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_shoe,parent,false);
        return new ShoeViewHolder(view);
    }

    private void createTabelOnFirstStart() {
        favoriteDB.insertEmpty();
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();
    }

    @Override
    public void onBindViewHolder( Shoe_Adapter.ShoeViewHolder holder, int position) {
        Shoe shoe = shoeList.get(position);
        ReadCusorData(shoe,holder);
        Glide.with(context).load(shoeList.get(position).getImgShoe()).into(holder.imgShoe);
//        Glide.with(context).load(shoeList.get(position).getImgFavoriteShoe()).into(holder.imgFavoriteShoe);
        holder.txtNameShoe.setText(shoeList.get(position).getNameShoe());
        holder.txtd.setText(shoeList.get(position).getDong());
        holder.txtMoneyShoe.setText(new DecimalFormat("##.000").format(shoeList.get(position).getMoneyShoe()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPham.class);
                    intent.putExtra("img",shoeList.get(position).getImgShoe());
                    intent.putExtra("imgFavorite",shoeList.get(position).getImgFavoriteShoe());
                    intent.putExtra("name",shoeList.get(position).getNameShoe());
                    intent.putExtra("dong",shoeList.get(position).getDong());
                    intent.putExtra("money",shoeList.get(position).getMoneyShoe());
                     intent.putExtra("favorite",shoeList.get(position).getFavStatus());
                Log.d("hau", shoeList.get(position).getFavStatus() + "..........");
                  // intent.putExtra("favorite",favorite);
                    context.startActivity(intent);
            }
        });
    }

    private void ReadCusorData(Shoe shoe, ShoeViewHolder holder) {
        Cursor cursor = favoriteDB.readAllData(shoe.getKey_id());
        SQLiteDatabase database = favoriteDB.getReadableDatabase();
        try{
            while (cursor.moveToNext()){
                String item_favStatus = cursor.getString(cursor.getColumnIndex(FavoriteDB.FAVORITE_STATUS));
                shoe.setFavStatus(item_favStatus);

                //check fav status
                if (item_favStatus !=null && item_favStatus.equals("1")){
                holder.imgFavoriteShoe.setImageResource(R.drawable.ic_favorite_on);
                }else if (item_favStatus!=null && item_favStatus.equals("0")){
                    holder.imgFavoriteShoe.setImageResource(R.drawable.ic_favorite_off);
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
        return shoeList.size();
    }

    public class ShoeViewHolder extends RecyclerView.ViewHolder{
        ImageView imgShoe;
        TextView txtNameShoe;
        TextView txtd;
        TextView txtMoneyShoe;
        ImageView imgFavoriteShoe;

        public ShoeViewHolder( View itemView) {
            super(itemView);
            imgShoe = itemView.findViewById(R.id.imgShoe);
            txtNameShoe = itemView.findViewById(R.id.txtNameShoe);
            txtd = itemView.findViewById(R.id.txtdong);
            txtMoneyShoe = itemView.findViewById(R.id.txtMoneyShoe);
            imgFavoriteShoe = itemView.findViewById(R.id.imgFavoriteShoe);



            imgFavoriteShoe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //add to favorite imge
                    int position = getAdapterPosition();
                    Shoe shoe = shoeList.get(position);
                   if (shoe.getFavStatus().equals("0")){
                       shoe.setFavStatus("1");
                       favoriteDB.insertIntoTheDatabase(shoe.getKey_id(),shoe.getFavStatus(),shoe.getImgLogoSale(),shoe.getImgShoe(),shoe.getImgFavoriteShoe(),shoe.getNameShoe(),shoe.getDong(),shoe.getMoneyShoe(),shoe.getDongSaleBandau(),shoe.getMoneySaleBandau());
                       imgFavoriteShoe.setImageResource(R.drawable.ic_favorite_on);
                   }else {
                       shoe.setFavStatus("0");
                       favoriteDB.removeFavorite(shoe.getKey_id());
                       imgFavoriteShoe.setImageResource(R.drawable.ic_favorite_off);
                   }
                }
            });
        }
    }
}
