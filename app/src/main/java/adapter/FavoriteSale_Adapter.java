package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appprojectcuoikhoa.R;

import java.text.DecimalFormat;
import java.util.List;

import data.FavoriteDB;
import model.FavoriteSale;


public class FavoriteSale_Adapter extends RecyclerView.Adapter<FavoriteSale_Adapter.ViewHolder>{
    private Context context;
    private List<FavoriteSale> favoriteSaleList;
    private FavoriteDB favoriteDB;

    public FavoriteSale_Adapter(Context context, List<FavoriteSale> favoriteSaleList) {
        this.context = context;
        this.favoriteSaleList = favoriteSaleList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_favorite_sale,parent,false);
        favoriteDB = new FavoriteDB(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(favoriteSaleList.get(position).getImageFavSale()).into(holder.imageFavSale);
        holder.txtNameFavSale.setText(favoriteSaleList.get(position).getNamFavSale());

        if (favoriteSaleList.get(position).getMoneyFavSaleBD() != 0.0){
            holder.txtMoneyFavSaleBD.setText(new DecimalFormat("##.000").format(favoriteSaleList.get(position).getMoneyFavSaleBD())+" đ ");
        }else {
            holder.txtMoneyFavSaleBD.setVisibility(View.GONE);
            holder.imgLogoFavSale.setVisibility(View.GONE);
        }

        holder.txtMoneyFavSale.setText(new DecimalFormat("##.000").format(favoriteSaleList.get(position).getMoneyFavSale())+" đ ");
    }

    @Override
    public int getItemCount() {
        return favoriteSaleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgLogoFavSale , imgFavSale , imageFavSale;
        TextView txtMoneyFavSale , txtMoneyFavSaleBD , txtNameFavSale;
        public ViewHolder(View itemView) {
            super(itemView);
            imageFavSale = itemView.findViewById(R.id.FavSaleImage);
            imgLogoFavSale = itemView.findViewById(R.id.imgLogoFavSale);
            imgFavSale = itemView.findViewById(R.id.imgFavSale);
            txtNameFavSale = itemView.findViewById(R.id.txtNameSaleFav);
            txtMoneyFavSale = itemView.findViewById(R.id.txtMoneyFavSale);
            txtMoneyFavSaleBD = itemView.findViewById(R.id.txtMoneyFavSaleBD);

            imgFavSale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    FavoriteSale favoriteSale = favoriteSaleList.get(position);
                    favoriteDB.removeFavorite(favoriteSale.getIdFavSale());
                    RemoveItem(position);
                }
            });
        }
    }
    private void RemoveItem(int position){
        favoriteSaleList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,favoriteSaleList.size());
    }
}
