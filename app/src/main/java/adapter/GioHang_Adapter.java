package adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.appprojectcuoikhoa.GioHangActivity;
import com.example.appprojectcuoikhoa.HomeActivity;
import com.example.appprojectcuoikhoa.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import model.GioHang;

import static com.example.appprojectcuoikhoa.GioHangActivity.EventUtil;


public class GioHang_Adapter extends RecyclerView.Adapter<GioHang_Adapter.GioHangViewHolder>{
    private Context context;
    private List<GioHang> arrayGioHang;
//    int sl;

    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public GioHang_Adapter(Context context, List<GioHang> arrayGioHang) {
        this.context = context;
        this.arrayGioHang = arrayGioHang;
    }
//    public void setDataGioHang(List<GioHang> arrayGioHang){
//        this.arrayGioHang =arrayGioHang;
//        notifyDataSetChanged();
//    }
    @Override
    public GioHangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_gio_hang,parent,false);
        return new GioHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GioHang_Adapter.GioHangViewHolder holder, int position) {
        GioHang gioHang = arrayGioHang.get(position);
        Glide.with(context).load(arrayGioHang.get(position).getHinhAnhSp()).into(holder.hinhAnhSp);
        holder.tenSp.setText(arrayGioHang.get(position).getTenSp());
        holder.giaSp.setText(new DecimalFormat("##.000").format(arrayGioHang.get(position).getGiaSp())+"đ");
        holder.tongGiaGioHnag.setText(new DecimalFormat("###,###,###.000").format(arrayGioHang.get(position).getTongGiaSp())+"đ");
        holder.soLuongSp.setText(String.valueOf(arrayGioHang.get(position).getSoLuongSp()));
        holder.sizeSp.setText(String.valueOf(arrayGioHang.get(position).getSize()));
        viewBinderHelper.bind(holder.swipeRevealLayout,gioHang.getTenSp());
//        Soluong(sl);
        holder.layoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.gioHangArrayList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                EventUtil();
            }
        });

//         sl = Integer.parseInt(holder.soLuongSp.getText().toString().trim());

//        if (sl<=1){
//            holder.minus.setVisibility(View.INVISIBLE);
//        }else if (sl>=1){
//            holder.minus.setVisibility(View.VISIBLE);
//            holder.plus.setVisibility(View.VISIBLE);
//        }
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuongMoi = Integer.parseInt(holder.soLuongSp.getText().toString())+1;
                holder.soLuongSp.setText(String.valueOf(soLuongMoi));
                int soLuongHienTai = HomeActivity.gioHangArrayList.get(position).getSoLuongSp();
                double giaHienTai = HomeActivity.gioHangArrayList.get(position).getTongGiaSp();
                HomeActivity.gioHangArrayList.get(position).setSoLuongSp(soLuongMoi);
                double giaMoi = (soLuongMoi*giaHienTai)/soLuongHienTai;
                HomeActivity.gioHangArrayList.get(position).setTongGiaSp(giaMoi);
                holder.tongGiaGioHnag.setText(new DecimalFormat("###,###,###.000").format(giaMoi)+"đ");
                EventUtil();
//                Soluong(soLuongMoi);
                if (soLuongMoi>=2){
                    holder.minus.setVisibility(View.VISIBLE);
                    holder.plus.setVisibility(View.VISIBLE);
                    holder.soLuongSp.setText(String.valueOf(soLuongMoi));
                }
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuongMoi = Integer.parseInt(holder.soLuongSp.getText().toString())-1;
                holder.soLuongSp.setText(String.valueOf(soLuongMoi));
                int soLuongHienTai = HomeActivity.gioHangArrayList.get(position).getSoLuongSp();
                double giaHienTai = HomeActivity.gioHangArrayList.get(position).getTongGiaSp();
                HomeActivity.gioHangArrayList.get(position).setSoLuongSp(soLuongMoi);
                double giaMoi = (soLuongMoi*giaHienTai)/soLuongHienTai;
                HomeActivity.gioHangArrayList.get(position).setTongGiaSp(giaMoi);
                holder.tongGiaGioHnag.setText(new DecimalFormat("###,###,###.000").format(giaMoi)+"đ");
                EventUtil();
//                Soluong(soLuongMoi);
                if (soLuongMoi <2){
                    holder.minus.setVisibility(View.INVISIBLE);
                    holder.plus.setVisibility(View.VISIBLE);
                    holder.soLuongSp.setText(String.valueOf(soLuongMoi));
                }else {
                    holder.minus.setVisibility(View.VISIBLE);
                    holder.plus.setVisibility(View.VISIBLE);
                    holder.soLuongSp.setText(String.valueOf(soLuongMoi));
                }
            }
        });

    }
//    public static void Soluong(int soluong){
//        int tongsoluong = 0;
//        for (int i=0;i<HomeActivity.gioHangArrayList.size();i++){
//            tongsoluong += soluong;
//        }
//    }

    @Override
    public int getItemCount() {
        return arrayGioHang.size();
    }

    public class GioHangViewHolder extends RecyclerView.ViewHolder{
        ImageView hinhAnhSp;
        TextView tenSp;
        TextView soLuongSp;
        TextView giaSp;
        TextView tongGiaGioHnag;
        TextView sizeSp;
        ImageView minus , plus;
        SwipeRevealLayout swipeRevealLayout;
        LinearLayout layoutDelete;

        public GioHangViewHolder(View itemView) {
            super(itemView);
            hinhAnhSp = itemView.findViewById(R.id.imgHinhAnhSp);
            tenSp = itemView.findViewById(R.id.txtNameGioHang);
            soLuongSp = itemView.findViewById(R.id.txtSoLuongSPGioHang);
            giaSp = itemView.findViewById(R.id.txtGiaSanPham);
            tongGiaGioHnag = itemView.findViewById(R.id.txtTongGiaGioHang);
            sizeSp = itemView.findViewById(R.id.txtSizeGioHang);
            minus = itemView.findViewById(R.id.btnMinusGioHang);
            plus = itemView.findViewById(R.id.btnPlusGiohang);
            swipeRevealLayout = itemView.findViewById(R.id.swipeDelete);
            layoutDelete = itemView.findViewById(R.id.layout_delete);
        }
    }
}
