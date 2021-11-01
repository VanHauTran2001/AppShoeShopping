package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.appprojectcuoikhoa.HomeActivity;
import com.example.appprojectcuoikhoa.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import data.FavoriteDB;
import fragment.ThongBaoFragment;
import model.ThongBao;


public class ThongBao_Adapter extends RecyclerView.Adapter<ThongBao_Adapter.ThongBaoViewHolder>{
    private Context context;
    private List<ThongBao> thongBaoList;
    private FavoriteDB favoriteDB;
    ThongBaoFragment thongBaoFragment;

    public ThongBao_Adapter(Context context, List<ThongBao> thongBaoList) {
        this.context = context;
        this.thongBaoList = thongBaoList;
    }
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    @Override
    public ThongBaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_thong_bao,parent,false);
        favoriteDB = new FavoriteDB(context);
        return new ThongBaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ThongBaoViewHolder holder, int position) {
        ThongBao TB = thongBaoList.get(position);
//        holder.imgLogoThongBao.setImageResource(thongBaoList.get(position).getImgLogoThongBao());
        holder.txtThongBao.setText(thongBaoList.get(position).getTxtThongBao());
        holder.txtHour.setText(thongBaoList.get(position).getHour());
        holder.txtDay.setText(thongBaoList.get(position).getDay());
        viewBinderHelper.bind(holder.swipeRevealTBLayout,String.valueOf(TB.getIdThongBao()));
        holder.layoutTBDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThongBao thongBao = thongBaoList.get(position);
                favoriteDB.removeThongBao(thongBao.getIdThongBao());
                RemoveItem(position);
            }
        });
    }
    private void RemoveItem(int position){
        thongBaoList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,thongBaoList.size());
    }
    @Override
    public int getItemCount() {
        return thongBaoList.size();
    }

    public class ThongBaoViewHolder extends RecyclerView.ViewHolder{

        ImageView imgLogoThongBao;
        TextView txtThongBao , txtDay , txtHour;
        SwipeRevealLayout swipeRevealTBLayout;
        LinearLayout layoutTBDelete;
        public ThongBaoViewHolder(View itemView) {
            super(itemView);
            imgLogoThongBao = itemView.findViewById(R.id.logoThongBao);
            txtThongBao = itemView.findViewById(R.id.txtThongBao);
            txtDay = itemView.findViewById(R.id.txtDay);
            txtHour = itemView.findViewById(R.id.txtHour);
            swipeRevealTBLayout = itemView.findViewById(R.id.swipeDeleteTB);
            layoutTBDelete = itemView.findViewById(R.id.layout_delete_TB);
        }
    }
}
