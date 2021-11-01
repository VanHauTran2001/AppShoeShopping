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
import model.Puma;

public class Puma_Adapter extends RecyclerView.Adapter<Puma_Adapter.PumaViewHolder>{
    private Context context;
    private List<Puma> pumaList;
    private FavoriteDB favoriteDB;

    public Puma_Adapter(Context context, List<Puma> pumaList) {
        this.context = context;
        this.pumaList = pumaList;
    }

    @Override
    public PumaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        favoriteDB = new FavoriteDB(context);
        //create table on first
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        boolean firstStart = sharedPreferences.getBoolean("firstStart",true);
        if (firstStart){
            createTabelOnFirstStart();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_puma,parent,false);
        return new PumaViewHolder(view);
    }
    private void createTabelOnFirstStart() {
        favoriteDB.insertEmpty();
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();
    }
    @Override
    public void onBindViewHolder(Puma_Adapter.PumaViewHolder holder, int position) {
        Puma puma = pumaList.get(position);
        ReadCusorData(puma,holder);
        Glide.with(context).load(pumaList.get(position).getImgPuma()).into(holder.imgPuma);
//      Glide.with(context).load(pumaList.get(position).getImgFavoritePuma()).into(holder.imgFavoritePuma);
        holder.txtNamePuma.setText(pumaList.get(position).getNamePuma());
        holder.txtdongPuma.setText(pumaList.get(position).getDongPuma());
        holder.txtMoneyPuma.setText(new DecimalFormat("##.000").format(pumaList.get(position).getMoneyPuma()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietSanPham.class);
                intent.putExtra("img",pumaList.get(position).getImgPuma());
                intent.putExtra("imgFavorite",pumaList.get(position).getImgFavoritePuma());
                intent.putExtra("name",pumaList.get(position).getNamePuma());
                intent.putExtra("dong",pumaList.get(position).getDongPuma());
                intent.putExtra("money",pumaList.get(position).getMoneyPuma());
                intent.putExtra("favorite",pumaList.get(position).getFavPuma());
                context.startActivity(intent);
            }
        });
    }

    private void ReadCusorData(Puma puma, PumaViewHolder holder) {
        Cursor cursor = favoriteDB.readAllData(puma.getIdPuma());
        SQLiteDatabase database = favoriteDB.getReadableDatabase();
        try{
            while (cursor.moveToNext()){
                String item_favStatus = cursor.getString(cursor.getColumnIndex(FavoriteDB.FAVORITE_STATUS));
                puma.setFavPuma(item_favStatus);
                //check fav status
                if (item_favStatus !=null && item_favStatus.equals("1")){
                    holder.imgFavoritePuma.setImageResource(R.drawable.ic_favorite_on);
                }else if (item_favStatus!=null && item_favStatus.equals("0")){
                    holder.imgFavoritePuma.setImageResource(R.drawable.ic_favorite_off);
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
        return pumaList.size();
    }

    public class PumaViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPuma;
        ImageView imgFavoritePuma;
        TextView txtNamePuma;
        TextView txtdongPuma;
        TextView txtMoneyPuma;
        public PumaViewHolder( View itemView) {
            super(itemView);
            imgPuma = itemView.findViewById(R.id.imgPuma);
            imgFavoritePuma = itemView.findViewById(R.id.imgFavoritePuma);
            txtNamePuma = itemView.findViewById(R.id.txtNamePuma);
            txtdongPuma = itemView.findViewById(R.id.txtdongPuma);
            txtMoneyPuma = itemView.findViewById(R.id.txtMoneyPuma);

            imgFavoritePuma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //add to favorite imge
                    int position = getAdapterPosition();
                    Puma puma = pumaList.get(position);
                    if (puma.getFavPuma().equals("0")){
                        puma.setFavPuma("1");
                        favoriteDB.insertIntoTheDatabase(puma.getIdPuma(),puma.getFavPuma(),puma.getImgLogoSale(),puma.getImgPuma(),puma.getImgFavoritePuma(),puma.getNamePuma(),puma.getDongPuma(),puma.getMoneyPuma(),puma.getDongSaleBandau(),puma.getMoneySaleBandau());
                        imgFavoritePuma.setImageResource(R.drawable.ic_favorite_on);
                    }else {
                        puma.setFavPuma("0");
                        favoriteDB.removeFavorite(puma.getIdPuma());
                        imgFavoritePuma.setImageResource(R.drawable.ic_favorite_off);
                    }
                }
            });
        }
    }
}
