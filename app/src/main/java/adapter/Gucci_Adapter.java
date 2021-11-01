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
import model.Gucci;

public class Gucci_Adapter extends RecyclerView.Adapter<Gucci_Adapter.GucciViewHolder>{
    private Context context;
    private List<Gucci> gucciList;
    private FavoriteDB favoriteDB;
    public Gucci_Adapter(Context context, List<Gucci> gucciList) {
        this.context = context;
        this.gucciList = gucciList;
    }

    @Override
    public GucciViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        favoriteDB = new FavoriteDB(context);
        //create table on first
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        boolean firstStart = sharedPreferences.getBoolean("firstStart",true);
        if (firstStart){
            createTabelOnFirstStart();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_gucci,parent,false);
        return new GucciViewHolder(view);
    }
    private void createTabelOnFirstStart() {
        favoriteDB.insertEmpty();
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();
    }
    @Override
    public void onBindViewHolder(Gucci_Adapter.GucciViewHolder holder, int position) {
        Gucci gucci = gucciList.get(position);
        ReadCusorData(gucci,holder);
        Glide.with(context).load(gucciList.get(position).getImgGucci()).into(holder.imgGucci);
//        Glide.with(context).load(gucciList.get(position).getImgFavoriteGucci()).into(holder.imgFavoriteGucci);
        holder.txtNameGucci.setText(gucciList.get(position).getNameGucci());
        holder.txtdongGucci.setText(gucciList.get(position).getDongGucci());
        holder.txtMoneyGucci.setText(new DecimalFormat("##.000").format(gucciList.get(position).getMoneyGucci()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietSanPham.class);
                intent.putExtra("img",gucciList.get(position).getImgGucci());
                intent.putExtra("imgFavorite",gucciList.get(position).getImgFavoriteGucci());
                intent.putExtra("name",gucciList.get(position).getNameGucci());
                intent.putExtra("dong",gucciList.get(position).getDongGucci());
                intent.putExtra("money",gucciList.get(position).getMoneyGucci());
                intent.putExtra("favorite",gucciList.get(position).getFavGucci());
                context.startActivity(intent);
            }
        });
    }

    private void ReadCusorData(Gucci gucci, GucciViewHolder holder) {
        Cursor cursor = favoriteDB.readAllData(gucci.getIdGucci());
        SQLiteDatabase database = favoriteDB.getReadableDatabase();
        try{
            while (cursor.moveToNext()){
                String item_favStatus = cursor.getString(cursor.getColumnIndex(FavoriteDB.FAVORITE_STATUS));
                gucci.setFavGucci(item_favStatus);
                //check fav status
                if (item_favStatus !=null && item_favStatus.equals("1")){
                    holder.imgFavoriteGucci.setImageResource(R.drawable.ic_favorite_on);
                }else if (item_favStatus!=null && item_favStatus.equals("0")){
                    holder.imgFavoriteGucci.setImageResource(R.drawable.ic_favorite_off);
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
        return gucciList.size();
    }

    public class GucciViewHolder extends RecyclerView.ViewHolder{
        ImageView imgGucci;
        ImageView imgFavoriteGucci;
        TextView txtNameGucci;
        TextView txtdongGucci;
        TextView txtMoneyGucci;

        public GucciViewHolder( View itemView) {
            super(itemView);
            imgGucci = itemView.findViewById(R.id.imgGucci);
            imgFavoriteGucci = itemView.findViewById(R.id.imgFavoriteGucci);
            txtNameGucci = itemView.findViewById(R.id.txtNameGucci);
            txtdongGucci = itemView.findViewById(R.id.txtdongGucci);
            txtMoneyGucci = itemView.findViewById(R.id.txtMoneyGucci);

            imgFavoriteGucci.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //add to favorite imge
                    int position = getAdapterPosition();
                    Gucci gucci = gucciList.get(position);
                    if (gucci.getFavGucci().equals("0")){
                        gucci.setFavGucci("1");
                        favoriteDB.insertIntoTheDatabase(gucci.getIdGucci(),gucci.getFavGucci(),gucci.getImgLogoSale(),gucci.getImgGucci(),gucci.getImgFavoriteGucci(),gucci.getNameGucci(),gucci.getDongGucci(),gucci.getMoneyGucci(),gucci.getDongSaleBandau(),gucci.getMoneySaleBandau());
                        imgFavoriteGucci.setImageResource(R.drawable.ic_favorite_on);
                    }else {
                        gucci.setFavGucci("0");
                        favoriteDB.removeFavorite(gucci.getIdGucci());
                        imgFavoriteGucci.setImageResource(R.drawable.ic_favorite_off);
                    }
                }
            });
        }
    }
}
