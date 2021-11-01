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
import model.Vans;

public class Vans_Adapter extends RecyclerView.Adapter<Vans_Adapter.VansViewHoler>{
    private Context context;
    private List<Vans> vansList;
    private FavoriteDB favoriteDB;

    public Vans_Adapter(Context context, List<Vans> vansList) {
        this.context = context;
        this.vansList = vansList;
    }

    @Override
    public VansViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        favoriteDB = new FavoriteDB(context);
        //create table on first
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        boolean firstStart = sharedPreferences.getBoolean("firstStart",true);
        if (firstStart){
            createTabelOnFirstStart();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_vans,parent,false);
        return new VansViewHoler(view);
    }
    private void createTabelOnFirstStart() {
        favoriteDB.insertEmpty();
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();
    }
    @Override
    public void onBindViewHolder(Vans_Adapter.VansViewHoler holder, int position) {
        Vans vans = vansList.get(position);
        ReadCusorData(vans,holder);
        Glide.with(context).load(vansList.get(position).getImgVans()).into(holder.imgVans);
//        Glide.with(context).load(vansList.get(position).getImgFavoriteVans()).into(holder.imgFavoriteVans);
        holder.txtNameVans.setText(vansList.get(position).getNameVans());
        holder.txtdongVans.setText(vansList.get(position).getDongVans());
        holder.txtMoneyVans.setText(new DecimalFormat("##.000").format(vansList.get(position).getMoneyVans()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietSanPham.class);
                intent.putExtra("img",vansList.get(position).getImgVans());
                intent.putExtra("imgFavorite",vansList.get(position).getImgFavoriteVans());
                intent.putExtra("name",vansList.get(position).getNameVans());
                intent.putExtra("dong",vansList.get(position).getDongVans());
                intent.putExtra("money",vansList.get(position).getMoneyVans());
                intent.putExtra("favorite",vansList.get(position).getFavVans());
                context.startActivity(intent);
            }
        });
    }

    private void ReadCusorData(Vans vans, VansViewHoler holder) {
        Cursor cursor = favoriteDB.readAllData(vans.getIdVans());
        SQLiteDatabase database = favoriteDB.getReadableDatabase();
        try{
            while (cursor.moveToNext()){
                String item_favStatus = cursor.getString(cursor.getColumnIndex(FavoriteDB.FAVORITE_STATUS));
                vans.setFavVans(item_favStatus);
                //check fav status
                if (item_favStatus !=null && item_favStatus.equals("1")){
                    holder.imgFavoriteVans.setImageResource(R.drawable.ic_favorite_on);
                }else if (item_favStatus!=null && item_favStatus.equals("0")){
                    holder.imgFavoriteVans.setImageResource(R.drawable.ic_favorite_off);
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
        return vansList.size();
    }

    public class VansViewHoler extends RecyclerView.ViewHolder{
        ImageView imgVans;
        ImageView imgFavoriteVans;
        TextView txtNameVans;
        TextView txtdongVans;
        TextView txtMoneyVans;
        public VansViewHoler(View itemView) {
            super(itemView);
            imgVans = itemView.findViewById(R.id.imgVans);
            imgFavoriteVans = itemView.findViewById(R.id.imgFavoriteVans);
            txtNameVans = itemView.findViewById(R.id.txtNameVans);
            txtdongVans = itemView.findViewById(R.id.txtdongVans);
            txtMoneyVans = itemView.findViewById(R.id.txtMoneyVans);
            imgFavoriteVans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //add to favorite imge
                    int position = getAdapterPosition();
                    Vans vans = vansList.get(position);
                    if (vans.getFavVans().equals("0")){
                        vans.setFavVans("1");
                        favoriteDB.insertIntoTheDatabase(vans.getIdVans(),vans.getFavVans(),vans.getImgLogoSale(),vans.getImgVans(),vans.getImgFavoriteVans(),vans.getNameVans(),vans.getDongVans(),vans.getMoneyVans(),vans.getDongSaleBandau(),vans.getMoneySaleBandau());
                        imgFavoriteVans.setImageResource(R.drawable.ic_favorite_on);
                    }else {
                        vans.setFavVans("0");
                        favoriteDB.removeFavorite(vans.getIdVans());
                        imgFavoriteVans.setImageResource(R.drawable.ic_favorite_off);
                    }
                }
            });
        }
    }
}
