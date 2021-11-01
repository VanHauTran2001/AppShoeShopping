package adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.List;

import data.FavoriteDB;
import model.Nike;
import model.Shoe;

public class Nike_Adapter extends RecyclerView.Adapter<Nike_Adapter.NikeViewHolder>{
    private Context context;
    private List<Nike> nikeList;
    private FavoriteDB favoriteDB;

    public Nike_Adapter(Context context, List<Nike> nikeList) {
        this.context = context;
        this.nikeList = nikeList;
    }

    @Override
    public NikeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        favoriteDB = new FavoriteDB(context);
        //create table on first
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        boolean firstStart = sharedPreferences.getBoolean("firstStart",true);
        if (firstStart){
            createTabelOnFirstStart();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_nike,parent,false);
        return new NikeViewHolder(view);
    }
    private void createTabelOnFirstStart() {
        favoriteDB.insertEmpty();
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();
    }

    @Override
    public void onBindViewHolder(Nike_Adapter.NikeViewHolder holder, int position) {
        Nike nike = nikeList.get(position);
        ReadCusorData(nike,holder);
        Glide.with(context).load(nikeList.get(position).getImgNike()).into(holder.imgNike);
//        Glide.with(context).load(nikeList.get(position).getImgFavoriteNike()).into(holder.imgFavoriteNike);
        holder.txtNameNike.setText(nikeList.get(position).getNameNike());
        holder.txtdongNike.setText(nikeList.get(position).getDongNike());
        holder.txtMoneyNike.setText(new DecimalFormat("##.000").format(nikeList.get(position).getMoneyNike()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietSanPham.class);
                intent.putExtra("img",nikeList.get(position).getImgNike());
                intent.putExtra("imgFavorite",nikeList.get(position).getImgFavoriteNike());
                intent.putExtra("name",nikeList.get(position).getNameNike());
                intent.putExtra("dong",nikeList.get(position).getDongNike());
                intent.putExtra("money",nikeList.get(position).getMoneyNike());
                intent.putExtra("favorite",nikeList.get(position).getFavNike());
                context.startActivity(intent);
            }
        });
    }
    private void ReadCusorData(Nike nike,Nike_Adapter.NikeViewHolder holder) {
        Cursor cursor = favoriteDB.readAllData(nike.getIdNike());
        SQLiteDatabase database = favoriteDB.getReadableDatabase();
        try{
            while (cursor.moveToNext()){
                String item_favStatus = cursor.getString(cursor.getColumnIndex(FavoriteDB.FAVORITE_STATUS));
                nike.setFavNike(item_favStatus);
                //check fav status
                if (item_favStatus !=null && item_favStatus.equals("1")){
                    holder.imgFavoriteNike.setImageResource(R.drawable.ic_favorite_on);
                }else if (item_favStatus!=null && item_favStatus.equals("0")){
                    holder.imgFavoriteNike.setImageResource(R.drawable.ic_favorite_off);
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
        return nikeList.size();
    }

    public class NikeViewHolder extends RecyclerView.ViewHolder{
        ImageView imgNike;
        ImageView imgFavoriteNike;
        TextView txtNameNike;
        TextView txtdongNike;
        TextView txtMoneyNike;
        public NikeViewHolder( View itemView) {
            super(itemView);
            imgNike = itemView.findViewById(R.id.imgNike);
            imgFavoriteNike = itemView.findViewById(R.id.imgFavoriteNike);
            txtNameNike = itemView.findViewById(R.id.txtNameNike);
            txtdongNike = itemView.findViewById(R.id.txtdongNike);
            txtMoneyNike = itemView.findViewById(R.id.txtMoneyNike);

            imgFavoriteNike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //add to favorite imge
                    int position = getAdapterPosition();
                    Nike nike = nikeList.get(position);
                    if (nike.getFavNike().equals("0")){
                        nike.setFavNike("1");
                        favoriteDB.insertIntoTheDatabase(nike.getIdNike(),nike.getFavNike(),nike.getImgLogoSale(),nike.getImgNike(),nike.getImgFavoriteNike(),nike.getNameNike(),nike.getDongNike(),nike.getMoneyNike(),nike.getDongSaleBandau(),nike.getMoneySaleBandau());
                        imgFavoriteNike.setImageResource(R.drawable.ic_favorite_on);
                    }else {
                        nike.setFavNike("0");
                        favoriteDB.removeFavorite(nike.getIdNike());
                        imgFavoriteNike.setImageResource(R.drawable.ic_favorite_off);
                    }
                }
            });
        }
    }
}
