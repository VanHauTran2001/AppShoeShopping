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
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appprojectcuoikhoa.ChiTietSanPham;
import com.example.appprojectcuoikhoa.R;

import java.text.DecimalFormat;
import java.util.List;

import data.FavoriteDB;
import model.Adidas;
import model.Nike;

public class Adidas_Adapter extends RecyclerView.Adapter<Adidas_Adapter.AdidasViewHolder>{
    private Context context;
    private List<Adidas> adidasList;
    private FavoriteDB favoriteDB;

    public Adidas_Adapter(Context context, List<Adidas> adidasList) {
        this.context = context;
        this.adidasList = adidasList;
    }

    @Override
    public AdidasViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        favoriteDB = new FavoriteDB(context);
        //create table on first
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        boolean firstStart = sharedPreferences.getBoolean("firstStart",true);
        if (firstStart){
            createTabelOnFirstStart();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_adidas,parent,false);
        return new AdidasViewHolder(view);
    }
    private void createTabelOnFirstStart() {
        favoriteDB.insertEmpty();
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();
    }

    @Override
    public void onBindViewHolder(Adidas_Adapter.AdidasViewHolder holder, int position) {
        Adidas adidas = adidasList.get(position);
        ReadCusorData(adidas,holder);
        Glide.with(context).load(adidasList.get(position).getImgAdidas()).into(holder.imgAdidas);
//        Glide.with(context).load(adidasList.get(position).getImgFavoriteAdidas()).into(holder.imgFavoriteAdidas);
        holder.txtNameAdidas.setText(adidasList.get(position).getNameAdidas());
        holder.txtdongAdidas.setText(adidasList.get(position).getDongAdidas());
        holder.txtMoneyAdidas.setText(new DecimalFormat("##.000").format(adidasList.get(position).getMoneyAdidas()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietSanPham.class);
                intent.putExtra("img",adidasList.get(position).getImgAdidas());
                intent.putExtra("imgFavorite",adidasList.get(position).getImgFavoriteAdidas());
                intent.putExtra("name",adidasList.get(position).getNameAdidas());
                intent.putExtra("dong",adidasList.get(position).getDongAdidas());
                intent.putExtra("money",adidasList.get(position).getMoneyAdidas());
                intent.putExtra("favorite",adidasList.get(position).getFavAdidas());
                context.startActivity(intent);
            }
        });
    }

    private void ReadCusorData(Adidas adidas, AdidasViewHolder holder) {
        Cursor cursor = favoriteDB.readAllData(adidas.getIdAdidas());
        SQLiteDatabase database = favoriteDB.getReadableDatabase();
        try{
            while (cursor.moveToNext()){
                String item_favStatus = cursor.getString(cursor.getColumnIndex(FavoriteDB.FAVORITE_STATUS));
                adidas.setFavAdidas(item_favStatus);
                //check fav status
                if (item_favStatus !=null && item_favStatus.equals("1")){
                    holder.imgFavoriteAdidas.setImageResource(R.drawable.ic_favorite_on);
                }else if (item_favStatus!=null && item_favStatus.equals("0")){
                    holder.imgFavoriteAdidas.setImageResource(R.drawable.ic_favorite_off);
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
        return adidasList.size();
    }

    public class AdidasViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAdidas;
        ImageView imgFavoriteAdidas;
        TextView txtNameAdidas;
        TextView txtdongAdidas;
        TextView txtMoneyAdidas;
        public AdidasViewHolder(View itemView) {
            super(itemView);
            imgAdidas = itemView.findViewById(R.id.imgAdidas);
            imgFavoriteAdidas= itemView.findViewById(R.id.imgFavoriteAdidas);
            txtNameAdidas = itemView.findViewById(R.id.txtNameAdidas);
            txtdongAdidas = itemView.findViewById(R.id.txtdongAdidas);
            txtMoneyAdidas = itemView.findViewById(R.id.txtMoneyAdidas);

            imgFavoriteAdidas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //add to favorite imge
                    int position = getAdapterPosition();
                    Adidas adidas = adidasList.get(position);
                    if (adidas.getFavAdidas().equals("0")){
                        adidas.setFavAdidas("1");
                        favoriteDB.insertIntoTheDatabase(adidas.getIdAdidas(),adidas.getFavAdidas(),adidas.getImgLogoSale(),adidas.getImgAdidas(),adidas.getImgFavoriteAdidas(),adidas.getNameAdidas(),adidas.getDongAdidas(),adidas.getMoneyAdidas(),adidas.getDongSaleBandau(),adidas.getMoneySaleBandau());
                        imgFavoriteAdidas.setImageResource(R.drawable.ic_favorite_on);
                    }else {
                        adidas.setFavAdidas("0");
                        favoriteDB.removeFavorite(adidas.getIdAdidas());
                        imgFavoriteAdidas.setImageResource(R.drawable.ic_favorite_off);
                    }
                }
            });
        }
    }
}
