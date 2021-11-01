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
import model.Sale;
import model.Shoe;

public class Sale_Adapter extends RecyclerView.Adapter<Sale_Adapter.SaleViewHolder>{
    private Context context;
    private List<Sale> saleList;
    private FavoriteDB favoriteDB;

    public Sale_Adapter(Context context, List<Sale> saleList) {
        this.context = context;
        this.saleList = saleList;
    }

    @Override
    public SaleViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        favoriteDB = new FavoriteDB(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        boolean firstStart = sharedPreferences.getBoolean("firstStart",true);
        if (firstStart){
            createTabelOnFirstStart();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_sale,parent,false);
        return new SaleViewHolder(view);
    }

    private void createTabelOnFirstStart() {
        favoriteDB.insertEmpty();
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();
    }

    @Override
    public void onBindViewHolder( Sale_Adapter.SaleViewHolder holder, int position) {
        Sale sale = saleList.get(position);
        ReadCusorDataSale(sale,holder);

        Glide.with(context).load(saleList.get(position).getImgSanPhamSale()).into(holder.imgSanPhanSale);
//        Glide.with(context).load(saleList.get(position).getImgFavoriteSale()).into(holder.imgFavoriteSale);
        Glide.with(context).load(saleList.get(position).getImgLogoSale()).into(holder.imgLogoSale);
        holder.txtNameSale.setText(saleList.get(position).getNameSale());
        holder.txtdongSale.setText(saleList.get(position).getDongSale());
        holder.txtMoneySale.setText(new DecimalFormat("##.000").format(saleList.get(position).getMoneySale()));
        holder.txtdongSaleBandau.setText(saleList.get(position).getDongSaleBandau());
        holder.txtmoneySaleBandau.setText(new DecimalFormat("##.000").format(saleList.get(position).getMoneySaleBandau()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietSanPham.class);
                intent.putExtra("img",saleList.get(position).getImgSanPhamSale());
                intent.putExtra("imgFavorite",saleList.get(position).getImgFavoriteSale());
                intent.putExtra("name",saleList.get(position).getNameSale());
                intent.putExtra("dong",saleList.get(position).getDongSale());
                intent.putExtra("money",saleList.get(position).getMoneySale());
                intent.putExtra("logo",saleList.get(position).getImgLogoSale());
                intent.putExtra("favorite",saleList.get(position).getFavStatus_Sale());
                context.startActivity(intent);
            }
        });

    }

    private void ReadCusorDataSale(Sale sale, SaleViewHolder holder) {
        Cursor cursor = favoriteDB.readAllData(sale.getId_sale());
        SQLiteDatabase database = favoriteDB.getReadableDatabase();
        try{
            while (cursor.moveToNext()){
                String item_favStatus_sale = cursor.getString(cursor.getColumnIndex(FavoriteDB.FAVORITE_STATUS));
                sale.setFavStatus_Sale(item_favStatus_sale);

                //check fav status
                if (item_favStatus_sale !=null && item_favStatus_sale.equals("1")){
                    holder.imgFavoriteSale.setImageResource(R.drawable.ic_favorite_on);
                }else if (item_favStatus_sale!=null && item_favStatus_sale.equals("0")){
                    holder.imgFavoriteSale.setImageResource(R.drawable.ic_favorite_off);
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
        return saleList.size();
    }

    public class SaleViewHolder extends RecyclerView.ViewHolder{
        ImageView imgSanPhanSale;
        ImageView imgFavoriteSale;
//        ImageView imgFavoriteSaleOn;
        ImageView imgLogoSale;
        TextView txtNameSale;
        TextView txtdongSale;
        TextView txtMoneySale;
        TextView txtdongSaleBandau;
        TextView txtmoneySaleBandau;
        public SaleViewHolder( View itemView) {
            super(itemView);
            imgSanPhanSale = itemView.findViewById(R.id.imgSanPhamSale);
            imgFavoriteSale = itemView.findViewById(R.id.imgFavoriteSale);
            imgLogoSale  = itemView.findViewById(R.id.imgLogoSale);
            txtNameSale = itemView.findViewById(R.id.txtNameSale);
            txtdongSale = itemView.findViewById(R.id.txtdongSale);
            txtMoneySale = itemView.findViewById(R.id.txtMoneySale);
            txtdongSaleBandau = itemView.findViewById(R.id.txtdongSaleBanDau);
            txtmoneySaleBandau = itemView.findViewById(R.id.txtMoneySaleBanDau);
//            imgFavoriteSaleOn = itemView.findViewById(R.id.imgFavoriteSaleOn);

            imgFavoriteSale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //add to favorite image
                    int position = getAdapterPosition();
                    Sale sale = saleList.get(position);
                    if (sale.getFavStatus_Sale().equals("0")){
                        sale.setFavStatus_Sale("1");
                        favoriteDB.insertIntoTheDatabase(sale.getId_sale(),sale.getFavStatus_Sale(),sale.getImgLogoSale(),sale.getImgSanPhamSale(),sale.getImgFavoriteSale(),sale.getNameSale(),sale.getDongSale(),sale.getMoneySale(),sale.getDongSaleBandau(),sale.getMoneySaleBandau());
                        imgFavoriteSale.setImageResource(R.drawable.ic_favorite_on);
                    }else {
                        sale.setFavStatus_Sale("0");
                        favoriteDB.removeFavorite(sale.getId_sale());
                        imgFavoriteSale.setImageResource(R.drawable.ic_favorite_off);
                    }
                }
            });
        }
    }
}
